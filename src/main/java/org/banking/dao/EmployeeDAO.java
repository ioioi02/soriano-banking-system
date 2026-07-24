package org.banking.dao;

import org.banking.model.Employee;

import java.sql.SQLException;

public interface EmployeeDAO {

    String getPasswordByEmployeeId(String employeeId) throws SQLException;

    Employee findEmployeeByEmployeeId(String employeeId) throws SQLException;
}