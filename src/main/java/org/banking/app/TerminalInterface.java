package org.banking.app;

import org.banking.dao.EmployeeDAO;
import org.banking.dao.impl.EmployeeDAOImpl;
import org.banking.dto.EmployeeCredential;
import org.banking.model.Employee;
import org.banking.service.EmployeeService;

import java.sql.SQLException;
import java.util.Scanner;

public class TerminalInterface {

    private final Scanner scanner;

    private final EmployeeService employeeService;

    private Employee currentSession = null;
    private boolean isRunning;

    public TerminalInterface() {
        scanner = new Scanner(System.in);

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        employeeService = new EmployeeService(employeeDAO);
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
            case "1" -> {
                System.out.println();
                processLogin();
            }
            case "2" -> shutdownTerminal();
            default -> invalidInputMessage();
        }
    }

    private void dashboardMenuAction() {
        printDashboardMenu();

        System.out.print("Enter action: ");
        String action = scanner.nextLine();

        switch (action) {
            case "1" -> System.out.println("Create Account");
            case "2" -> System.out.println("Balance Inquiry");
            case "3" -> System.out.println("Deposit");
            case "4" -> System.out.println("Withdraw");
            case "5" -> System.out.println("Transfer");
            case "6" -> System.out.println("Transaction History");
            case "7" -> System.out.println("Mini Statement");
            case "8" -> System.out.println("List All Accounts");
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
                ==========================================
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