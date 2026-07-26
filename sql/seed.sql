USE soriano_banking_system;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE employees;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO employees (
    employee_id, 
    password, 
    last_name, 
    first_name, 
    middle_name, 
    suffix, 
    is_active
) VALUES (
    '12345678', 
-- jBCrypt hash format representing the raw 'Employee123' value --
    '$2a$10$BQrtzduLqoeLvQTdAdOfAOLXp26ZlqskUeGPCY0G4yMtOPy9fwcNi', 
    'Doe', 
    'John', 
    'Cruz', 
    '', 
    1
);

SELECT employee_id, first_name, last_name, is_active FROM employees;
