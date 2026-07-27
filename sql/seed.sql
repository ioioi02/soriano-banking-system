USE soriano_banking_system;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE bank_accounts;
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
) VALUES
      (
          '12345678',
          '$2a$10$BQrtzduLqoeLvQTdAdOfAOLXp26ZlqskUeGPCY0G4yMtOPy9fwcNi',
          'Santos',
          'Juan',
          'Cruz',
          '',
          1
      ),
      (
          '87654321',
          '$2a$10$BQrtzduLqoeLvQTdAdOfAOLXp26ZlqskUeGPCY0G4yMtOPy9fwcNi',
          'Santos',
          'Maria',
          'Reyes',
          '',
          1
      ),
      (
          '11223344',
          '$2a$10$BQrtzduLqoeLvQTdAdOfAOLXp26ZlqskUeGPCY0G4yMtOPy9fwcNi',
          'Fernandez',
          'Pedro',
          'Aquino',
          'Jr.',
          1
      );

INSERT INTO bank_accounts (
    account_number,
    account_holder_name,
    current_balance,
    created_by_employee
) VALUES
      (
          'ACC-C1A7C04B',
          'Alice Dela Cruz',
          25500.00,
          '12345678'
      ),
      (
          'ACC-4B2C7E9D',
          'Bob Macatangay',
          150000.75,
          '12345678'
      ),
      (
          'ACC-8D3E9F1A',
          'Charlie Dimaculangan',
          520.00,
          '87654321'
      ),
      (
          'ACC-F5A1E83B',
          'Diana Rose Villanueva',
          1250000.00,
          '87654321'
      ),
      (
          'ACC-6D2F9A4E',
          'Ethan Gabriel Sy',
          7500.25,
          '11223344'
      );

SELECT employee_id, first_name, last_name, is_active FROM employees;
SELECT account_number, account_holder_name, current_balance, created_by_employee FROM bank_accounts;
