package com.food.eat.authservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenSchemaFixer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(RefreshTokenSchemaFixer.class);

    private final JdbcTemplate jdbcTemplate;

    public RefreshTokenSchemaFixer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        String tokenColumnType = jdbcTemplate.query(
                """
                        SELECT data_type
                        FROM information_schema.columns
                        WHERE table_schema = ANY (current_schemas(false))
                          AND table_name = 'refresh_tokens'
                          AND column_name = 'token'
                        ORDER BY array_position(current_schemas(false), table_schema)
                        LIMIT 1
                        """,
                rs -> rs.next() ? rs.getString("data_type") : null
        );

        if (tokenColumnType == null) {
            return;
        }

        if ("character varying".equalsIgnoreCase(tokenColumnType) || "varchar".equalsIgnoreCase(tokenColumnType)) {
            jdbcTemplate.execute("ALTER TABLE refresh_tokens ALTER COLUMN token TYPE TEXT");
            log.info("Updated refresh_tokens.token column type from {} to TEXT", tokenColumnType);
        }
    }
}
