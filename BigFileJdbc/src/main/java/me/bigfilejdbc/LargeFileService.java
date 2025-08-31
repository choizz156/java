package me.bigfilejdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class LargeFileService {

    private final LargeFileDao largeFileDao;

    @Transactional
    public void runAllScenarios() {
        long articleId = 1L;
        long dataSize = 1024L * 1024 * 100; // 100MB

        // 1. 테이블 생성 및 데이터 삽입 (공통)
        System.out.println("[초기화: 테이블 및 데이터 준비]");
        largeFileDao.setupDatabase();
        largeFileDao.insertLargeObject(articleId, dataSize);
        System.out.println("  -> 100MB 데이터 INSERT 완료.");

        // 2. 시나리오 1 실행
        runScenario1_buffering(articleId);

        // 3. 시나리오 2 실행
        runScenario2_streaming(articleId);
    }

    private void runScenario1_buffering(long articleId) {
        System.out.println("\n[시나리오 1: 전체 버퍼링 후 처리 시작]");
        System.out.println("  -> DB에서 100MB를 한 번에 가져옵니다 (네트워크 부하 발생)...");

        largeFileDao.queryAndBuffer_scenario1(articleId, inputStream -> {
            System.out.println("  -> 애플리케이션에서 데이터 처리를 시작합니다.");
            try {
                byte[] buffer = new byte[1024 * 1024]; // 1MB 버퍼
                int bytesRead;
                AtomicInteger chunkCount = new AtomicInteger(0);
                AtomicLong totalBytes = new AtomicLong(0);

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    chunkCount.incrementAndGet();
                    totalBytes.addAndGet(bytesRead);
                }
                System.out.println("    -> 처리 완료. 총 " + (totalBytes.get() / (1024 * 1024)) + " MB 읽음.");
            } catch (IOException e) {
                throw new RuntimeException("스트림을 읽는 중 오류 발생", e);
            }
        });
    }

    private void runScenario2_streaming(long articleId) {
        System.out.println("\n[시나리오 2: DB와 통신하며 스트리밍 처리 시작]");
        System.out.println("  -> DB에서 데이터를 1MB씩 나눠서 가져옵니다...");

        largeFileDao.queryAndStream_scenario2(articleId, inputStream -> {
            try {
                byte[] buffer = new byte[1024 * 1024]; // 1MB 버퍼
                int bytesRead;
                AtomicInteger chunkCount = new AtomicInteger(0);
                AtomicLong totalBytes = new AtomicLong(0);

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    chunkCount.incrementAndGet();
                    totalBytes.addAndGet(bytesRead);
                    System.out.println("    -> 청크 " + chunkCount.get() + " 처리 중 (" + bytesRead + " 바이트)");
                }
                System.out.println("    -> 처리 완료. 총 " + (totalBytes.get() / (1024 * 1024)) + " MB 읽음.");
            } catch (IOException e) {
                throw new RuntimeException("스트림을 읽는 중 오류 발생", e);
            }
        });
    }
}