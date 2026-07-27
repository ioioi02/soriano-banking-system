package org.banking.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";

    private static Connection connection = null;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                PropertyLoader config = PropertyLoader.getInstance();

                String url = config.getProperty(DB_URL);
                String username = config.getProperty(DB_USERNAME);
                String password = config.getProperty(DB_PASSWORD);

                connection = DriverManager.getConnection(url, username, password);
                System.out.println("[INFO] Database connection successfully established.");
            } catch (SQLException e) {
                System.err.println("[ERROR] Failed to establish database connection: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
}