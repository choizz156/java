
package me.bigfilejdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
public class LargeFileDao {

    private final JdbcTemplate jdbcTemplate;

    public void setupDatabase() {
        jdbcTemplate.execute("DROP TABLE articles IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE articles (id BIGINT PRIMARY KEY, content BLOB)");
    }

    public void insertLargeObject(long id, long dataSize) {
        String sql = "INSERT INTO articles (id, content) VALUES (?, ?)";

        jdbcTemplate.execute(sql, (java.sql.PreparedStatement ps) -> {
            ps.setLong(1, id);
            CustomInputStream dataStream = new CustomInputStream(dataSize);
            ps.setBinaryStream(2, dataStream, dataSize);
            return ps.executeUpdate();
        });
    }

    /**
     * 시나리오 1: DB에서 결과를 모두 가져와 드라이버 메모리에 버퍼링한 후, 앱에서 스트리밍
     */
    public void queryAndBuffer_scenario1(long id, Consumer<InputStream> streamProcessor) {
        String sql = "SELECT content FROM articles WHERE id = ?";

        // setFetchSize가 없으므로, 드라이버의 기본 방식(전체 결과 버퍼링)으로 동작합니다.
        PreparedStatementSetter pss = ps -> ps.setLong(1, id);

        RowCallbackHandler rch = rs -> {
            try (InputStream dataStream = rs.getBinaryStream("content")) {
                streamProcessor.accept(dataStream);
            } catch (IOException e) {
                throw new SQLException("Failed to process content stream", e);
            }
        };

        jdbcTemplate.query(sql, pss, rch);
    }

    /**
     * 시나리오 2: DB와 통신하며 데이터를 스트리밍 (진짜 스트리밍)
     */
    public void queryAndStream_scenario2(long id, Consumer<InputStream> streamProcessor) {
        String sql = "SELECT content FROM articles WHERE id = ?";

        // PreparedStatementSetter를 사용하여, 이 쿼리에만 스트리밍 설정을 적용합니다.
        PreparedStatementSetter pss = ps -> {
            ps.setLong(1, id);
            // H2 드라이버는 fetch size로 1 이상을 요구하므로, 1로 설정하여 한 행씩 가져오게 합니다.
            ps.setFetchSize(1);
        };

        RowCallbackHandler rch = rs -> {
            try (InputStream dataStream = rs.getBinaryStream("content")) {
                streamProcessor.accept(dataStream);
            } catch (IOException e) {
                throw new SQLException("Failed to process content stream", e);
            }
        };

        jdbcTemplate.query(sql, pss, rch);
    }
}
