package com.example.demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Configuration
public class ClickHouseConfig {

    @Value("${clickhouse.url}")
    private String url;

    @Value("${clickhouse.username}")
    private String username;

    @Value("${clickhouse.password}")
    private String password;

    @PostConstruct
    public void init() {
        int retries = 10;

        while (retries-- > 0) {
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {

                stmt.execute("""
                CREATE TABLE IF NOT EXISTS event_logs
                (
                    timestamp DateTime,
                    source String,
                    message String
                )
                ENGINE = MergeTree()
                ORDER BY timestamp
            """);

            } catch (Exception e) {
                throw new RuntimeException("Failed to init ClickHouse", e);
            }
        }
    }
}
