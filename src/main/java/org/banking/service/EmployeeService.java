package org.banking.service;

import org.banking.dao.EmployeeDAO;
import org.banking.model.Employee;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public Employee authenticateEmployee(String employeeId, String securityPassword) {
        try {
            String password = employeeDAO.getPasswordByEmployeeId(employeeId);

            if (password == null) { return null; }

            if (BCrypt.checkpw(securityPassword, password)) {
                return employeeDAO.findEmployeeByEmployeeId(employeeId);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve employee information: " + e.getMessage());
        }
        return null;
    }
}