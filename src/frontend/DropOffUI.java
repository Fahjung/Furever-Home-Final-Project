package frontend;

import backend.*;
import backend.Observer.ShelterSystem;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.net.URL;

public class DropOffUI extends JFrame {
    private ShelterSystem shelter;
    private String selectedImagePath = "";
    private JLabel previewLabel;

    public DropOffUI() {
        this.shelter = ShelterSystem.getInstance(); 
        setTitle("Drop Off a Pet");
        setSize(650, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(UIPalette.BG_COLOR);
        main.setBorder(new EmptyBorder(30, 40, 30, 40));
        setContentPane(main);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(20, 0, 20, 0));

        JTextField nameField = styleTextField();
        JTextField ageField = styleTextField();
        JComboBox<String> sexBox = new JComboBox<>(
            new String[]{"Male", "Female"}
        );
        JTextField otherTypeField = styleTextField();

        JComboBox<String> typeBox = new JComboBox<> (
            new String[] {"Dog", "Cat", "Bunny", "Goat", "Other"}
        );
        JTextField descField = styleTextField();

        JPanel row1 = makeHorizontalRow(
            createMiniColumn("Name:", nameField, 180),
            createMiniColumn("Age:", ageField, 80)
        );

        JPanel row2 = makeHorizontalRow(
            createMiniColumn("Sex:", sexBox, 130),
            createMiniColumn("Type:", typeBox, 130),
            createMiniColumn("Remarks: ", descField, 180)
        );

        // Specify Other Row (Hidden by default)
        JPanel otherRow = makeRow("Please specify pet type:", otherTypeField);
        otherRow.setOpaque(false);
        otherRow.setVisible(false);
        

        //uploading photo
        JLabel urlLabel = new JLabel("Paste Photo Link:");
        urlLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        urlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField urlField = styleTextField();
        urlField.setMaximumSize(new Dimension(350, 30));

        previewLabel = new JLabel("No Image Preview", SwingConstants.CENTER);
        previewLabel.setPreferredSize(new Dimension(150, 150));
        previewLabel.setMaximumSize(new Dimension(150, 150));
        previewLabel.setBorder(new LineBorder(UIPalette.PRIMARY_COLOR, 2, true));
        previewLabel.setBackground(Color.GRAY);
        previewLabel.setOpaque(true);
        previewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton previewBtn = UIPalette.createStyledButton("Preview", UIPalette.PRIMARY_COLOR);
        previewBtn.setPreferredSize(new Dimension(150, 25));
        previewBtn.setMaximumSize(new Dimension(180, 40)); 
        previewBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setOpaque(false);
        
        JButton enterButton = UIPalette.createStyledButton("Enter", UIPalette.ACCENT_COLOR);
        JButton backBtn = UIPalette.createStyledButton("← Back", UIPalette.PRIMARY_COLOR);
        backBtn.setPreferredSize(new Dimension(120, 45));
        enterButton.setPreferredSize(new Dimension(200, 45));

        bottomPanel.add(backBtn);
        bottomPanel.add(enterButton);
        main.add(bottomPanel, BorderLayout.SOUTH);

        // Add them to the form
        form.add(row1);
        form.add(Box.createVerticalStrut(15));
        form.add(row2);
        form.add(Box.createVerticalStrut(10));

        // Please Specify (hidden by default)
        form.add(otherRow);
        form.add(Box.createVerticalStrut(20));
        form.add(urlLabel);
        form.add(Box.createVerticalStrut(5));
        form.add(urlField);
        form.add(Box.createVerticalStrut(10));
        form.add(previewBtn);
        form.add(Box.createVerticalStrut(15));
        form.add(previewLabel);

        main.add(form);
        setContentPane(main);

        typeBox.addActionListener(e -> {
            boolean isOther = typeBox.getSelectedItem().equals("Other");
            otherRow.setVisible(isOther);
            otherTypeField.setVisible(isOther);
            form.revalidate();
            form.repaint();
        });

        previewBtn.addActionListener(e -> {
            String link = urlField.getText().trim();
            if (!link.isEmpty()) {
                try {
                    // Use the URI -> URL conversion to avoid the deprecation warning
                    URL url = java.net.URI.create(link).toURL();
                    ImageIcon icon = new ImageIcon(url);
                    
                    // Check if the image actually loaded
                    if (icon.getIconWidth() > 0) {
                        Image img = icon.getImage().getScaledInstance(-1, 120, Image.SCALE_SMOOTH);
                        previewLabel.setIcon(new ImageIcon(img));
                        previewLabel.setText("");
                        selectedImagePath = link; // Store the link for the pet object
                    } else {
                        previewLabel.setText("Invalid Image Link");
                    }
                } catch (Exception ex) {
                    previewLabel.setText("Error loading URL");
                }
            }
        });

        enterButton.addActionListener(e -> {
            String name = nameField.getText();
            String ageText = ageField.getText();
            String sex = (String) sexBox.getSelectedItem();
            String type = (String) typeBox.getSelectedItem();
            String desc = descField.getText();
            String finalImagePath = urlField.getText().trim();

            if (type.equals("Other")) {
                type = otherTypeField.getText();
            }

            if (name.isEmpty() || ageText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                if (finalImagePath.isEmpty()) {
                    finalImagePath = "https://www.insticc.org/node/TechnicalProgram/56e7352809eb881d8c5546a9bbf8406e.png"; // Your default
                }

                Pet newPet = PetFactory.createPet(type, name, age, sex, desc, finalImagePath);
                 
                shelter.addPet(newPet);
                JOptionPane.showMessageDialog(this, "Pet dropped off successfully!"); 
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age.");
            }
            
        });

        backBtn.addActionListener(e -> {
        new HomeUI().setVisible(true);
        dispose();
        });

        try {
            URL iconURL = new URL("https://img.sanishtech.com/u/6bf284a4196a24e7423d34c82a19eea2.png");
            ImageIcon frameIcon = new ImageIcon(iconURL);
            this.setIconImage(frameIcon.getImage());
        } catch (Exception e) {
            System.out.println("Could not set taskbar icon: " + e.getMessage());
        }
    }

    private JPanel makeRow(String labelText, JComponent field) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension size = new Dimension(270, 30); 
        field.setPreferredSize(size);
        field.setMinimumSize(size);
        field.setMaximumSize(size);
        row.add(label);
        row.add(Box.createVerticalStrut(3));
        row.add(field);
        return row;
    }

    private JPanel makeHorizontalRow(JComponent... components) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        row.setOpaque(false); // Keeps background consistent
        for (JComponent c : components) {
            row.add(c);
        }
        return row;
    }

    private JPanel createMiniColumn(String labelText, JComponent field, int width) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setPreferredSize(new Dimension(width, 30));
        field.setMaximumSize(new Dimension(width, 30));

        col.add(label);
        col.add(Box.createVerticalStrut(3));
        col.add(field);
        return col;
    }

    private JTextField styleTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIPalette.TEXT_COLOR, 1),
            new EmptyBorder(2, 5, 2, 5)
        ));
        return tf;
    }
}