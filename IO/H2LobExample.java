import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * H2 인메모리 데이터베이스를 사용하여 대용량 BLOB 데이터를
 * 스트리밍 방식으로 저장하고 조회하는 예제입니다.
 * 
 * [실행 전 준비사항]
 * 1. H2 데이터베이스 드라이버 .jar 파일 다운로드
 * 2. 아래의 컴파일 및 실행 명령어에 다운로드한 .jar 파일의 실제 경로를 입력
 */
public class H2LobExample {

    // H2 인메모리 DB 연결 정보
    private static final String JDBC_URL = "jdbc:h2:mem:testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    // 처리할 데이터 크기 (100MB)
    private static final long DATA_SIZE = 1024L * 1024 * 100;

    public static void main(String[] args) {
        System.out.println("H2 대용량 데이터 처리 예제를 시작합니다.");
        System.out.println("주의: H2 드라이버(.jar)가 클래스패스에 포함되어 있어야 합니다.");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            System.out.println("H2 데이터베이스에 연결되었습니다.");

            // 1. 데이터베이스 테이블 설정
            setupDatabase(conn);

            // 2. 100MB 데이터를 스트림으로 DB에 INSERT
            insertLargeObject(conn);

            // 3. DB에 저장된 100MB 데이터를 스트림으로 SELECT하여 처리
            queryAndStreamLargeObject(conn);

            System.out.println("\n모든 작업이 완료되었습니다.");

        } catch (SQLException e) {
            System.err.println("데이터베이스 작업 중 오류가 발생했습니다.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("예상치 못한 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    private static void setupDatabase(Connection conn) throws SQLException {
        System.out.println("\n[1단계: 테이블 생성]");
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE articles (id INT PRIMARY KEY, content BLOB)");
            System.out.println("  -> 'articles' 테이블을 생성했습니다.");
        }
    }

    private static void insertLargeObject(Connection conn) throws SQLException {
        System.out.println("\n[2단계: 100MB 데이터 스트리밍 INSERT]");
        String sql = "INSERT INTO articles (id, content) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);

            // 100MB 데이터를 메모리에 올리지 않고, 필요할 때마다 생성하는 커스텀 InputStream 사용
            DummyDataInputStream dataStream = new DummyDataInputStream(DATA_SIZE);
            
            // setBinaryStream을 사용하여 스트림으로 데이터를 전송
            pstmt.setBinaryStream(2, dataStream, DATA_SIZE);

            System.out.println("  -> DB로 데이터 전송을 시작합니다...");
            pstmt.executeUpdate();
            System.out.println("  -> 100MB 데이터 INSERT 완료.");
        }
    }

    private static void queryAndStreamLargeObject(Connection conn) throws SQLException, IOException {
        System.out.println("\n[3단계: 100MB 데이터 스트리밍 SELECT]");
        String sql = "SELECT content FROM articles WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("  -> DB로부터 대용량 데이터 스트림을 가져왔습니다. 이제 1MB씩 읽습니다.");
                    // BLOB 객체에서 InputStream을 얻어옴
                    try (InputStream contentStream = rs.getBinaryStream("content")) {
                        byte[] buffer = new byte[1024 * 1024]; // 1MB 버퍼
                        int bytesRead;
                        int chunkCount = 0;
                        long totalBytes = 0;

                        while ((bytesRead = contentStream.read(buffer)) != -1) {
                            chunkCount++;
                            totalBytes += bytesRead;
                            System.out.println("    -> 청크 " + chunkCount + " 처리 중 (" + bytesRead + " 바이트)");
                        }
                        System.out.println("  -> 스트림 처리 완료. 총 " + (totalBytes / (1024*1024)) + " MB 읽음.");
                    }
                } else {
                    System.out.println("  -> 조회된 데이터가 없습니다.");
                }
            }
        }
    }

    /**
     * 지정된 크기만큼의 가상 데이터를 실시간으로 생성하는 InputStream.
     * 100MB짜리 byte[] 배열을 미리 만들 필요가 없어 메모리를 절약할 수 있다.
     */
    private static class DummyDataInputStream extends InputStream {
        private final long totalSize;
        private long bytesGenerated = 0;

        public DummyDataInputStream(long totalSize) {
            this.totalSize = totalSize;
        }

        @Override
        public int read() throws IOException {
            if (bytesGenerated >= totalSize) {
                return -1; // End of stream
            }
            bytesGenerated++;
            // 'A' ~ 'Z' 사이의 문자를 반복해서 반환
            return 'A' + (int)((bytesGenerated - 1) % 26);
        }

        @Override
        public int available() throws IOException {
            return (int) Math.min(Integer.MAX_VALUE, totalSize - bytesGenerated);
        }
    }
}