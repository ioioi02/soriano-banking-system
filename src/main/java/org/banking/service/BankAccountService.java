package org.banking.service;

import org.banking.dao.BankAccountDAO;
import org.banking.dto.BankAccountCreationDetail;
import org.banking.model.BankAccount;
import org.banking.util.DateTimeUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BankAccountService {

    private final BankAccountDAO bankAccountDAO;

    public BankAccountService(BankAccountDAO bankAccountDAO) {
        this.bankAccountDAO = bankAccountDAO;
    }

    public void createBankAccount(BankAccountCreationDetail bankAccountCreationDetail) {
        String generatedAccountNumber = "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        BankAccount bankAccount = new BankAccount(
                null,
                generatedAccountNumber,
                bankAccountCreationDetail.accountHolderName(),
                bankAccountCreationDetail.initialDeposit(),
                bankAccountCreationDetail.createdByEmployee(),
                null,
                null);

        try {
            bankAccountDAO.createBankAccount(bankAccount);
            System.out.println("Bank account created successfully.\n");
        } catch (SQLException e) {
            System.out.println("Unable to create an account. Please check your connection or try again later.\n");
        }
    }

    public void currentBalanceInquiry(String accountNumber) {
        try {
            BankAccount bankAccount = bankAccountDAO.findBankAccountByAccountNumber(accountNumber);

            if (bankAccount == null) {
                System.out.println("Invalid account number '" + accountNumber + "'.\n");
                return;
            }

            System.out.printf("""
                \n==========================================
                             [Current Balance]
                ==========================================
                Account No      : %s
                Account Holder  : %s
                Current Balance : ₱%,.2f
                \n""", bankAccount.getAccountNumber(), bankAccount.getAccountHolderName(), bankAccount.getCurrentBalance());
        } catch (SQLException e) {
            System.out.println("Unable to retrieve current balance. Please check your connection or try again later.\n");
        }
    }

    public void listAllBankAccounts() {
        try {
            List<BankAccount> bankAccounts = bankAccountDAO.findAllBankAccounts();

            if (bankAccounts.isEmpty()) {
                System.out.println("No registered bank accounts found.\n");
                return;
            }

            System.out.printf("""
                \n==========================================
                             [Bank Accounts List]
                ==========================================
                %-12s | %-22s | %-12s | %-12s
                """,
                        "Account No", "Holder Name", "Balance", "Opened By");
            for (BankAccount bankAccount : bankAccounts) {
                System.out.printf("""
                %-10s | %-20s | ₱%,-10.2f | %-10s
                """,
                        bankAccount.getAccountNumber(),
                        bankAccount.getAccountHolderName(),
                        bankAccount.getCurrentBalance(),
                        DateTimeUtil.formatToLocal(bankAccount.getCreated_at()));
            }
            System.out.println("""
                ==========================================
                """);
        } catch (SQLException e) {
            System.out.println("Unable to retrieve bank accounts. Please check your connection or try again later.\n");
        }
    }
}