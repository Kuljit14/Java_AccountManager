package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

class Account {
    private String AccountholderName;
    private int age;
    private String AccountType;
    private int AccountNumber;
    private double balance;

    public Account(String AccountholderName, int age, String AccountType, int AccountNumber, double balance) {
        this.AccountholderName = AccountholderName;
        this.age = age;
        this.AccountType = AccountType;
        this.AccountNumber = AccountNumber;
        this.balance = balance;
    }

    public String getAccountholderName() {
        return AccountholderName;
    }

    public int getAge() {
        return age;
    }

    public String getAccountType() {
        return AccountType;
    }

    public int getAccountNumber() {
        return AccountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}

public class AccountManager {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Account> accounts = new ArrayList<>();
        System.out.println("_______Welcome To Account Manager By Kuljit...");

        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue; // blank line skip karo
                }
                try {
                    String[] parts = line.split(", ");
                    String accountNumberStr = parts[0].split(": ")[1];
                    String accountHolderName = parts[1].split(": ")[1];
                    int age = Integer.parseInt(parts[2].split(": ")[1]);
                    String accountType = parts[3].split(": ")[1];
                    double balance = Double.parseDouble(parts[4].split(": ")[1]);

                    accounts.add(new Account(accountHolderName, age, accountType,
                            Integer.parseInt(accountNumberStr), balance));
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the accounts file.");
            e.printStackTrace();
        }

        while (true) {
            System.out.println(
                    "Enter your Concern: 1 for Open account/ 2 for Deposit/ 3 for Withdraw/ 4 for Check Balance/ 5 Account List / 6 for Exit:");
            String Choice = sc.nextLine();
            switch (Choice) {
                case "1" -> System.out.println("I want to Create an Account");
                case "2" -> System.out.println("Deposit");
                case "3" -> System.out.println("Withdraw");
                case "4" -> System.out.println("Check Balance");
                case "5" -> System.out.println("Account List");
                case "6" -> System.out.println("Exit");
                default -> System.out.println("Invalid choice");
            }

            if (Choice.equals("1")) {

                System.out.println("Enter Account Holder Name: ");
                String name = sc.nextLine();
                System.out.println("Enter Your Age:");
                int age = sc.nextInt();
                sc.nextLine(); // Consume the newline character
                System.out.println("Enter Your Account Type (Savings/Current):");
                String accountType = sc.nextLine();
                System.out.println("Enter Your Initial Balance:");
                double balance = sc.nextDouble();
                sc.nextLine(); // Consume the newline character

                Account ac = new Account(name, age, accountType, accounts.size() + 1, balance);
                accounts.add(ac);
                System.out.println("Account Created Successfully!");

                try {
                    FileWriter writer = new FileWriter("Accounts.txt", true);
                    writer.write("Account Number: " + ac.getAccountNumber() +
                            ", Account Holder Name: " + ac.getAccountholderName() +
                            ", Age: " + ac.getAge() +
                            ", Account Type: " + ac.getAccountType() +
                            ", Balance: " + ac.getBalance() + "\n");
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred while creating the accounts file.");
                    e.printStackTrace();
                }
            }
            if (Choice.equals("2")) {
                System.out.println("Enter Account Number:");
                int accountNumber = sc.nextInt();
                sc.nextLine(); // Consume the newline character
                System.out.println("Enter Amount to Deposit:");
                double amount = sc.nextDouble();
                sc.nextLine(); // Consume the newline character

                boolean found = false;
                for (Account account : accounts) {
                    if (account.getAccountNumber() == accountNumber) {
                        account.setBalance(account.getBalance() + amount);
                        System.out.println("Deposit Successful! New Balance: " + account.getBalance());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Account not found!");
                }
            }
            if (Choice.equals("3")) {
                System.out.println("Enter Account Number:");
                int accountNumber = sc.nextInt();
                sc.nextLine(); // Consume the newline character
                System.out.println("Enter Amount to Withdraw:");
                double amount = sc.nextDouble();
                sc.nextLine(); // Consume the newline character

                boolean found = false;
                for (Account account : accounts) {
                    if (account.getAccountNumber() == accountNumber) {
                        if (account.getBalance() >= amount) {
                            account.setBalance(account.getBalance() - amount);
                            System.out.println("Withdrawal Successful! New Balance: " + account.getBalance());
                        } else {
                            System.out.println("Insufficient Balance!");
                        }
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Account not found!");
                }
            }
            if (Choice.equals("4")) {
                System.out.println("Enter Account Number:");
                int accountNumber = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                boolean found = false;
                for (Account account : accounts) {
                    if (account.getAccountNumber() == accountNumber) {
                        System.out.println("Current Balance: " + account.getBalance());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Account not found!");
                }
            }
            if (Choice.equals("5")) {
                if (accounts.isEmpty()) {
                    System.out.println("No accounts found!");
                } else {
                    for (Account account : accounts) {
                        System.out.println("Account Number: " + account.getAccountNumber() +
                                ", Account Holder Name: " + account.getAccountholderName() +
                                ", Age: " + account.getAge() +
                                ", Account Type: " + account.getAccountType() +
                                ", Balance: " + account.getBalance());

                    }

                }
                if (Choice.equals("6")) {
                    System.out.println("Exiting...");
                    break;
                }
            }
            break;
        }
    }
}