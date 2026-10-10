import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bankmanagement {

    private static final List<Account> accounts = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean run = true;
        while (run) {
            
            menu();

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1: 
                    createAccount(); 
                break;
                
                case 2: 
                    deposit(); 
                break;
                
                case 3: 
                    withdraw(); 
                break;
                
                case 4: 
                    checkBalance(); 
                break;
                
                case 5: 
                    showAccountDetails(); 
                break;
                
                case 6: 
                    showTransactionHistory(); 
                break;

                case 7:
                    run = false;
                    System.out.println("Thank you for using the Bank Management System.");
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 7.");
            }
        }
        sc.close();
    }

    private static void menu() {
        System.out.println("\n===== WELCOME TO THE BANK MANAGEMENT SYSTEM =====");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Check Balance");
        System.out.println("5. Account Details");
        System.out.println("6. Transaction History");
        System.out.println("7. Exit");
        System.out.print("Enter Your Choice: ");
    }

    private static void createAccount() {
        System.out.print("Account Holder Name: ");
        String name = sc.nextLine();

        double initial = readAmount("Enter the Initial Deposit Amount: ");

        try {
            Account account = new Account(name, initial);
            accounts.add(account);
            System.out.println("\nAccount Created Successfully.");
            System.out.println("Your Account Number is: " + account.getAccountNo());
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void deposit() {
        Account account = askForAccount();
        if (account == null) return;

        double amount = readAmount("Enter the amount to deposit: ");

        try {
            account.deposit(amount);
            System.out.println("Amount Deposited Successfully.");
            System.out.printf("Current Balance: %.2f%n", account.getBalance());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void withdraw() {
        Account account = askForAccount();
        if (account == null) return;

        double amount = readAmount("Enter the amount to withdraw: ");

        try {
            account.withdraw(amount);
            System.out.println("Withdrawal Successful.");
            System.out.printf("Current Balance: %.2f%n", account.getBalance());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void checkBalance() {
        Account account = askForAccount();
        if (account == null) return;

        System.out.printf("Current Balance: %.2f%n", account.getBalance());
    }

    private static void showAccountDetails() {
        Account account = askForAccount();
        if (account == null) return;

        System.out.println("\n----- ACCOUNT DETAILS -----");
        System.out.println("Account Number : " + account.getAccountNo());
        System.out.println("Account Holder : " + account.getHolderName());
        System.out.printf("Current Balance: %.2f%n", account.getBalance());
        System.out.println("Transactions   : " + account.getTransactionHistory().size());
        System.out.println("---------------------------");
    }

    private static void showTransactionHistory() {
        Account account = askForAccount();
        if (account == null) return;

        List<Transaction> history = account.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No Transactions Found...");
            return;
        }

        System.out.println("\nTransaction history for account " + account.getAccountNo()
                + " (" + account.getHolderName() + ")");
        System.out.println(Transaction.tableHeader());
        for (Transaction t : history) {
            System.out.println(t);
        }
        System.out.println("Total transactions: " + history.size());
    }

    private static Account askForAccount() {
        System.out.print("Enter Account Number: ");
        int accountNo;

        try {
            accountNo = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid Account Number format.");
            return null;
        }

        Account account = findByAccountNo(accountNo);
        if (account == null) {
            System.out.println("ERROR: Account " + accountNo + " not found.");
        }
        return account;
    }

    private static Account findByAccountNo(int accountNo) {
        for (Account account : accounts) {
            if (account.getAccountNo() == accountNo) {
                return account;
            }
        }
        return null;
    }

    private static double readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(sc.nextLine().trim());
                if (Double.isNaN(value) || Double.isInfinite(value)) {
                    System.out.println("Invalid input. Please enter a normal number.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid input. Please enter a number.");
            }
        }
    }
}