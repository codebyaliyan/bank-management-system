import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {

    private static int nextAccountNo = 1001;

    private final int accountNo;               
    private final String holderName;
    private double balance;
    private final ArrayList<Transaction> transactions = new ArrayList<>();

    /**
     * @param holderName     name of the account holder (must not be empty)
     * @param initialDeposit opening balance (0 or more)
     * @throws IllegalArgumentException if the name is empty or the deposit is invalid
     */

    public Account(String holderName, double initialDeposit) {
        if (holderName == null || holderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty.");
        }
        if (Double.isNaN(initialDeposit) || Double.isInfinite(initialDeposit) || initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative.");
        }

        this.accountNo = nextAccountNo++;
        this.holderName = holderName.trim();
        this.balance = initialDeposit;

        if (initialDeposit > 0) {
            transactions.add(new Transaction(Transaction.Type.OPENING_DEPOSIT, initialDeposit, balance));
        }
    }

    public int getAccountNo() {
        return accountNo;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactions);
    }

    /*
     * @throws IllegalArgumentException if the amount is not greater than zero
     */
    public void deposit(double amount) {
        validateAmount(amount);
        balance += amount;
        transactions.add(new Transaction(Transaction.Type.DEPOSIT, amount, balance));
    }

    /*
     * @throws IllegalArgumentException if the amount is not greater than zero
     * @throws IllegalStateException    if the balance is too low
     */
    public void withdraw(double amount) {
        validateAmount(amount);
        if (amount > balance) {
            throw new IllegalStateException(
                    "Insufficient balance. Available: " + String.format("%.2f", balance));
        }
        balance -= amount;
        transactions.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, balance));
    }

    private void validateAmount(double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }
}