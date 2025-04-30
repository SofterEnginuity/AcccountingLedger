package com.pluralsight;

import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class FinancialTracker {

    //  FileWriter writer = new FileWriter("inventory.csv");
    //  BufferedWriter bufWriter =  new BufferedWriter(fileWriter);
    //  writer.write("inventory.csv");


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

            System.out.println("Welcome to your Personal Finance Tracker");
            System.out.println("Choose an option:");
            System.out.println("D) Deposit Funds");
            System.out.println("P) Make a Payment");
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
    }

    public static void loadTransactions(String fileName) {
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(newTransaction);
            }
            bufReader.close();
            // If the file does not exist, it should be created.
            // Each line of the file represents a single transaction in the following format:
        } catch (Exception e) {
            System.out.println("There was an issue with reading the file.");
        }

    }

    private static void addDeposit(Scanner scanner) {
        try {
            System.out.println("Please enter the current date | Example: 2024-04-28");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            System.out.println("Please enter the current time | Example: HH:mm:ss");
            LocalTime time = LocalTime.parse(scanner.nextLine());
            System.out.println("Enter a brief description of the item");
            String description = scanner.nextLine();
            System.out.println("Please enter the vendor");
            String vendor = scanner.nextLine();
            System.out.println("Please enter the total amount you'd like to deposit");
            double amount = Double.parseDouble(String.valueOf(scanner.nextDouble()));
            scanner.nextLine();

            if (amount > 1) {
                System.out.println("Thank you for your deposit of $" + String.format("%.2f", amount));
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                    String line = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
                    writer.write(line);
                    writer.newLine();
                    System.out.println("Deposit recorded in transactions.csv.");
                    //catches are not working================================================================================================
                } catch (IOException e) {
                    System.out.println("Failed to write to file.");
                }
            }
            Transaction transaction = new Transaction(date, time, description, vendor, amount);
            new Transaction(date, time, description, vendor, amount);


            // The amount should be a positive number!
            // After validating the input, a new `Transaction` object should be created with the entered values.
            // The new deposit should be added to the `transactions` ArrayList.
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addPayment(Scanner scanner) {
        try {
            System.out.println("Please enter the current date | Example: 2024-04-28");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            System.out.println("Please enter the current time | Example: HH:mm:ss");
            LocalTime time = LocalTime.parse(scanner.nextLine());
            System.out.println("Enter a brief description of the item");
            String description = scanner.nextLine();
            System.out.println("Please enter the vendor");
            String vendor = scanner.nextLine();
            System.out.println("Please enter the total amount you'd like to deposit");
            double amount = Double.parseDouble(String.valueOf(scanner.nextDouble()));
            scanner.nextLine();
            if (amount >= 1) {
                System.out.println("Thank you for your payment of " + String.format("%.2f", amount));
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
                    String line = date + "|" + time + "|" + description + "|" + vendor + "-" + amount;
                    writer.write(line);
                    writer.newLine();
                    System.out.println("Payment recorded in transactions.csv.");
                    transactions.add(new Transaction(date, time, description, vendor, -amount));
                } catch (IOException e) {
                    System.out.println("Please enter a valid payment amount.");
                }
            }
//                // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
//                // The amount received should be a positive number then transformed to a negative number.
//                // After validating the input, a new `Transaction` object should be created with the entered values.
//                // The new payment should be added to the `transactions` ArrayList.
        } catch (Exception e) {
            System.out.println("error");
            ;
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
                    displayLedger(scanner);
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

    private static void displayLedger(Scanner scanner) {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
        System.out.println("Ledger:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
        System.out.println("Deposits:");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
    }

    private static void displayPayments() {
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
        System.out.println("Payments:");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
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
            LocalDate date = LocalDate.now();
            switch (input) {
                case "1":
                    System.out.println("Month to Date Report");
                    LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
                    filterTransactionsByDate(firstOfMonth);
                    break;

                case "2":
                    System.out.println("Last Month's Report");
                    LocalDate lastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    filterTransactionsByDate(lastMonth);
                    break;

                case "4":
// Generate a report for all transactions within the current year,
// including the date, time, description, vendor, and amount for each transaction.
                    System.out.println("Last Year's Report");
                    LocalDate lastYear = LocalDate.now().minusYears(1).withDayOfYear(1);
                    filterTransactionsByDate(lastYear);

                case "5":
                    System.out.println("Search by Vendor");
                    String vendor = scanner.nextLine();
                    filterTransactionsByVendor(vendor);
                    break;

                case "0":
                    running = false;
                default:
            }
        }
    }

    private static void filterTransactionsByDate(LocalDate conditionDate) {
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (conditionDate.equals(transaction.getDate())) {
                System.out.println("Transaction found: " + transaction);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for the given date: " + conditionDate);
        }
    }

    //cannot get to print to the console
    private static void filterTransactionsByVendor(String vendor) {
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (vendor.equalsIgnoreCase(transaction.getVendor())) {
                System.out.println(transaction);  // Print matching transactions
                found = true;
            }
        }

        // If no matching transaction was found, notify the user
        if (!found) {
            System.out.println("No transactions found for vendor: " + vendor);
        }
    }
    }

//}
//        boolean dateFound = false;
//        try {
//            String file = "transactions.csv";
//            BufferedReader bufReader = new BufferedReader(new FileReader(file));
//            String line = bufReader.readLine(); // Skip header
//            while ((line = bufReader.readLine()) != null) {
//                System.out.println("Please enter dates to search | Example: YYYY-MM-DD");
//                System.out.print("Start Date");
//                System.out.print("End Date");
//
//                date = currentDate.withDayOfMonth(1);
//                String[] parts = line.split("\\|");
//                date = LocalDate.parse(parts[0].trim());
//                LocalTime time = LocalTime.parse(parts[1].trim());
//                String description = parts[2];
//                String vendor = parts[3];
//                String amount = parts[4].trim();
//                if ((date.isEqual(date) || date.isAfter(date)) &&
//                        (date.isEqual(endDate) || date.isBefore(endDate))) {
//                    System.out.println(line);
//                    dateFound = true;
//                }
//            }

//
//            bufReader.close();
//            if (!dateFound) {
//                System.out.println("There were no dates found in that range.");
//            }
//        } catch (Exception e) {
//            System.out.println("There was an issue with reading the file or parsing dates.");
//        }
//    }
//}


// This method filters the transactions by vendor and prints a report to the console.

// The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
// Transactions with a matching vendor name are printed to the console.
// If no transactions match the specified vendor name, the method prints a message indicating that there are no results.


//Bank Deposit
//double userBalance = 0;
//double userPayment = scanner.nextDouble();
//userBalance -= userPayment;
// try catch not working on some - test al


