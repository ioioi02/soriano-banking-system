package org.banking.model;

import java.math.BigDecimal;

public class BankTransaction {

    private Integer id;
    private String accountNumber;
    private BankTransactionType transactionType;
    private BigDecimal monetaryAmount;
    private BigDecimal balanceAfter;
    private String referenceNumber;
    private String remarks;

    public BankTransaction(Integer id, String accountNumber, BankTransactionType transactionType, BigDecimal monetaryAmount, BigDecimal balanceAfter, String referenceNumber, String remarks) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.monetaryAmount = monetaryAmount;
        this.balanceAfter = balanceAfter;
        this.referenceNumber = referenceNumber;
        this.remarks = remarks;
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

    public BankTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(BankTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getMonetaryAmount() {
        return monetaryAmount;
    }

    public void setMonetaryAmount(BigDecimal monetaryAmount) {
        this.monetaryAmount = monetaryAmount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}