package org.banking.dao.impl;

import org.banking.config.DatabaseConnection;
import org.banking.dao.EmployeeDAO;
import org.banking.dto.EmployeeAccount;
import org.banking.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static final String SELECT_EMPLOYEE_BY_EMPLOYEE_ID =
            "SELECT id, employee_id, password, last_name, first_name, middle_name, suffix, is_active, created_at, updated_at FROM employees WHERE employee_id = ? AND is_active = ?";

    @Override
    public EmployeeAccount findEmployeeByEmployeeId(String empId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_EMPLOYEE_ID)) {

            preparedStatement.setString(1, empId);
            preparedStatement.setInt(2, 1);

            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    Long id = result.getLong("id");
                    String employeeId = result.getString("employee_id");
                    String password = result.getString("password");
                    String lastname = result.getString("last_name");
                    String firstname = result.getString("first_name");
                    String middlename = result.getString("middle_name");
                    String suffix = result.getString("suffix");
                    Boolean isActive = result.getBoolean("is_active");
                    Instant created_at = result.getTimestamp("created_at").toInstant();
                    Instant updated_at = result.getTimestamp("updated_at").toInstant();

                    Employee employee = new Employee(id, employeeId, lastname, firstname, middlename, suffix, isActive, created_at, updated_at);

                    return new EmployeeAccount(employee, password);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to retrieve employee account for Employee Id '" + empId + "': " + e.getMessage());
            throw e;
        }

        return null;
    }
}