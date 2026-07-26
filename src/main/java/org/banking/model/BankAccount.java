package org.banking.model;

import java.math.BigDecimal;

public class BankAccount {

    private Integer id;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal currentBalance;
    private String createdByEmployee;

    public BankAccount(Integer id, String accountNumber, String accountHolderName, BigDecimal currentBalance, String createdByEmployee) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.currentBalance = currentBalance;
        this.createdByEmployee = createdByEmployee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}