import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC 스트리밍 시나리오 예제 코드입니다.
 * 이 코드를 실행하려면 실제 데이터베이스와 JDBC 드라이버가 필요합니다.
 * 
 * 가정: `articles` 테이블에 `id` (INT)와 `large_content` (BLOB/LONGBLOB) 컬럼이 존재합니다.
 */
public class JdbcStreamingExample {

    // 데이터베이스 연결 정보 (실제 환경에 맞게 수정 필요)
    private static final String DB_URL_MYSQL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";

    /**
     * 시나리오 1: 기본 동작 (클라이언트 측 스트리밍)
     * - DB는 100MB 데이터를 모두 네트워크로 전송합니다.
     * - JDBC 드라이버가 이 데이터를 메모리에 받은 후, 애플리케이션은 드라이버의 메모리로부터 1MB씩 읽습니다.
     * - 애플리케이션의 Heap 메모리는 절약되지만, 네트워크 대역폭은 절약되지 않습니다.
     */
    public void scenario1_defaultBehavior() throws SQLException {
        System.out.println("--- 시나리오 1: 기본 동작 실행 ---");

        String sql = "SELECT large_content FROM articles WHERE id = 1";

        try (Connection conn = DriverManager.getConnection(DB_URL_MYSQL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                // getInputStream()을 호출하면 드라이버가 가진 전체 데이터에 대한 스트림을 받습니다.
                try (InputStream dataStream = rs.getBinaryStream("large_content")) {
                    
                    // 파일 읽기와 동일한 방식
                    byte[] buffer = new byte[1024 * 1024]; // 1MB 버퍼
                    int bytesRead;
                    int chunkCount = 0;
                    long totalBytes = 0;

                    System.out.println("스트림에서 데이터 읽기 시작...");
                    while ((bytesRead = dataStream.read(buffer)) != -1) {
                        chunkCount++;
                        totalBytes += bytesRead;
                        System.out.println("  -> 청크 " + chunkCount + " 처리 (" + bytesRead + " 바이트)");
                        // 여기서 1MB씩 읽은 데이터를 처리하는 로직 수행
                    }
                    System.out.println("스트림 읽기 완료. 총 " + totalBytes + " 바이트 처리.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("--- 시나리오 1 종료 ---\n");
    }

    /**
     * 시나리오 2: 진짜 스트리밍 (서버 측 스트리밍) - MySQL 예제
     * - JDBC 드라이버 설정을 통해 DB에서 데이터를 조금씩 가져오도록 설정합니다.
     * - 애플리케이션이 스트림을 읽다가 버퍼가 비면, 드라이버가 DB에 다음 데이터 조각을 요청합니다.
     * - 애플리케이션의 Heap 메모리와 네트워크 대역폭 모두를 절약할 수 있습니다.
     */
    public void scenario2_trueStreaming_mysql() throws SQLException {
        System.out.println("--- 시나리오 2: 진짜 스트리밍 실행 (MySQL) ---");

        // MySQL의 경우, 스트리밍을 위해 커넥션 URL에 옵션을 추가할 수도 있습니다.
        // String streamingUrl = DB_URL_MYSQL + "?useCursorFetch=true";
        
        String sql = "SELECT large_content FROM articles WHERE id = 1";

        // try-with-resources는 conn, pstmt, rs를 자동으로 닫아줍니다.
        try (Connection conn = DriverManager.getConnection(DB_URL_MYSQL, DB_USER, DB_PASSWORD)) {
            
            // ***** 핵심 설정 부분 *****
            // 1. Statement를 스트리밍 가능하도록 생성합니다.
            PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            
            // 2. 한 번에 가져올 데이터 양을 최소로 설정하여 스트리밍을 강제합니다.
            //    (Integer.MIN_VALUE는 MySQL 드라이버에게 한 번에 한 행씩 스트리밍하라는 신호)
            pstmt.setFetchSize(Integer.MIN_VALUE);
            // ***********************

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 이 스트림을 읽을 때, 내부 버퍼가 비면 드라이버가 DB에 다음 데이터를 요청합니다.
                    try (InputStream dataStream = rs.getBinaryStream("large_content")) {
                        
                        byte[] buffer = new byte[1024 * 1024]; // 1MB 버퍼
                        int bytesRead;
                        int chunkCount = 0;
                        long totalBytes = 0;

                        System.out.println("스트림에서 데이터 읽기 시작 (진짜 스트리밍)...");
                        while ((bytesRead = dataStream.read(buffer)) != -1) {
                            chunkCount++;
                            totalBytes += bytesRead;
                            System.out.println("  -> DB로부터 청크 " + chunkCount + " 수신 (" + bytesRead + " 바이트)");
                        }
                        System.out.println("스트림 읽기 완료. 총 " + totalBytes + " 바이트 처리.");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("--- 시나리오 2 종료 ---");
    }

    public static void main(String[] args) {
        System.out.println("JDBC 스트리밍 예제입니다.");
        System.out.println("이 코드는 개념 설명을 위한 것으로, 실제 DB 연결 없이는 실행되지 않습니다.");
        System.out.println("실행을 위해서는 'DB_URL_MYSQL', 'DB_USER', 'DB_PASSWORD'를 수정하고,");
        System.out.println("MySQL JDBC 드라이버를 클래스패스에 추가해야 합니다.");
        
        // 예:
        // try {
        //     JdbcStreamingExample example = new JdbcStreamingExample();
        //     example.scenario1_defaultBehavior();
        //     example.scenario2_trueStreaming_mysql();
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
    }
}