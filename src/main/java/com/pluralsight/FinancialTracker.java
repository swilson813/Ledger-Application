package com.pluralsight;


import javax.naming.Name;
import javax.sound.sampled.Line;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static com.sun.imageio.plugins.jpeg.JPEG.vendor;

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


        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 5) {
                        LocalDate date = LocalDate.parse(parts[0], DATE_FORMATTER);
                        LocalTime time = LocalTime.parse(parts[1], TIME_FORMATTER);
                        String description = parts[2];
                        String vendor = parts[3].trim();
                        Double amount = Double.parseDouble(parts[4]);
                        transactions.add(new Transaction(date,time,description,vendor,amount));
                    }

                }
                reader.close();
            }

        } catch (Exception e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }



    private static void addDeposit(Scanner scanner) {
        //User input for date, time, description, vendor & amount
        System.out.println("Enter the date yyyy-MM-dd: ");
        String dateInput = scanner.nextLine();

        LocalDate date = LocalDate.parse(dateInput, DATE_FORMATTER);
        System.out.println("Enter the time HH:mm:ss: ");
        String timeInput = scanner.nextLine();

        LocalTime time = LocalTime.parse(timeInput, TIME_FORMATTER);
        System.out.println("Enter the description: ");
        String description = scanner.nextLine();

        System.out.println("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter the positive amount: $ ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        if (amount < 0) {

            System.out.println("Invalid input! Try again!");
            System.out.println("\n Enter the positive amount: $ ");

        }
        try {
            Transaction newDeposit = new Transaction(date, time, description, vendor, amount);
            transactions.add(newDeposit);

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            String outputLine = newDeposit.getDate() + "|" + newDeposit.getTime() + "|" + newDeposit.getDescription() + "|" + newDeposit.getVendor() + "|" + newDeposit.getAmount();
            writer.write("\n" + outputLine);
            writer.close();

        } catch (IOException e) {
            System.out.println("ERROR! Try again!");
        }


    }

    private static void addPayment(Scanner scanner) {

        System.out.println("Enter the date yyyy-MM-dd: ");
        String date = scanner.nextLine();
        LocalDate formattedDate = LocalDate.parse(date, DATE_FORMATTER);

        System.out.println("Enter the time HH:mm:ss: ");
        String time = scanner.nextLine();
        LocalTime formattedTime = LocalTime.parse(time, TIME_FORMATTER);

        System.out.println("Enter the description: ");
        String description = scanner.nextLine();

        System.out.println("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter a positive payment amount: $ ");
        double amount = scanner.nextDouble();
        scanner.nextLine();


        amount = amount * -1;
        try {
            Transaction newPayment = new Transaction(formattedDate, formattedTime, description, vendor, amount);
            transactions.add(newPayment);

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            String outputLine = newPayment.getDate() + "|" + newPayment.getTime() + "|" + newPayment.getDescription() + "|" + newPayment.getVendor() + "|"  + newPayment.getAmount();
            writer.write("\n" + outputLine);
            writer.close();
        } catch (IOException e) {
            System.out.println("ERROR! Try again!");
        }
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
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {

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


            switch (input) {
                case "1":
                    filterTransactionsByDate(LocalDate.now().withDayOfMonth(1), LocalDate.now());
                    break;
                case "2":
                    filterTransactionsByDate(LocalDate.now().withDayOfMonth(1).minusMonths(1).withDayOfMonth(1), LocalDate.now().withDayOfMonth(1).minusDays(1));
                    break;
                case "3":
                    filterTransactionsByDate(LocalDate.now().withDayOfYear(1), LocalDate.now().withMonth(12).withDayOfMonth(31));
                    break;
                case "4":
                    filterTransactionsByDate(LocalDate.now().minusYears(1).withDayOfYear(1), LocalDate.now().withMonth(12).withDayOfMonth(31));
                    break;
                case "5":
                    filterTransactionsByVendor(vendor);
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
                    return;
                }

            }

            System.out.println("No transactions connected to this vendor");
        } catch (Exception e) {
            System.out.println("Something went wrong! Try again!");

        }
    }
}
