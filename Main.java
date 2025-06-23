package PersonalFinanceBudget;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Enable Dark Mode
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (Exception e) {
            System.out.println("Failed to set dark mode");
        }

        SwingUtilities.invokeLater(() -> LoginScreen.showLogin());
    }
}