CREATE DATABASE IF NOT EXISTS soriano_banking_system
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE soriano_banking_system;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS employees (
    id              INT             NOT NULL AUTO_INCREMENT,
    employee_id     VARCHAR(20)     NOT NULL,
    password        VARCHAR(255)    NOT NULL,
    last_name       VARCHAR(50)     NOT NULL,
    first_name      VARCHAR(50)     NOT NULL,
    middle_name     VARCHAR(50)     DEFAULT NULL,
    suffix          VARCHAR(20)     DEFAULT NULL,
    is_active       TINYINT(1)      NOT NULL DEFAULT 1,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY (employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS bank_accounts (
    id                  INT             NOT NULL AUTO_INCREMENT,
    account_number      VARCHAR(20)     NOT NULL,
    account_holder_name VARCHAR(150)    NOT NULL,
    current_balance     DECIMAL(15, 2)  NOT NULL DEFAULT 0.00,
    created_by_employee VARCHAR(20)     NOT NULL,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY (account_number),
    KEY (created_by_employee),
    CONSTRAINT fk_bank_accounts_created_by_employee 
        FOREIGN KEY (created_by_employee) REFERENCES employees (employee_id) 
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS bank_transactions (
    id               INT                                                        NOT NULL AUTO_INCREMENT,
    account_number   VARCHAR(20)                                                NOT NULL,
    transaction_type ENUM('DEPOSIT', 'WITHDRAW', 'TRANSFER_IN', 'TRANSFER_OUT') NOT NULL,
    monetary_amount  DECIMAL(15, 2)                                             NOT NULL,
    balance_after    DECIMAL(15, 2)                                             NOT NULL,
    reference_number VARCHAR(40)                                                NOT NULL,
    remarks          VARCHAR(255)                                               DEFAULT NULL,
    created_at       TIMESTAMP                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY (account_number),
    CONSTRAINT fk_bank_transactions_account_number 
        FOREIGN KEY (account_number) REFERENCES bank_accounts (account_number) 
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
