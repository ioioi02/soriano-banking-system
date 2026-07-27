package org.banking.dao;

import org.banking.model.BankTransaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BankTransactionDAO {

    void saveBankTransaction(BankTransaction bankTransaction) throws SQLException;

    void saveBankTransaction(Connection connection, BankTransaction bankTransaction) throws SQLException;

    List<BankTransaction> findBankTransactions(String accNumber) throws SQLException;

    List<BankTransaction> findBankTransactions(String accNumber, int limit) throws SQLException;
}