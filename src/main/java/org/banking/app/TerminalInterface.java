package org.banking.app;

import org.banking.dao.EmployeeDAO;
import org.banking.dao.impl.EmployeeDAOImpl;
import org.banking.model.Employee;
import org.banking.service.EmployeeService;
import org.banking.util.DatabaseSeeder;

import java.util.Scanner;

public class TerminalInterface {

    private final Scanner scanner;

    private final EmployeeService employeeService;
    private final DatabaseSeeder databaseSeeder;

    private Employee currentSession = null;

    public TerminalInterface() {
        scanner = new Scanner(System.in);

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();

        employeeService = new EmployeeService(employeeDAO);
        databaseSeeder = new DatabaseSeeder(employeeDAO);
    }

    public void start() {
        boolean isRunning = true;

        while (isRunning) {
            printWelcomeMenu();

            System.out.print("Enter action: ");
            String action = scanner.nextLine();

            switch (action) {
                case "1" -> {
                    System.out.println();
                    processLogin();
                }
                case "2" -> {
                    System.out.println("Shutting down. Goodbye!");
                    isRunning = false;
                }
                case "db:seed" -> {
                    databaseSeeder.seed();
                }
                default -> {
                    System.out.println("Invalid input. Please enter option [] from the menu.\n");
                }
            }
        }
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

    private void processLogin() {
        System.out.print("""
                ==========================================
                         [Security Authentication]
                ==========================================
                """);

        System.out.print("Enter employee Id: ");
        String employeeId = scanner.nextLine();

        System.out.print("Enter security password: ");
        String securityPassword = scanner.nextLine();

        Employee employee = employeeService.authenticateEmployee(employeeId, securityPassword);
        if (employee != null) {
            this.currentSession = employee;
            System.out.println("Successfully logged in.\n");
        } else {
            System.out.println("Invalid employee id or security password.\n");
        }
    }
}