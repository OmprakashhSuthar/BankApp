import java.time.LocalDate;
import java.util.logging.Logger;

public class Transaction {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private LocalDate lastWithdrawalDate;
    private double dailyWithdrawnAmount;
    private final Limits limits;
    private final TransactionHistory transactionHistory;

    public Transaction() {
        this.limits = new Limits();
        this.lastWithdrawalDate = LocalDate.now();
        this.dailyWithdrawnAmount = 0;
        this.transactionHistory = new TransactionHistory();
    }

    public synchronized void withdraw(Account account, double amount) {
        LocalDate currentDate = LocalDate.now();

        if (!currentDate.equals(lastWithdrawalDate)) {
            dailyWithdrawnAmount = 0; // Reset daily limit on a new day
            lastWithdrawalDate = currentDate;
        }

        if (!limits.canWithdraw(amount, dailyWithdrawnAmount, account.getBalance())) {
            return;
        }

        account.withdraw(amount); // Deduct from the account
        dailyWithdrawnAmount += amount;

        String description = "Withdrawn ₹" + amount + " from account [" + account.getAccountNumber() + "]";
        logger.info(description);
        transactionHistory.recordTransaction(description, currentDate, amount); // Record transaction

        logger.info("Remaining balance: ₹" + account.getBalance());
    }

    public synchronized void showLastFiveTransactions() {
        System.out.println("Last 5 Transactions: ");
        for (String transaction : transactionHistory.getLastFiveTransactions()) {
            System.out.println(transaction);
        }
    }

    public synchronized void showFullTransactionHistory() {
        System.out.println("Full Transaction History: ");
        for (TransactionRecord record : transactionHistory.getFullHistory()) {
            System.out.println(record.toString());
        }
    }

    public synchronized void showTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        System.out.println("Transactions from " + startDate + " to " + endDate + ": ");
        for (TransactionRecord record : transactionHistory.getTransactionsByDate(startDate, endDate)) {
            System.out.println(record.toString());
        }
    }
}
