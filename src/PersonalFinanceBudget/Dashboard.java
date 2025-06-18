package PersonalFinanceBudget;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Dashboard {
    private static JLabel balanceLabel = new JLabel();
    private static JLabel incomeLabel = new JLabel();
    private static JLabel expenseLabel = new JLabel();

    public static void showDashboard() {
        // Load data
        List<Transaction> transactions = TransactionManager.loadTransactions();
        if (transactions == null) transactions = new java.util.ArrayList<>();

        double income = transactions.stream()
                .filter(t -> "Income".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount).sum();

        double expense = transactions.stream()
                .filter(t -> "Expense".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount).sum();

        double balance = income - expense;

        // Main frame
        JFrame frame = new JFrame("Personal Finance Manager");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("icon.png").getImage());
        frame.setLayout(new BorderLayout());

        // Dark mode
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        // Top panel for summary
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        balanceLabel.setText("Current Balance: $" + String.format("%.2f", balance));
        incomeLabel.setText("Total Income: $" + String.format("%.2f", income));
        expenseLabel.setText("Total Expenses: $" + String.format("%.2f", expense));

        summaryPanel.add(styledLabelPanel("Current Balance", balanceLabel));
        summaryPanel.add(styledLabelPanel("Total Income", incomeLabel));
        summaryPanel.add(styledLabelPanel("Total Expenses", expenseLabel));

        // Chart panel
        JPanel chartPanel = new JPanel(new BorderLayout());
        JLabel chartTitle = new JLabel("Monthly Expenses", SwingConstants.CENTER);
        chartTitle.setFont(new Font("Arial", Font.BOLD, 16));
        chartPanel.add(chartTitle, BorderLayout.NORTH);
        chartPanel.add(new ChartPanel(createBarChart(transactions)), BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addBtn = new JButton("Add Transaction");
        JButton reportBtn = new JButton("View Reports");
        JButton planBtn = new JButton("Budget Planning");

        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        reportBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        planBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        List<Transaction> finalTransactions = transactions;
        addBtn.addActionListener(e -> AddTransactionForm.showForm(frame, finalTransactions));
        reportBtn.addActionListener(e -> ReportWindow.showReport(frame, finalTransactions));
        planBtn.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Budget planning coming soon!"));

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(addBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(reportBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(planBtn);

        // Combine center
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(chartPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.EAST);

        // Add to frame
        frame.add(summaryPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel styledLabelPanel(String title, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }

    private static JFreeChart createBarChart(List<Transaction> transactions) {
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Transaction t : transactions) {
            if ("Expense".equalsIgnoreCase(t.getType())) {
                categoryTotals.put(
                        t.getCategory(),
                        categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount()
                );
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            dataset.addValue(entry.getValue(), "Expenses", entry.getKey());
        }

        return ChartFactory.createBarChart(
                "", // title
                "Category",
                "Amount",
                dataset
        );
    }
}