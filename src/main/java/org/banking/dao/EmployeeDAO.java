package org.banking.dao;

import org.banking.dto.EmployeeAccount;

import java.sql.SQLException;

public interface EmployeeDAO {

    EmployeeAccount findEmployeeByEmployeeId(String empId) throws SQLException;
}