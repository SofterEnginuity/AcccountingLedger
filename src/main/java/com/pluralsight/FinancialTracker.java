package com.pluralsight;

import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

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

        //        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println("Welcome to your Personal Finance Tracker");
            System.out.println("Choose an option:");
            System.out.println("D) Deposit Funds");
            System.out.println("P) Make a Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
//is Ledger Case L the same output as Case A - all?
            String input = scanner.nextLine().trim();
            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    loadTransactions(FILE_NAME);

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
        //scanner.close();
    }


//is this all transactions? Does this need to be in order?
    public static void loadTransactions(String fileName) {
        try {
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            String file = "transactions.csv";
            BufferedReader bufReader = new BufferedReader(new FileReader(file));
            String line = bufReader.readLine();
            while ((line = bufReader.readLine()) != null) {
                System.out.println(line);
                String[] parts = line.split("\\|");
                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                String amount = parts[4];
            }
            bufReader.close();
            // If the file does not exist, it should be created.
            // Each line of the file represents a single transaction in the following format:
        } catch (Exception e) {
            System.out.println("There was an issue with reading the file.");
        }

    }

    private static void addDeposit(Scanner scanner) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter;
        DateTimeFormatter ymD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String month = today.format(ymD);
        try {
            System.out.println("Please enter the current date | Example: 2024-04-28");
            String date = scanner.nextLine();
            System.out.println("Please enter the current time | Example: HH:mm:ss");
            String time = scanner.nextLine();
            System.out.println("Enter a brief description of the item");
            String description = scanner.nextLine();
            System.out.println("Please enter the vendor");
            String vendor = scanner.nextLine();
            System.out.println("Please enter the total amount you'd like to deposit");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Fix: consume newline
            if (amount > 1) {
                System.out.println("Thank you for your deposit of $" + String.format("%.2f", amount));
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
                    String line = date + "|" + time + "|" + description + "|SELF|" + amount;
                    writer.write(line);
                    writer.newLine();
                    System.out.println("Deposit recorded in transactions.csv.");
                    //catches are not working================================================================================================
                } catch (IOException e) {
                    System.out.println("Failed to write to file.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid deposit amount");
        }

        // The amount should be a positive number!
        // After validating the input, a new `Transaction` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.
    }

    private static void addPayment(Scanner scanner) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter;
        DateTimeFormatter ymD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String month = today.format(ymD);
        try {
            System.out.println("Please enter the current date | Example: 2024-04-28");
            String date = scanner.nextLine();
            System.out.println("Please enter the current time | Example: HH:mm:ss");
            String time = scanner.nextLine();
            System.out.println("Enter a brief description of the item");
            String description = scanner.nextLine();
            System.out.println("Please enter the vendor");
            String vendor = scanner.nextLine();
            System.out.println("Please enter the total amount you'd like to deposit");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Fix: consume newline
            if (amount >= 1) {
                System.out.println("Thank you for your payment of " + String.format("%.2f", amount * -1));
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
                    String line = date + "|" + time + "|" + description + "|SELF|" + amount;
                    writer.write(line);
                    writer.newLine();
                    System.out.println("Payment recorded in transactions.csv.");

                    //catches are not working================================================================================================
                } catch (IOException e) {
                    System.out.println("Please enter a valid payment amount.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid deposit amount");
        }

        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount received should be a positive number then transformed to a negative number.

        // After validating the input, a new `Transaction` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.
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
                    loadTransactions(FILE_NAME);
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
        try {
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            String file = "transactions.csv";
            BufferedReader bufReader = new BufferedReader(new FileReader(file));
            String line = bufReader.readLine();

            while ((line = bufReader.readLine()) != null) {
                System.out.println(line);
                String[] parts = line.split("\\|");
                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                String amount = parts[4];

            }
            bufReader.close();
        } catch (Exception e) {
            System.out.println("There was an issue with writing to the file.");
        }
    }
    // This method should display a table of all transactions in the `transactions` ArrayList.
    // The table should have columns for date, time, description, vendor, and amount.


    private static void displayDeposits() {
        try {
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            String file = "transactions.csv";
            BufferedReader bufReader = new BufferedReader(new FileReader(file));
            String line = bufReader.readLine();
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                String amount = parts[4];

                amount = parts[4].trim();
                if (!amount.contains("-")) {
                    System.out.println(line);
                }
            }
            bufReader.close();
        } catch (Exception e) {
            System.out.println("There was an issue with reading the file.");
        }
    }
    // This method should display a table of all deposits in the `transactions` ArrayList.
    // The table should have columns for date, time, description, vendor, and amount.


    private static void displayPayments() {
        try {
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            String file = "transactions.csv";
            BufferedReader bufReader = new BufferedReader(new FileReader(file));
            String line = bufReader.readLine();
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                String amount = parts[4];

                amount = parts[4].trim();
                if (amount.contains("-")) {
                    System.out.println(line);
                }
            }
            bufReader.close();
        } catch (Exception e) {
            System.out.println("There was an issue with reading the file.");
        }
    }
    // This method should display a table of all payments in the `transactions` ArrayList.
    // The table should have columns for date, time, description, vendor, and amount.


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
//currently only showing the first of month
//need it to print just the lines from the first to currentDate
//so I just do the first of the month to current date?
                try {
                    ArrayList<Transaction> transactions = new ArrayList<Transaction>();
                    String file = "transactions.csv";
                    BufferedReader bufReader = new BufferedReader(new FileReader(file));
                    String line = bufReader.readLine();
                    System.out.println("Please enter the current date");
                    while ((line = bufReader.readLine()) != null) {
                      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                      LocalDate  currentDate = LocalDate.parse(scanner.nextLine());
                      LocalDate  firstOfMonth = currentDate.withDayOfMonth(1);

                      LocalDate currentMonth = currentDate.minusMonths(1);
                      String prevMonthString = currentMonth.format(formatter);
                        if (currentMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")).equals(prevMonthString))
                            System.out.println(line);
                    }

                    bufReader.close();
                    break;

                } catch (Exception e) {
                    System.out.println("There was an issue with reading the file or parsing dates.");;
                }

                case "2":
//currently printing all matches with the same month
//also shows the year prior
//  ??? e.printStackTrace();
                    try {
                        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
                        String file = "transactions.csv";
                        BufferedReader bufReader = new BufferedReader(new FileReader(file));
                        String line = bufReader.readLine();
                        System.out.println("Please enter the current date");
                        while ((line = bufReader.readLine()) != null) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                            LocalDate currentDate = LocalDate.parse(scanner.nextLine());
                            LocalDate firstOfMonth = currentDate.withDayOfMonth(1);
                            LocalDate endOfMonth = currentDate;
                            LocalDate endDate = currentDate;
                            LocalDate prevMonth = currentDate.minusMonths(1);
                            String prevMonthString = prevMonth.format(formatter);
                            if (prevMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")).equals(prevMonthString))
                                System.out.println(line);
                        }

                        bufReader.close();
                        break;

                    } catch (Exception e) {
                        System.out.println("There was an issue with reading the file or parsing dates.");
                    }
                    //take in current time and backdate 30 days and display results
                    // Generate a report for all transactions within the previous month,
                    // including the date, time, description, vendor, and amount for each transaction.



//not sure how to actually print the line if statements are tricky
// was I supposed to parse the date into smaller parts?

                case "3":
                    try {
                        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
                        String file = "transactions.csv";
                        BufferedReader bufReader = new BufferedReader(new FileReader(file));
                        String line = bufReader.readLine();
                        System.out.println("Please enter the current date");
                        while ((line = bufReader.readLine()) != null) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                            LocalDate currentDate = LocalDate.parse(scanner.nextLine());
                            LocalDate firstOfYear = currentDate.withDayOfYear(1);
                            LocalDate endOfYear = currentDate;
                            LocalDate endDate = currentDate;
                            String firstOfYearString = firstOfYear.format(formatter);
                            if (firstOfYear.format(DateTimeFormatter.ofPattern("yyyy-MM")).equals(firstOfYearString))
                                System.out.println(firstOfYear);
                        }
                        bufReader.close();
                    } catch (Exception e) {
                        System.out.println("There was an issue with reading the file or parsing dates.");
                    }break;
                    // Generate a report for all transactions within the current year,
                    // including the date, time, description, vendor, and amount for each transaction.

                case "4":
