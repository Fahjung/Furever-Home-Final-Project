package frontend;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeUI extends JFrame {

    public HomeUI() {
        setTitle("Furever Home");
        setSize(650, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Container with Background Color
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIPalette.BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        setContentPane(mainPanel);

        // Header Section
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        try {
            URL imgUrl = new URL("https://ibb.co/pjJNTJWb");
            ImageIcon originalIcon = new ImageIcon(imgUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Could not load icon: " + e.getMessage());
            iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        }
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(iconLabel);
        
        JLabel title = new JLabel("\uD83D\uDC3E Welcome to Furever Home! \uD83C\uDFE1", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        title.setForeground(UIPalette.TEXT_COLOR);

        JLabel subtitle = new JLabel("What would you like to do?", SwingConstants.CENTER);
        subtitle.setFont(new Font("Verdana", Font.PLAIN, 13));
        subtitle.setForeground(UIPalette.TEXT_COLOR);
        
        headerPanel.add(iconLabel);
        headerPanel.add(Box.createVerticalStrut(20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // Required for BoxLayout centering
        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(20));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(subtitle);
        headerPanel.add(Box.createVerticalStrut(80));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        JButton adoptBtn = UIPalette.createStyledButton("Adopt a Pet \uD83D\uDC93", UIPalette.PRIMARY_COLOR);
        JButton dropOffBtn = UIPalette.createStyledButton("Drop Off a Pet \uD83C\uDF81", UIPalette.ACCENT_COLOR);

        buttonPanel.add(adoptBtn);
        buttonPanel.add(dropOffBtn);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(buttonPanel);
        mainPanel.add(headerPanel, BorderLayout.CENTER);

        // Actions
        adoptBtn.addActionListener(e -> {
            new ShelterUI().setVisible(true);
            dispose();
        });

        dropOffBtn.addActionListener(e -> {
            new DropOffUI().setVisible(true);
            dispose();
        });

        try {
            URL iconURL = new URL("https://ibb.co/pjJNTJWb");
            ImageIcon frameIcon = new ImageIcon(iconURL);
            this.setIconImage(frameIcon.getImage());
        } catch (Exception e) {
            System.out.println("Could not set taskbar icon: " + e.getMessage());
        }
    }
    
}
