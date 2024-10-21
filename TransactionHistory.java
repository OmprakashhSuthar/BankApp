import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

public class TransactionHistory {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final Queue<String> lastFiveTransactions; // Stores the last 5 transactions
    private final List<TransactionRecord> fullHistory; // Stores all transaction records

    public TransactionHistory() {
        lastFiveTransactions = new LinkedList<>();
        fullHistory = new LinkedList<>();
    }

    // Record a new transaction (adds to both history and last 5 transactions)
    public void recordTransaction(String description, LocalDate date, double amount) {
        // Create a new transaction record
        TransactionRecord record = new TransactionRecord(description, date, amount);
        fullHistory.add(record);

        // If more than 5 transactions, remove the oldest one
        if (lastFiveTransactions.size() >= 5) {
            lastFiveTransactions.poll(); // Remove the oldest transaction
        }
        lastFiveTransactions.add(record.toString()); // Add the new transaction

        logger.info("Transaction recorded: " + record.toString());
    }

    // Get the last 5 transactions
    public Queue<String> getLastFiveTransactions() {
        return new LinkedList<>(lastFiveTransactions); // Return a copy
    }

    // Get the entire transaction history
    public List<TransactionRecord> getFullHistory() {
        return new LinkedList<>(fullHistory); // Return a copy of the full history
    }

    // Get transactions filtered by date range
    public List<TransactionRecord> getTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        List<TransactionRecord> filteredHistory = new LinkedList<>();

        for (TransactionRecord record : fullHistory) {
            if ((record.getDate().isAfter(startDate) || record.getDate().isEqual(startDate)) &&
                (record.getDate().isBefore(endDate) || record.getDate().isEqual(endDate))) {
                filteredHistory.add(record);
            }
        }
        return filteredHistory;
    }
}