//currently showing the previous year but not transactions
//not printing the line
//e.printStackTrace(); // Optional for debugging
                    try {
                        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
                        String file = "transactions.csv";
                        BufferedReader bufReader = new BufferedReader(new FileReader(file));
                        String line = bufReader.readLine();
                        while ((line = bufReader.readLine()) != null) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

                    System.out.println("Please enter the current date");
                            LocalDate currentDate = LocalDate.parse(scanner.nextLine());
                            LocalDate firstOfMonth = currentDate.withDayOfMonth(1);
                            LocalDate endOfMonth = currentDate;
                            LocalDate endDate = currentDate;
                            LocalDate prevYear = currentDate.minusYears(1);
                    String prevYearString = prevYear.format(formatter);
                    System.out.println(prevYear);
                            if (line.contains(prevYearString)) {
                                System.out.println(line);
                            }
                            bufReader.close();
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("There was an issue with reading the file or parsing dates.");
                    }

                case "5":
//need to call the methods??
                try {
                    ArrayList<Transaction> transactions = new ArrayList<Transaction>();
                    String file = "transactions.csv";
                    BufferedReader bufReader = new BufferedReader(new FileReader(file));
                    String line = bufReader.readLine();
                    while ((line = bufReader.readLine()) != null) {
                        System.out.println("please enter the Vendor you would like to search by");
                        String userVendor = scanner.nextLine();
                        String[] parts = line.split("\\|");
                        LocalDate date = LocalDate.parse(parts[0].trim());
                        LocalTime time = LocalTime.parse(parts[1].trim());
                        String description = parts[2];
                        String vendor = parts[3];
                        String amount = parts[4].trim();

                        if (userVendor.toLowerCase().contains(userVendor.toLowerCase())) {
                            System.out.println(line);
                        }
                        bufReader.close();
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("There was an issue with reading the file or parsing dates.");
                }

                case "0":
                    running = false;
                default:
                    break;
            }
        }
    }

