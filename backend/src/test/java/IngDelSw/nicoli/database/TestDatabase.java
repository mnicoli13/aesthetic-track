package IngDelSw.nicoli.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TestDatabase {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testConnection() {
        assertThat(jdbcTemplate).isNotNull();

        String result = jdbcTemplate.queryForObject("SELECT 'Connection Successful' AS result", String.class);

        assertThat(result).isEqualTo("Connection Successful");
    }
}

