package org.banking.dao.impl;

import org.banking.config.DatabaseConnection;
import org.banking.dao.BankTransactionDAO;
import org.banking.model.BankTransaction;
import org.banking.model.BankTransactionType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BankTransactionDAOImpl implements BankTransactionDAO {

    private static final String INSERT_BANK_TRANSACTION =
            "INSERT INTO bank_transactions (account_number, transaction_type, monetary_amount, balance_after, reference_number, remarks) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BANK_TRANSACTIONS =
            "SELECT id, account_number, transaction_type, monetary_amount, balance_after, reference_number, remarks, created_at FROM bank_transactions WHERE account_number = ? ORDER BY created_at DESC";

    private static final String SELECT_BANK_TRANSACTIONS_MINI =
            "SELECT id, account_number, transaction_type, monetary_amount, balance_after, reference_number, remarks, created_at FROM bank_transactions WHERE account_number = ? ORDER BY created_at DESC LIMIT ?";

    @Override
    public void saveBankTransaction(BankTransaction bankTransaction) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BANK_TRANSACTION, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, bankTransaction.getAccountNumber());
            preparedStatement.setString(2, bankTransaction.getTransactionType().name());
            preparedStatement.setBigDecimal(3, bankTransaction.getMonetaryAmount());
            preparedStatement.setBigDecimal(4, bankTransaction.getBalanceAfter());
            preparedStatement.setString(5, bankTransaction.getReferenceNumber());
            preparedStatement.setString(6, bankTransaction.getRemarks());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    bankTransaction.setId(generatedKey.getLong(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to save bank transaction for Account Number '" + bankTransaction.getAccountNumber() + "': " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void saveBankTransaction(Connection connection,BankTransaction bankTransaction) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BANK_TRANSACTION, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, bankTransaction.getAccountNumber());
            preparedStatement.setString(2, bankTransaction.getTransactionType().name());
            preparedStatement.setBigDecimal(3, bankTransaction.getMonetaryAmount());
            preparedStatement.setBigDecimal(4, bankTransaction.getBalanceAfter());
            preparedStatement.setString(5, bankTransaction.getReferenceNumber());
            preparedStatement.setString(6, bankTransaction.getRemarks());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    bankTransaction.setId(generatedKey.getLong(1));
                }
            }
        }
    }

    @Override
    public List<BankTransaction> findBankTransactions(String accNumber) throws SQLException {
        List<BankTransaction> bankTransactions = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BANK_TRANSACTIONS)) {

            preparedStatement.setString(1, accNumber);

            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    String accountNumber = result.getString("account_number");
                    String transactionType = result.getString("transaction_type");
                    BigDecimal monetaryAmount = result.getBigDecimal("monetary_amount");
                    BigDecimal balanceAfter = result.getBigDecimal("balance_after");
                    String referenceNumber = result.getString("reference_number");
                    String remarks = result.getString("remarks");
                    Instant created_at = result.getTimestamp("created_at").toInstant();

                    BankTransactionType bankTransactionType = BankTransactionType.valueOf(transactionType.toUpperCase());

                    BankTransaction bankTransaction = new BankTransaction(id, accountNumber, bankTransactionType, monetaryAmount, balanceAfter, referenceNumber, remarks, created_at);
                    bankTransactions.add(bankTransaction);
                }

                return bankTransactions;
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to retrieve bank transactions for Account Number '" + accNumber + "': " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BankTransaction> findBankTransactions(String accNumber, int limit) throws SQLException {
        List<BankTransaction> bankTransactions = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BANK_TRANSACTIONS_MINI)) {

            preparedStatement.setString(1, accNumber);
            preparedStatement.setInt(2, limit);

            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    String accountNumber = result.getString("account_number");
                    String transactionType = result.getString("transaction_type");
                    BigDecimal monetaryAmount = result.getBigDecimal("monetary_amount");
                    BigDecimal balanceAfter = result.getBigDecimal("balance_after");
                    String referenceNumber = result.getString("reference_number");
                    String remarks = result.getString("remarks");
                    Instant created_at = result.getTimestamp("created_at").toInstant();

                    BankTransactionType bankTransactionType = BankTransactionType.valueOf(transactionType.toUpperCase());

                    BankTransaction bankTransaction = new BankTransaction(id, accountNumber, bankTransactionType, monetaryAmount, balanceAfter, referenceNumber, remarks, created_at);
                    bankTransactions.add(bankTransaction);
                }

                return bankTransactions;
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to retrieve bank transactions for Account Number '" + accNumber + "': " + e.getMessage());
            throw e;
        }
    }
}