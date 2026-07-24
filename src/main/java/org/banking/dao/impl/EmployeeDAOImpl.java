package org.banking.dao.impl;

import org.banking.config.DatabaseConnection;
import org.banking.dao.EmployeeDAO;
import org.banking.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static final String SELECT_PASSWORD_BY_EMPLOYEE_ID =
            "SELECT password FROM employees WHERE employee_id = ? AND is_active = ?";

    private static final String SELECT_EMPLOYEE_BY_EMPLOYEE_ID =
            "SELECT id, employee_id, last_name, first_name, middle_name, suffix, is_active FROM employees WHERE employee_id = ? AND is_active = ?";

    @Override
    public String getPasswordByEmployeeId(String empId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PASSWORD_BY_EMPLOYEE_ID)) {

            preparedStatement.setString(1, empId);
            preparedStatement.setInt(2, 1);

            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    return result.getString("password");
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve employee password: " + e.getMessage());
            throw e;
        }
        return null;
    }

    @Override
    public Employee findEmployeeByEmployeeId(String empId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_EMPLOYEE_ID)) {

            preparedStatement.setString(1, empId);
            preparedStatement.setInt(2, 1);

            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String employeeId = result.getString("employee_id");
                    String lastname = result.getString("last_name");
                    String firstname = result.getString("first_name");
                    String middlename = result.getString("middle_name");
                    String suffix = result.getString("suffix");
                    byte isActive = result.getByte("is_active");

                    return new Employee(id, employeeId, lastname, firstname, middlename, suffix, isActive);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve employee information: " + e.getMessage());
            throw e;
        }
        return null;
    }
}