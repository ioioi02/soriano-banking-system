package org.banking.util;

import org.banking.config.DatabaseConnection;
import org.banking.dao.EmployeeDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSeeder {

    private static final String INSERT_EMPLOYEE =
            "INSERT INTO employees (employee_id, password, last_name, first_name, middle_name, suffix, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final EmployeeDAO employeeDAO;

    public DatabaseSeeder(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public void seed() {
        System.out.print("Seeding database...");

        String employeeId = "12345678";
        String password = "Employee123";
        String lastname = "Doe";
        String firstname = "John";
        String middlename = "Cruz";
        String suffix = "";
        byte isActive = 1;

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        try {
            if (employeeDAO.findEmployeeByEmployeeId(employeeId) != null) {
                System.out.print("Aborted. Employee Id '" + employeeId + "' already exists in the database.");
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE)) {

                preparedStatement.setString(1, employeeId);
                preparedStatement.setString(2, passwordHash); // Saves the safe $2a$ string hash
                preparedStatement.setString(3, lastname);
                preparedStatement.setString(4, firstname);
                preparedStatement.setString(5, middlename);
                preparedStatement.setString(6, suffix);
                preparedStatement.setByte(7, isActive);

                int rowInserted = preparedStatement.executeUpdate();
                if (rowInserted > 0) {
                    System.out.print("Database seeding completed successfully. Rows inserted: " + rowInserted);
                }
            }
        } catch (SQLException e) {
            System.err.println(": " + e.getMessage());
        }
    }
}