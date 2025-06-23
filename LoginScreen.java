package PersonalFinanceBudget;

import javax.swing.*;
import java.awt.*;

public class LoginScreen {
    public static void showLogin() {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("icon.png").getImage());

        // Center panel with padding
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField(15);
        userPanel.add(userLabel);
        userPanel.add(userText);

        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passText = new JPasswordField(15);
        passPanel.add(passLabel);
        passPanel.add(passText);

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.addActionListener(e -> {
            frame.dispose();
            Dashboard.showDashboard();  // Proceed to dashboard after login
        });

        // Add all to main panel
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(userPanel);
        panel.add(passPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loginButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}