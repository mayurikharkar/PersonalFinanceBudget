package PersonalFinanceBudget;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String date;
    private String type;
    private String category;
    private double amount;
    private String dateModified;

    public Transaction(String date, String type, String category, double amount) {
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.dateModified = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    // Getters
    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDateModified() {
        return dateModified;
    }

    // Setters
    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
}