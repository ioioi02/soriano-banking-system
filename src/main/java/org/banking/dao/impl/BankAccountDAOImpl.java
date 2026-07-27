package org.banking.dao.impl;

import org.banking.config.DatabaseConnection;
import org.banking.dao.BankAccountDAO;
import org.banking.model.BankAccount;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDAOImpl implements BankAccountDAO {

    private static final String INSERT_BANK_ACCOUNT =
            "INSERT INTO bank_accounts (account_number, account_holder_name, current_balance, created_by_employee) VALUES (?, ?, ?, ?)";

    private static final String SELECT_BANK_ACCOUNT_BY_ACCOUNT_NUMBER =
            "SELECT id, account_number, account_holder_name, current_balance, created_by_employee, created_at, updated_at FROM bank_accounts WHERE account_number = ?";

    private static final String UPDATE_BANK_ACCOUNT_CURRENT_BALANCE =
            "UPDATE bank_accounts SET current_balance = ? WHERE account_number = ?";

    private static final String SELECT_ALL_BANK_ACCOUNTS =
            "SELECT id, account_number, account_holder_name, current_balance, created_by_employee, created_at, updated_at FROM bank_accounts ORDER BY created_at DESC";

    @Override
    public void createBankAccount(BankAccount bankAccount) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BANK_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, bankAccount.getAccountNumber());
            preparedStatement.setString(2, bankAccount.getAccountHolderName());
            preparedStatement.setBigDecimal(3, bankAccount.getCurrentBalance());
            preparedStatement.setString(4, bankAccount.getCreatedByEmployee());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    bankAccount.setId(generatedKey.getLong(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to create bank account: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public BankAccount findBankAccountByAccountNumber(String accNumber) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BANK_ACCOUNT_BY_ACCOUNT_NUMBER)) {

            preparedStatement.setString(1, accNumber);

            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    Long id = result.getLong("id");
                    String accountNumber = result.getString("account_number");
                    String accountHolderName = result.getString("account_holder_name");
                    BigDecimal currentBalance = result.getBigDecimal("current_balance");
                    String createdByEmployee = result.getString("created_by_employee");
                    Instant created_at = result.getTimestamp("created_at").toInstant();
                    Instant updated_at = result.getTimestamp("updated_at").toInstant();

                    return new BankAccount(id, accountNumber, accountHolderName, currentBalance, createdByEmployee, created_at, updated_at);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to retrieve bank account for Account Number '" + accNumber + "': " + e.getMessage());
            throw e;
        }

        return null;
    }

    @Override
    public void updateCurrentBalance(String accNumber, BigDecimal currentBalance) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BANK_ACCOUNT_CURRENT_BALANCE)) {

            preparedStatement.setBigDecimal(1, currentBalance);
            preparedStatement.setString(2, accNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to update current balance for Account Number '" + accNumber + "': " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateCurrentBalance(Connection connection, String accNumber, BigDecimal currentBalance) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BANK_ACCOUNT_CURRENT_BALANCE)) {

            preparedStatement.setBigDecimal(1, currentBalance);
            preparedStatement.setString(2, accNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Current balance update affected 0 rows for account: " + accNumber);
            }
        }
    }

    @Override
    public List<BankAccount> findAllBankAccounts() throws SQLException {
        List<BankAccount> bankAccounts = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BANK_ACCOUNTS);
             ResultSet result = preparedStatement.executeQuery()) {

            while (result.next()) {
                Long id = result.getLong("id");
                String accountNumber = result.getString("account_number");
                String accountHolderName = result.getString("account_holder_name");
                BigDecimal currentBalance = result.getBigDecimal("current_balance");
                String createdByEmployee = result.getString("created_by_employee");
                Instant created_at = result.getTimestamp("created_at").toInstant();
                Instant updated_at = result.getTimestamp("updated_at").toInstant();

                BankAccount bankAccount = new BankAccount(id, accountNumber, accountHolderName, currentBalance, createdByEmployee, created_at, updated_at);
                bankAccounts.add(bankAccount);
            }

            return bankAccounts;
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to retrieve all bank accounts: " + e.getMessage());
            throw e;
        }
    }
}