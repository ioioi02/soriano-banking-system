package org.banking.dao;

import org.banking.model.BankAccount;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface BankAccountDAO {

    void createBankAccount(BankAccount bankAccount) throws SQLException;

    BankAccount findBankAccountByAccountNumber(String accountNumber) throws SQLException;

    void updateCurrentBalance(String accountNumber, BigDecimal currentBalance) throws SQLException;

    List<BankAccount> findAllBankAccounts() throws SQLException;
}