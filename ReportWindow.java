package PersonalFinanceBudget;

import com.formdev.flatlaf.FlatLightLaf;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportWindow {
    public static void showReport(JFrame parent, List<Transaction> transactions) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Default theme
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame reportFrame = new JFrame("Reports");
        reportFrame.setSize(500, 500);
        reportFrame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Reports", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        reportFrame.add(title, BorderLayout.NORTH);

        JPanel chartPanel = new JPanel(new BorderLayout());

        JButton generateBtn = new JButton("Generate");
        JButton themeToggle = new JButton("Toggle Theme");

        // Add theme toggle and generate button to a panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(generateBtn, BorderLayout.CENTER);
        bottomPanel.add(themeToggle, BorderLayout.EAST);

        reportFrame.add(bottomPanel, BorderLayout.SOUTH);
        reportFrame.add(chartPanel, BorderLayout.CENTER);

        generateBtn.addActionListener(e -> {
            JFreeChart chart = createPieChart(transactions);
            chartPanel.removeAll();
            chartPanel.add(new ChartPanel(chart), BorderLayout.CENTER);
            chartPanel.revalidate();
        });

        themeToggle.addActionListener(e -> ThemeManager.toggleTheme(reportFrame));

        reportFrame.setLocationRelativeTo(parent);
        reportFrame.setVisible(true);
    }

    private static JFreeChart createPieChart(List<Transaction> transactions) {
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Transaction t : transactions) {
            if ("Expense".equalsIgnoreCase(t.getType())) {
                categoryTotals.put(t.getCategory(),
                        categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Expenses by Category",
                dataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("#,##0.00"), new DecimalFormat("0.00%")
        ));

        return chart;
    }
}