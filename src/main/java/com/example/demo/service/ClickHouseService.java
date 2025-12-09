package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClickHouseService {

    @Value("${clickhouse.url}")
    private String url;

    @Value("${clickhouse.username}")
    private String username;

    @Value("${clickhouse.password}")
    private String password;

    public void insertEvent(String source, String message) {
        String sql = "INSERT INTO event_logs (timestamp, source, message) VALUES (now(), ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, source);
            ps.setString(2, message);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getLatestEvents() {
        String sql = "SELECT source, message, timestamp FROM event_logs ORDER BY timestamp DESC LIMIT 10";
        List<String> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(
                        rs.getDate("timestamp") + " [" +
                                rs.getString("source") + "] " +
                                rs.getString("message")
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
