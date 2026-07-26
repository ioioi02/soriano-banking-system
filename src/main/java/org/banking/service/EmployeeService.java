package org.banking.service;

import org.banking.dao.EmployeeDAO;
import org.banking.dto.EmployeeAccount;
import org.banking.model.Employee;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public Employee authenticateEmployee(String employeeId, String securityPassword) throws SQLException {
        EmployeeAccount employeeAccount = employeeDAO.findEmployeeByEmployeeId(employeeId);

        if (employeeAccount != null && BCrypt.checkpw(securityPassword, employeeAccount.password())) {
            return employeeAccount.employee();
        }

        return null;
    }
}