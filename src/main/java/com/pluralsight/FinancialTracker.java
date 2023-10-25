package com.pluralsight;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        // This method should load transactions from a file with the given file name.
        // If the file does not exist, it should be created.
        // The transactions should be stored in the `transactions` ArrayList.
        // Each line of the file represents a single transaction in the following format:
        // <date>,<time>,<vendor>,<type>,<amount>
        // For example: 2023-04-29,13:45:00,Amazon,PAYMENT,29.99
        // After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed.

        ArrayList<Transaction> transactions = new ArrayList<>();
        //create fileName for when it exists and another if it doesnt
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                BufferedReader reader = new BufferedReader(FileReader(file));


            }

        }
    }

    String dateInput;
    String timeInput;

    private static void addDeposit(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Deposit` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.
        System.out.println("Enter the date");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern());
        System.out.println("Enter the time");
        String timeInput = scanner.nextLine();
        LocalTime time = LocalTime.parse(timeInput);
        System.out.println("Enter the description");
        String description = scanner.nextLine();
        System.out.println("Enter the vendor");
        String vendor = scanner.nextLine();
        System.out.println("Enter the amount");
        double amount = Double.parseDouble(scanner.nextLine());
        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        transactions.add(transaction);


        System.out.println("Deposit was successfully added");
        System.out.println("Would that be all? (Y/N): ");
        String response = scanner.nextLine();


    }

    private static void addPayment(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Payment` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.

        System.out.println("Enter the date YYYY-MM-DD: ");
        String date = scanner.nextLine();
        LocalDate formattedDate = LocalDate.parse(date, DATE_FORMATTER);

        System.out.println("Enter the time MM:HH:SS: ");
        String time = scanner.nextLine();
        LocalTime formattedTime = LocalTime.parse(time, DATE_FORMATTER);

        System.out.println("Enter the description: ");
        String description = scanner.nextLine();

        System.out.println("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter the payment amount: $ ");
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, type, and amount.
        System.out.println("Transactions");
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(transactions.get(i));
        }

    }

    private static void displayDeposits() {
        System.out.println("All Deposits: ");

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getAmount() > 0) {
                System.out.println(transactions.get(i));
            }
        }
    }

    private static void displayPayments() {
        System.out.println("All Payments: ");

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getAmount() < 0) {
                System.out.println(transactions.get(i));
            }
        }
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");

            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            // Generate a report for all transactions within the current month,
            // including the date, vendor, and amount for each transaction.

            switch (input) {
                case "1":
                    filterTransactionsByDate(LocalDate.now().withDayOfMonth(1), LocalDate.now());
                    break;
                case "2":

                    break;

                case "3":

                    break;

                case "4":

                    break;

                case "5":

                    break;

                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {

               System.out.println("Report:");
        for (Transaction transaction : transactions) {
            if (transaction.getDate().isAfter(startDate.minusDays(1)) && transaction.getDate().isBefore(endDate.plusDays(1))) {
                System.out.println(transaction);
            }
        }

    }

    private static void filterTransactionsByVendor(String vendor) {
                try {


            for (Transaction transaction : transactions) {
                if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                    System.out.println(transaction);

                }

            }
            System.out.println("No transactions connected to this vendor");
        } catch (Exception e) {

        }
    }
}
