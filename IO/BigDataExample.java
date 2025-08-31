import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BigDataExample {

	public static void main(String[] args) throws IOException {
		String largeFilePath = "IO/temp/large_file.txt";
		createFile(largeFilePath, 1024 * 1024 * 100);

		processFileInChunks(largeFilePath, 1024 * 1024);

		try {
			Files.delete(Paths.get(largeFilePath));
			System.out.println("성공적으로 파일을 삭제했습니다: " + largeFilePath);
		} catch (IOException e) {
			System.err.println("파일 삭제 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	private static void processFileInChunks(String largeFilePath, int chunkSize) {
		Path path = Paths.get(largeFilePath);

		if (!Files.exists(path) || !Files.isReadable(path)) {
			System.out.println("파일 없음");
			return;
		}

		try (FileInputStream fis = new FileInputStream(path.toFile())) {
			byte[] buffer = new byte[chunkSize];
			int bytesRead;
			long totalBytesProcessed = 0;
			int chunkCount = 0;

			while ((bytesRead = fis.read(buffer, 0, buffer.length)) != -1) {
				chunkCount++;
				totalBytesProcessed += bytesRead;

				System.out.println("--- 청크 " + chunkCount + " 처리 중 ---");
				System.out.printf("읽은 바이트 수: %,d 바이트%n", bytesRead);

				String chunkContent = new String(buffer, 0, bytesRead);
				System.out.println(
					"데이터 일부: " + chunkContent.substring(0, Math.min(chunkContent.length(), 50))
						+ "...");
			}

			System.out.println("---------------------------------------");
			System.out.println("파일 처리가 완료되었습니다.");
			System.out.printf("총 처리된 바이트: %,d 바이트%n", totalBytesProcessed);
			System.out.println("총 청크 수: " + chunkCount);



		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createFile(String largeFilePath, int i) throws IOException {
		Path path = Paths.get(largeFilePath);

		if (Files.exists(path)) {
			System.out.println("이미 존재");
			return;
		}

		byte[] contents = new byte[i];
		for (int j = 0; j < i; j++) {
			contents[j] = (byte) ('A' + (j % 26));
		}

		Files.write(path, contents);
		System.out.println("더미파일 생성 완료");
	}
}
