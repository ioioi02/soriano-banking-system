package org.banking.dao;

import org.banking.model.BankAccount;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BankAccountDAO {

    void createBankAccount(BankAccount bankAccount) throws SQLException;

    BankAccount findBankAccountByAccountNumber(String accNumber) throws SQLException;

    void updateCurrentBalance(String accNumber, BigDecimal currentBalance) throws SQLException;

    void updateCurrentBalance(Connection connection, String accNumber, BigDecimal currentBalance) throws SQLException;

    List<BankAccount> findAllBankAccounts() throws SQLException;
}