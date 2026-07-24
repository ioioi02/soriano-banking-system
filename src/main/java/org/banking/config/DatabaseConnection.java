package org.banking.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/soriano_banking_system";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private static Connection connection = null;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                System.out.println("Database connection established successfully.");
            } catch (SQLException e) {
                System.err.println("Failed to establish database connection: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
}