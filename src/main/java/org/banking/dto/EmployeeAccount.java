package org.banking.dto;

import org.banking.model.Employee;

public record EmployeeAccount(Employee employee, String password) {
}