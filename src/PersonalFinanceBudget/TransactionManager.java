package PersonalFinanceBudget;

import java.io.*;
import java.util.*;

public class TransactionManager {
    private static final String FILE_PATH = "transactions.csv";

    public static void saveTransactions(List<Transaction> transactions) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Transaction t : transactions) {
                writer.write(t.getDate() + "," + t.getType() + "," + t.getCategory() + "," + t.getAmount() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Transaction t = new Transaction(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
                    transactions.add(t);
                }
            }
        } catch (IOException e) {
            // No file found initially
        }
        return transactions;
    }
}