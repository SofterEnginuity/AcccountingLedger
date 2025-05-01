package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


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
//always shows there was an error reading file
    public static void loadTransactions(String fileName) {
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0], DATE_FORMATTER);
                LocalTime time = LocalTime.parse(parts[1], TIME_FORMATTER);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(newTransaction);
            }
            bufReader.close();
        } catch (Exception e) {
            System.out.println("There was an issue with reading the file.");
        }
    }

    private static void addDeposit(Scanner scanner) {

        System.out.println("Please enter the current date | Example: 2024-04-28");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
        System.out.println("Please enter the current time | Example: HH:mm:ss");
        LocalTime time = LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);
        System.out.println("Enter a brief description of the item");
        String description = scanner.nextLine();
        System.out.println("Please enter the vendor");
        String vendor = scanner.nextLine();
        System.out.println("Please enter the total amount you'd like to deposit");
        double amount = Double.parseDouble(String.valueOf(scanner.nextDouble()));
        scanner.nextLine();

        if (amount > 1) {
            transactions.add(new Transaction(date, time, description, vendor, amount));
            System.out.println("Thank you for your deposit of $" + String.format("%.2f", amount));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                String line = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
                writer.write(line);
                writer.newLine();
                System.out.println("Deposit recorded in transactions.csv");


//catches are not working
            } catch (IOException e) {
                System.out.println("Failed to write to file.");
            }
        }

        // The new deposit should be added to the `transactions` ArrayList.
    }

    private static void addPayment(Scanner scanner) {

        System.out.println("Please enter the current date | Example: 2024-04-28");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
        System.out.println("Please enter the current time | Example: HH:mm:ss");
        LocalTime time = LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);
        System.out.println("Enter a brief description of the item");
        String description = scanner.nextLine();
        System.out.println("Please enter the vendor");
        String vendor = scanner.nextLine();
        System.out.println("Please enter the total amount of your purchase or payment");
        double amount = Double.parseDouble(String.valueOf(scanner.nextDouble()));

        scanner.nextLine();
        if (amount >= 1) {
            amount = amount * -1;
            System.out.println("Thank you for your payment of " + String.format("%.2f", amount));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
                String line = date + "|" + time + "|" + description + "|" + vendor + amount;
                writer.write(line);
                writer.newLine();
                System.out.println("Payment recorded in transactions.csv");
                transactions.add(new Transaction(date, time, description, vendor, amount));
            } catch (IOException e) {
                System.out.println("Please enter a valid payment amount");
            }
        }
//                // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
//                // The amount received should be a positive number then transformed to a negative number.
//                // After validating the input, a new `Transaction` object should be created with the entered values.
//                // The new payment should be added to the `transactions` ArrayList.

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


//System.out.printf("%-12s %-10s %-30s %-20s %-10s%n", "Date", "Time", "Description", "Vendor", "Amount");
    private static void displayLedger(Scanner scanner) {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
        System.out.println("Ledger:");
        transactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        for (Transaction transaction : transactions) {
            System.out.printf("%-12s %-10s %-30s %-20s %-10.2f%n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());
        }
    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
        System.out.println("Deposits:");
        transactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
                System.out.printf("%-12s %-10s %-30s %-20s %-10.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }

    private static void displayPayments() {
        transactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        System.out.println("Payments:");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.printf("%-12s %-10s %-30s %-20s %-10.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
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
//need readme
//start/end dates test
                case "1":
                    System.out.println("Month to Date Report");
                    LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
                    filterTransactionsByDate(firstOfMonth, date);
                    break;

// need readme
//start/end dates test
                case "2":
                    System.out.println("Last Month's Report");
                    LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate endDate = LocalDate.now().withDayOfMonth(1).minusDays(1);
                    filterTransactionsByDate(startDate, endDate);
                    break;

// works fine
                case "3":
                    System.out.println("Year to Date Report");
                    LocalDate today = LocalDate.now();
                    LocalDate firstOfYear = today.withDayOfYear(1);
                    filterTransactionsByDate(firstOfYear, today);
                    break;

//works fine
                case "4":
                    System.out.println("Last Year's Report");
                    LocalDate lastYear = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate lastDayLastYear = LocalDate.now().withDayOfYear(1).minusDays(1);
                    filterTransactionsByDate(lastYear, lastDayLastYear);
                    break;
//works fine
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

//need to account for the start date and the end dates
    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        transactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (transaction.getDate().isAfter(startDate) && transaction.getDate().isBefore(endDate)) {
//             System.out.println(transaction);
                System.out.printf("%-12s %-10s %-30s %-20s %-10.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
                        found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found!");
        }
    }

//There was an issue reading the file
    private static void filterTransactionsByVendor(String vendor) {
        transactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (vendor.equalsIgnoreCase(transaction.getVendor())) {
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for vendor: " + vendor);
        }
    }
    }
