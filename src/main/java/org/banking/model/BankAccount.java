package org.banking.model;

import java.math.BigDecimal;
import java.time.Instant;

public class BankAccount {

    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal currentBalance;
    private String createdByEmployee;
    private Instant created_at;
    private Instant updated_at;

    public BankAccount(Long id, String accountNumber, String accountHolderName, BigDecimal currentBalance, String createdByEmployee, Instant created_at, Instant updated_at) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.currentBalance = currentBalance;
        this.createdByEmployee = createdByEmployee;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getCreatedByEmployee() {
        return createdByEmployee;
    }

    public void setCreatedByEmployee(String createdByEmployee) {
        this.createdByEmployee = createdByEmployee;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }
}