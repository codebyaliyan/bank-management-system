import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    public enum Type {
        OPENING_DEPOSIT, DEPOSIT, WITHDRAWAL
    }

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Type type;
    private final double amount;
    private final double balanceAfter;
    private final LocalDateTime timestamp;

    public Transaction(Type type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static String tableHeader() {
        return String.format("| %-19s | %-15s | %12s | %14s |",
                "Date & Time", "Type", "Amount", "Balance After")
                + "\n|---------------------|-----------------|--------------|----------------|";
    }

    @Override
    public String toString() {
        return String.format("| %-19s | %-15s | %12.2f | %14.2f |",
                timestamp.format(FORMAT), type, amount, balanceAfter);
    }
}