package PersonalFinanceBudget;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.jdatepicker.impl.*;

public class AddTransactionForm {
    public static void showForm(JFrame parent, List<Transaction> transactions) {
        JFrame frame = new JFrame("Add Transaction");
        frame.setSize(350, 300);
        frame.setIconImage(new ImageIcon("icon.png").getImage());
        frame.setLayout(new GridLayout(5, 2));

        // --- DATE PICKER CONFIGURATION ---
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Income", "Expense"});
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Food", "Rent", "Transport", "Other"});
        JTextField amountField = new JTextField();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                // Get selected date
                java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
                if (selectedDate == null) {
                    throw new IllegalArgumentException("Please select a date.");
                }

                String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
                String type = (String) typeBox.getSelectedItem();
                String category = (String) categoryBox.getSelectedItem();
                String amountText = amountField.getText().trim();

                if (amountText.isEmpty()) {
                    throw new IllegalArgumentException("Amount is required.");
                }

                double amount = Double.parseDouble(amountText);

                Transaction t = new Transaction(date, type, category, amount);
                transactions.add(t);
                TransactionManager.saveTransactions(transactions);
                frame.dispose();
                parent.dispose();
                Dashboard.showDashboard();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input: " + ex.getMessage());
            }
        });

        frame.add(new JLabel("Date:"));
        frame.add(datePicker);
        frame.add(new JLabel("Type:"));
        frame.add(typeBox);
        frame.add(new JLabel("Category:"));
        frame.add(categoryBox);
        frame.add(new JLabel("Amount:"));
        frame.add(amountField);
        frame.add(addButton);

        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }

    // Inner class for formatting date
    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

        @Override
        public Object stringToValue(String text) {
            return java.sql.Date.valueOf(LocalDate.parse(text, dateFormatter));
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            }
            return "";
        }
    }
}