//Does this need to be filtered in order? or just within that date range?
    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        boolean dateFound = false;
        try {
            String file = "transactions.csv";
            BufferedReader bufReader = new BufferedReader(new FileReader(file));
            String line = bufReader.readLine(); // Skip header
            while ((line = bufReader.readLine()) != null) {
                System.out.println("Please enter dates to search | Example: YYYY-MM-DD");
                    System.out.print("Start Date");
                    System.out.print("End Date");
                   LocalDate currentDate = LocalDate.now();
                     startDate = currentDate.withDayOfMonth(1);
                     endDate = currentDate;
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0].trim());
                LocalTime time = LocalTime.parse(parts[1].trim());
                String description = parts[2];
                String vendor = parts[3];
                String amount = parts[4].trim();
                if ((date.isEqual(startDate) || date.isAfter(startDate)) &&
                        (date.isEqual(endDate) || date.isBefore(endDate))) {
                    System.out.println(line);
                    dateFound = true;
                }
            }
            bufReader.close();
            if (!dateFound) {
                System.out.println("There were no dates found in that range.");
            }
        } catch (Exception e) {
            System.out.println("There was an issue with reading the file or parsing dates.");
        }
    }
    }

//catch edge cases where the number entered is not the correct amount
// format to display no results found between  *Jan-01-2023-Jan-01-2024================================================================
//should i have a method to path in the conversation and use the different switch cases as parameters?




//private static void filterTransactionsByVendor(String vendor) {

        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
        // }



//Bank Deposit
//double userBalance = 0;
//double userPayment = scanner.nextDouble();
//userBalance -= userPayment;
// try catch not working on some - test all
