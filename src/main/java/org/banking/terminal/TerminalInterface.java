package org.banking.terminal;

import org.banking.dao.BankAccountDAO;
import org.banking.dao.BankTransactionDAO;
import org.banking.dao.EmployeeDAO;
import org.banking.dao.impl.BankAccountDAOImpl;
import org.banking.dao.impl.BankTransactionDAOImpl;
import org.banking.dao.impl.EmployeeDAOImpl;
import org.banking.dto.BankAccountCreationDetail;
import org.banking.dto.EmployeeCredential;
import org.banking.model.Employee;
import org.banking.service.BankAccountService;
import org.banking.service.BankTransactionService;
import org.banking.service.EmployeeService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;

public class TerminalInterface {

    private final Scanner scanner;

    private final EmployeeService employeeService;
    private final BankAccountService bankAccountService;
    private final BankTransactionService bankTransactionService;

    private Employee currentSession = null;
    private boolean isRunning;

    public TerminalInterface() {
        scanner = new Scanner(System.in);

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        employeeService = new EmployeeService(employeeDAO);

        BankAccountDAO bankAccountDAO = new BankAccountDAOImpl();
        bankAccountService = new BankAccountService(bankAccountDAO);

        BankTransactionDAO bankTransactionDAO = new BankTransactionDAOImpl();
        bankTransactionService = new BankTransactionService(bankAccountDAO, bankTransactionDAO);
    }

    public void start() {
        this.isRunning = true;

        while (isRunning) {
            if (currentSession == null) {
                welcomeMenuAction();
            } else {
                dashboardMenuAction();
            }
        }
    }

    private void welcomeMenuAction() {
        printWelcomeMenu();

        System.out.print("Enter action: ");
        String action = scanner.nextLine();

        switch (action) {
            case "1" -> processLogin();
            case "2" -> shutdownTerminal();
            default -> invalidInputMessage();
        }
    }

    private void dashboardMenuAction() {
        printDashboardMenu();

        System.out.print("Enter action: ");
        String action = scanner.nextLine();

        switch (action) {
            case "1" -> bankAccountService.createBankAccount(promptBankAccountCreationDetailRequest());
            case "2" -> bankAccountService.currentBalanceInquiry(promptAccountNumberInput());
            case "3" -> bankTransactionService.deposit(promptAccountNumberInput(), promptPositiveAmountInput());
            case "4" -> bankTransactionService.withdraw(promptAccountNumberInput(), promptPositiveAmountInput());
            case "5" -> bankTransactionService.moneyTransfer(promptAccountNumberInput("sender"), promptAccountNumberInput("receiver"), promptPositiveAmountInput());
            case "6" -> bankTransactionService.transactionHistory(promptAccountNumberInput());
            case "7" -> bankTransactionService.miniStatement(promptAccountNumberInput());
            case "8" -> bankAccountService.listAllBankAccounts();
            case "9" -> employeeLogout();
            default -> invalidInputMessage();
        }
    }

    private void processLogin() {
        printLoginHeader();

        EmployeeCredential employeeCredential = promptLoginRequest();

        try {
            Employee employee = employeeService.authenticateEmployee(employeeCredential.employeeId(), employeeCredential.securityPassword());
            if (employee != null) {
                this.currentSession = employee;
                System.out.println("Logged in successfully..\n");
            } else {
                System.out.println("Invalid employee id or security password.\n");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to the server. Please check your connection or try again later.\n");
        }
    }

    private EmployeeCredential promptLoginRequest() {
        System.out.print("Enter employee Id: ");
        String employeeId = scanner.nextLine();

        System.out.print("Enter security password: ");
        String securityPassword = scanner.nextLine();

        return new EmployeeCredential(employeeId, securityPassword);
    }

    private BankAccountCreationDetail promptBankAccountCreationDetailRequest() {
        System.out.print("Enter account holder name: ");
        String accountHolderName = scanner.nextLine();

        System.out.print("Enter initial deposit: ");
        BigDecimal initialDeposit = scanner.nextBigDecimal();
        scanner.nextLine();

        return new BankAccountCreationDetail(accountHolderName, initialDeposit, currentSession.getEmployeeId());
    }

    private String promptAccountNumberInput() {
        System.out.print("Enter account number: ");
        return scanner.nextLine();
    }

    private String promptAccountNumberInput(String label) {
        System.out.print("Enter " + label + " account number: ");
        return scanner.nextLine();
    }

    private BigDecimal promptPositiveAmountInput() {
        System.out.print("Enter positive amount: ");
        BigDecimal positiveAmount = scanner.nextBigDecimal();
        scanner.nextLine();

        return positiveAmount;
    }

    private void printWelcomeMenu() {
        System.out.print("""
                ==========================================
                          WELCOME TO SORIANOBANK
                       [Internal Management System]
                ==========================================
                [1] SorianoBank Operations Terminal
                [2] Terminal Shutdown
                """);
    }

    private void printDashboardMenu() {
        System.out.printf("""
                ==========================================
                               SORIANOBANK
                               [Dashboard]
                ==========================================
                %s
                [1] Create Account
                [2] Balance Inquiry
                [3] Deposit
                [4] Withdraw
                [5] Transfer
                [6] Transaction History
                [7] Mini Statement
                [8] List All Accounts
                [9] Logout
                """, currentSession.getfullName());
    }

    private void printLoginHeader() {
        System.out.print("""
                \n==========================================
                         [Security Authentication]
                ==========================================
                """);
    }

    private void shutdownTerminal() {
        System.out.println("Shutting down. Goodbye!");
        this.isRunning = false;
    }

    private void employeeLogout() {
        this.currentSession = null;
        System.out.println("Logged out successfully.\n");
    }

    private void invalidInputMessage() {
        System.out.println("Invalid input. Please enter option [] from the menu.\n");
    }
}