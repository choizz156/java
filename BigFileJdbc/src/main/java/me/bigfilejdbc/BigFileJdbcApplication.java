package me.bigfilejdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class BigFileJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigFileJdbcApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(LargeFileService largeFileService) {
		return args -> {
			log.info("CommandLineRunner 시작: 시나리오 1과 2를 순차적으로 실행합니다.");
			try {
				largeFileService.runAllScenarios();
			} catch (Exception e) {
				log.error("실행 중 심각한 오류 발생", e);
			}
			log.info("CommandLineRunner 종료.");
		};
	}
}
