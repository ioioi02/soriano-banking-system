package org.banking.service;

import org.banking.config.DatabaseConnection;
import org.banking.dao.BankAccountDAO;
import org.banking.dao.BankTransactionDAO;
import org.banking.model.BankAccount;
import org.banking.model.BankTransaction;
import org.banking.model.BankTransactionType;
import org.banking.util.DateTimeUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class BankTransactionService {

    private static final int MINI_STATEMENT_LIMIT = 5;

    private final BankAccountDAO bankAccountDAO;
    private final BankTransactionDAO bankTransactionDAO;

    public BankTransactionService(BankAccountDAO bankAccountDAO, BankTransactionDAO bankTransactionDAO) {
        this.bankAccountDAO = bankAccountDAO;
        this.bankTransactionDAO = bankTransactionDAO;
    }

    public void deposit(String accountNumber, BigDecimal depositAmount) {
        try {
            BankAccount bankAccount = bankAccountDAO.findBankAccountByAccountNumber(accountNumber);

            BigDecimal newBalance = bankAccount.getCurrentBalance().add(depositAmount);

            String generatedReferenceNumber = "REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            BankTransaction bankTransaction = new BankTransaction(
                    null,
                    bankAccount.getAccountNumber(),
                    BankTransactionType.DEPOSIT,
                    depositAmount,
                    newBalance,
                    generatedReferenceNumber,
                    "Cash Deposit",
                    Instant.now());

            try (Connection connection = DatabaseConnection.getConnection()) {
                try {
                    connection.setAutoCommit(false);

                    bankAccountDAO.updateCurrentBalance(connection, bankAccount.getAccountNumber(), newBalance);
                    bankTransactionDAO.saveBankTransaction(connection, bankTransaction);

                    connection.commit();

                    System.out.println("Cash deposit successful.");
                    bankTransactionReceipt(bankTransaction, bankAccount.getAccountHolderName());
                } catch (SQLException e) {
                    try {
                        connection.rollback();
                        System.out.println("[INFO] Transaction rolled back successfully.");
                    } catch (SQLException rollbackEx) {
                        System.err.println("[ERROR] Failed to rollback: " + rollbackEx.getMessage());
                    }
                    throw e;
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to process deposit. Please check your connection or try again later.\n");
        }
    }

    public void withdraw(String accountNumber, BigDecimal withdrawAmount) {
        try {
            BankAccount bankAccount = bankAccountDAO.findBankAccountByAccountNumber(accountNumber);

            if (bankAccount.getCurrentBalance().compareTo(withdrawAmount) < 0) {
                System.out.println("Transaction failed: Insufficient balance.\n");
                return;
            }

            BigDecimal newBalance = bankAccount.getCurrentBalance().subtract(withdrawAmount);

            String generatedReferenceNumber = "REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            BankTransaction bankTransaction = new BankTransaction(
                    null,
                    bankAccount.getAccountNumber(),
                    BankTransactionType.WITHDRAW,
                    withdrawAmount,
                    newBalance,
                    generatedReferenceNumber,
                    "Cash Withdraw",
                    Instant.now());

            try (Connection connection = DatabaseConnection.getConnection()) {
                try {
                    connection.setAutoCommit(false);

                    bankAccountDAO.updateCurrentBalance(connection, bankAccount.getAccountNumber(), newBalance);
                    bankTransactionDAO.saveBankTransaction(connection, bankTransaction);

                    connection.commit();

                    System.out.println("Cash withdraw successful.");
                    bankTransactionReceipt(bankTransaction, bankAccount.getAccountHolderName());
                } catch (SQLException e) {
                    try {
                        connection.rollback();
                        System.out.println("[INFO] Transaction rolled back successfully.");
                    } catch (SQLException rollbackEx) {
                        System.err.println("[ERROR] Failed to rollback: " + rollbackEx.getMessage());
                    }
                    throw e;
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to process withdraw. Please check your connection or try again later.\n");
        }
    }

    public void moneyTransfer(String senderAccountNumber, String receiverAccountNumber, BigDecimal transferAmount) {
        try {
            BankAccount senderBankAccount = bankAccountDAO.findBankAccountByAccountNumber(senderAccountNumber);
            BankAccount receiverBankAccount = bankAccountDAO.findBankAccountByAccountNumber(receiverAccountNumber);

            if (senderBankAccount.getCurrentBalance().compareTo(transferAmount) < 0) {
                System.out.println("Transaction failed: Insufficient balance.\n");
                return;
            }

            BigDecimal senderNewBalance = senderBankAccount.getCurrentBalance().subtract(transferAmount);
            BigDecimal receiverNewBalance = receiverBankAccount.getCurrentBalance().add(transferAmount);

            String senderGeneratedReferenceNumber = "REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            String receiverGeneratedReferenceNumber = "REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            BankTransaction bankTransactionTransferOut = new BankTransaction(
                    null,
                    senderBankAccount.getAccountNumber(),
                    BankTransactionType.TRANSFER_OUT,
                    transferAmount,
                    senderNewBalance,
                    senderGeneratedReferenceNumber,
                    "Transfer to " + receiverBankAccount.getAccountNumber(),
                    Instant.now());

            BankTransaction bankTransactionTransferIn = new BankTransaction(
                    null,
                    receiverBankAccount.getAccountNumber(),
                    BankTransactionType.TRANSFER_IN,
                    transferAmount,
                    receiverNewBalance,
                    receiverGeneratedReferenceNumber,
                    "Transfer from " + senderBankAccount.getAccountNumber(),
                    Instant.now());

            try (Connection connection = DatabaseConnection.getConnection()) {
                try {
                    connection.setAutoCommit(false);

                    bankAccountDAO.updateCurrentBalance(connection, senderBankAccount.getAccountNumber(), senderNewBalance);
                    bankAccountDAO.updateCurrentBalance(connection, receiverBankAccount.getAccountNumber(), receiverNewBalance);

                    bankTransactionDAO.saveBankTransaction(connection, bankTransactionTransferOut);
                    bankTransactionDAO.saveBankTransaction(connection, bankTransactionTransferIn);

                    connection.commit();

                    System.out.println("Money transfer successful.\n");
                } catch (SQLException e) {
                    try {
                        connection.rollback();
                        System.out.println("[INFO] Transaction rolled back successfully.");
                    } catch (SQLException rollbackEx) {
                        System.err.println("[ERROR] Failed to rollback: " + rollbackEx.getMessage());
                    }
                    throw e;
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to process money transfer. Please check your connection or try again later.\n");
        }
    }

    public void transactionHistory(String accountNumber) {
        try {
            List<BankTransaction> bankTransactions = bankTransactionDAO.findBankTransactions(accountNumber);

            if (bankTransactions.isEmpty()) {
                System.out.println("No bank transactions found.\n");
                return;
            }

            System.out.printf("""
                \n==========================================
                           [Bank Transactions List]
                ==========================================
                Account No  : %s
                %-10s | %-12s | %-7s | %-12s | %-12s | %-12s
                """,
                    accountNumber, "Date Time", "Ref No", "Type", "Amount", "Balance After", "Remarks");
            for (BankTransaction bankTransaction : bankTransactions) {
                System.out.printf("""
                %-8s | %-10s | %-5s | ₱%,-10.2f | ₱%,-10.2f | %-10s
                """,
                        DateTimeUtil.formatToLocal(bankTransaction.getCreated_at()),
                        bankTransaction.getReferenceNumber(),
                        bankTransaction.getTransactionType(),
                        bankTransaction.getMonetaryAmount(),
                        bankTransaction.getBalanceAfter(),
                        bankTransaction.getRemarks());
            }
            System.out.println("""
                ==========================================
                """);
        } catch (SQLException e) {
            System.out.println("Unable to retrieve bank transactions. Please check your connection or try again later.\n");
        }
    }

    public void miniStatement(String accountNumber) {
        try {
            List<BankTransaction> bankTransactions = bankTransactionDAO.findBankTransactions(accountNumber, MINI_STATEMENT_LIMIT);

            if (bankTransactions.isEmpty()) {
                System.out.println("No bank transactions found.\n");
                return;
            }

            System.out.printf("""
                \n==========================================
                               [Mini Statement]
                ==========================================
                Account No  : %s
                %-10s | %-12s | %-7s | %-12s | %-12s | %-12s
                """,
                    accountNumber, "Date Time", "Ref No", "Type", "Amount", "Balance After", "Remarks");
            for (BankTransaction bankTransaction : bankTransactions) {
                System.out.printf("""
                %-8s | %-10s | %-5s | ₱%,-10.2f | ₱%,-10.2f | %-10s
                """,
                        DateTimeUtil.formatToLocal(bankTransaction.getCreated_at()),
                        bankTransaction.getReferenceNumber(),
                        bankTransaction.getTransactionType(),
                        bankTransaction.getMonetaryAmount(),
                        bankTransaction.getBalanceAfter(),
                        bankTransaction.getRemarks());
            }
            System.out.println("""
                ==========================================
                """);
        } catch (SQLException e) {
            System.out.println("Unable to retrieve bank transactions. Please check your connection or try again later.\n");
        }
    }

    private void bankTransactionReceipt(BankTransaction bankTransaction, String accountHolderName) {
        System.out.printf("""
                \n==========================================
                            TRANSACTION RECEIPT
                ==========================================
                Reference No    : %s
                Date Time       : %s
                Type            : %s
                
                Account No      : %s
                Account Holder  : %s
                ------------------------------------------
                Amount          : ₱%,.2f
                Balance After   : ₱%,.2f
                ------------------------------------------
                Remarks         : %s
                ==========================================
                \n""",
                bankTransaction.getReferenceNumber(),
                DateTimeUtil.formatToLocal(bankTransaction.getCreated_at()),
                bankTransaction.getTransactionType(),
                bankTransaction.getAccountNumber(),
                accountHolderName,
                bankTransaction.getMonetaryAmount(),
                bankTransaction.getBalanceAfter(),
                bankTransaction.getRemarks());
    }
}