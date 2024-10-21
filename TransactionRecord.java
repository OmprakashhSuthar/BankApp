import java.time.LocalDate;

public class TransactionRecord {
    private final String description;
    private final LocalDate date;
    private final double amount;

    public TransactionRecord(String description, LocalDate date, double amount) {
        this.description = description;
        this.date = date;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Amount: ₹" + amount + ", Description: " + description;
    }
}
