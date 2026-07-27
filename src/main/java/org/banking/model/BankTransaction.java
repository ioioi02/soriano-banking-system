package org.banking.model;

import java.math.BigDecimal;
import java.time.Instant;

public class BankTransaction {

    private Long id;
    private String accountNumber;
    private BankTransactionType transactionType;
    private BigDecimal monetaryAmount;
    private BigDecimal balanceAfter;
    private String referenceNumber;
    private String remarks;
    private Instant created_at;

    public BankTransaction(Long id, String accountNumber, BankTransactionType transactionType, BigDecimal monetaryAmount, BigDecimal balanceAfter, String referenceNumber, String remarks, Instant created_at) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.monetaryAmount = monetaryAmount;
        this.balanceAfter = balanceAfter;
        this.referenceNumber = referenceNumber;
        this.remarks = remarks;
        this.created_at = created_at;
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

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
}