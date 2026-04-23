package frontend;

import javax.swing.JButton;
import java.awt.*;

public class UIPalette {
    //color palette
    protected static final Color PRIMARY_COLOR = new Color(135, 110, 200); // purple
    protected static final Color BG_COLOR = new Color(225, 245, 250); // very very light blue
    protected static final Color TEXT_COLOR = new Color(20, 50, 60); // very dark blue
    protected static final Color ACCENT_COLOR = new Color(250, 120, 160); // kinda pink

    //modern buttons
    public static JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setOpaque(true);
        btn.setForeground(UIPalette.BG_COLOR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 45));
        
        // Hover Effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
        
        return btn;
    }
}
