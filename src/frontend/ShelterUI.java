package frontend;

import backend.*;
import backend.Observer.Observer;
import backend.Observer.ShelterSystem;
import backend.Strategy.AdoptionStrategy;
import backend.Strategy.DeliveryAdoption;
import backend.Strategy.StandardAdoption;
import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ShelterUI extends JFrame {
    private ShelterSystem shelter;
    private JLabel imageLabel;
    private JLabel nameLabel, ageLabel, sexLabel, typeLabel, descLabel; //added
    private JComboBox<String> typeDropdown;
    private JComboBox<String> nameDropdown;

    public ShelterUI() {
        this.shelter = ShelterSystem.getInstance();
        setTitle("Pet Shelter Adoption System");
        setSize(650, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Initialize UI Elements (MUST be done before refresh calls)
        typeDropdown = new JComboBox<>(new String[]{"All", "Dog", "Cat", "Bunny", "Goat"});
        nameDropdown = new JComboBox<>();
        
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(280, 380));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        descLabel = new JLabel();

        nameLabel = createInfoLabel("Name: ");
        ageLabel = createInfoLabel("Age: ");
        sexLabel = createInfoLabel("Sex: ");
        typeLabel = createInfoLabel("Type: ");
        descLabel = createInfoLabel("Remarks: ");

        // 2. Setup Layout
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(UIPalette.BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        setContentPane(mainPanel);

        // Top Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        topPanel.setOpaque(false);
        topPanel.add(new JLabel("Filter by Animal:"));
        topPanel.add(typeDropdown);
        topPanel.add(new JLabel("Select Pet Name:"));
        topPanel.add(nameDropdown);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center Panel (Image + Info)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 5));
        centerPanel.setOpaque(false);
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        centerPanel.add(imagePanel);

        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 0, 10));
        infoPanel.setOpaque(false);
        infoPanel.add(nameLabel);
        infoPanel.add(ageLabel);
        infoPanel.add(sexLabel);
        infoPanel.add(typeLabel);
        infoPanel.add(descLabel);
        centerPanel.add(infoPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel (Buttons + Log)
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 15));
        bottomPanel.setOpaque(false);
        
        JPanel actionBtnGroup = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        actionBtnGroup.setOpaque(false);
        JButton expressBtn = UIPalette.createStyledButton("Delivery Adopt \uD83D\uDE9A", UIPalette.ACCENT_COLOR);
        JButton standardBtn = UIPalette.createStyledButton("Standard Adopt \uD83D\uDCE5", UIPalette.PRIMARY_COLOR);
        JButton backBtn = UIPalette.createStyledButton("← Back", Color.GRAY);
        backBtn.setPreferredSize(new Dimension(100, 40));
        
        actionBtnGroup.add(backBtn);
        actionBtnGroup.add(standardBtn);
        actionBtnGroup.add(expressBtn);
        
        bottomPanel.add(actionBtnGroup, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 3. Setup Listeners
        typeDropdown.addActionListener(e -> refreshNameDropdown());
        nameDropdown.addActionListener(e -> {
            updatePetText();
            updatePetImage();
        });

        backBtn.addActionListener(e -> {
            new HomeUI().setVisible(true);
            dispose();
        });

        expressBtn.addActionListener(e -> startAdoption(new DeliveryAdoption()));
        standardBtn.addActionListener(e -> startAdoption(new StandardAdoption()));

        // 4. Initial Data Load
        refreshTypeDropdown();
        refreshNameDropdown();

        try {
            URL iconURL = new URL("z");
            ImageIcon frameIcon = new ImageIcon(iconURL);
            this.setIconImage(frameIcon.getImage());
        } catch (Exception e) {
            System.out.println("Could not set taskbar icon: " + e.getMessage());
        }
    }

    private void refreshNameDropdown() {
        if (nameDropdown == null) return; // Guard against null pointer
        nameDropdown.removeAllItems();
        
        String selectedType = (String) typeDropdown.getSelectedItem();
        if (selectedType == null) selectedType = "All"; // Fallback

        for (Pet pet : shelter.getPetList()) {
            // Log to console to see if the loop is even running
            System.out.println("Checking pet \uD83D\uDC3E : " + pet.name + " (" + pet.getType() + ")");

            if (selectedType.equals("All") || pet.getType().equalsIgnoreCase(selectedType)) {
                nameDropdown.addItem(pet.name);
            }
        }
        if (nameDropdown.getItemCount() > 0) {
            nameDropdown.setSelectedIndex(0);
            updatePetText();
            updatePetImage();
        }
    }

    private void refreshTypeDropdown() {
        String currentSelection = (String) typeDropdown.getSelectedItem();
        typeDropdown.removeAllItems();
        typeDropdown.addItem("All");
        
        // Use a Set to get unique types from the shelter
        java.util.Set<String> types = new java.util.HashSet<>();
        for (Pet pet : shelter.getPetList()) {
            types.add(pet.getType());
        }
        
        for (String type : types) {
            typeDropdown.addItem(type);
        }
        
        // Restore selection if it still exists
        typeDropdown.setSelectedItem(currentSelection);
    }

    private void startAdoption(AdoptionStrategy strategy) {
        String selectedName = (String) nameDropdown.getSelectedItem();
        Pet target = shelter.findPetByName(selectedName);

        if (target != null) {
            // make the UI an observer
            strategy.addObserver(shelter); 
            strategy.addObserver(new Observer() {
                @Override
                public void AdoptionComplete(Pet pet) {
                    JOptionPane.showMessageDialog(ShelterUI.this,
                        pet.name + " has been adopted! 🎉",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                @Override
                public void DropOffComplete(Pet pet) {
                    JOptionPane.showMessageDialog(ShelterUI.this, 
                        pet.name + " has arrived at your home! 📩", 
                        "Delivery Update", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });   
            strategy.processAdoption(target);
            refreshNameDropdown();
        }
    }
    
    private void updatePetText() {
        String selectedName = (String) nameDropdown.getSelectedItem();
        if (selectedName == null) return;

        Pet pet = shelter.findPetByName(selectedName);

        if (pet != null) {
            nameLabel.setText("Name: " + pet.name);
            ageLabel.setText("Age: " + pet.getAge());
            sexLabel.setText("Sex: " + pet.getSex());
            typeLabel.setText("Type: " + pet.getType());
            descLabel.setText("<html>Remarks: " + pet.getDesc() + "</html>");
        }
    }

    private void updatePetImage() {
        String selectedName = (String) nameDropdown.getSelectedItem();
        if (selectedName == null) return;

        Pet pet = shelter.findPetByName(selectedName);
        if (pet != null) {
            
            try {
                URL url = java.net.URI.create(pet.getImagePath()).toURL();
                ImageIcon icon = new ImageIcon(url);
                Image originalImage = icon.getImage();

                // Use the preferred size of the label as the target
                int targetW = imageLabel.getPreferredSize().width;
                int targetH = imageLabel.getPreferredSize().height;

                // Calculate scaling to fill the box without stretching (Aspect Fill)
                double widthRatio = (double) targetW / originalImage.getWidth(null);
                double heightRatio = (double) targetH / originalImage.getHeight(null);
                double scale = Math.max(widthRatio, heightRatio);

                int scaledW = (int) (originalImage.getWidth(null) * scale);
                int scaledH = (int) (originalImage.getHeight(null) * scale);

                // Create canvas
                java.awt.image.BufferedImage finalImg = new java.awt.image.BufferedImage(
                    targetW, targetH, java.awt.image.BufferedImage.TYPE_INT_ARGB);
                
                Graphics2D g2d = finalImg.createGraphics();
                
                // High Quality Rendering
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Center the image on the canvas
                int x = (targetW - scaledW) / 2;
                int y = (targetH - scaledH) / 2;

                g2d.drawImage(originalImage, x, y, scaledW, scaledH, null);
                g2d.dispose();

                imageLabel.setIcon(new ImageIcon(finalImg));
                imageLabel.setText(""); 

            } catch (Exception e) {
                imageLabel.setIcon(null);
                imageLabel.setText("Image not found");
            }
        }
    }
    //added
    private JLabel createInfoLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(UIPalette.TEXT_COLOR);
        return lbl;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeUI().setVisible(true));
    }
}