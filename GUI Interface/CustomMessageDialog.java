import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CustomMessageDialog extends JDialog {

    private String toHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    public CustomMessageDialog(Frame owner, String title, String message, String themeType) {
        super(owner, title, true); 

        setResizable(false);
        setSize(450, 350); 
        setLocationRelativeTo(owner);
        setUndecorated(true); 

        Color headerBgColor;
        Color okButtonBgColor; 
        
        switch (themeType) {
            case "WIN":
                headerBgColor = new Color(50, 168, 82); 
                okButtonBgColor = new Color(50, 168, 82); 
                break;
            case "LOSS":
                headerBgColor = new Color(200, 40, 40); 
                okButtonBgColor = new Color(50, 150, 255); 
                break;
            case "INFO": 
            default:
                headerBgColor = new Color(255, 180, 0); 
                okButtonBgColor = new Color(50, 168, 82); 
                break;
        }
        
        Color headerFgColor = Color.WHITE;
        Color dialogBgColor = new Color(30, 30, 30);
        Color textColor = Color.WHITE;

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(headerBgColor);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(headerFgColor);
        titlePanel.add(titleLabel, BorderLayout.WEST);
        
        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setForeground(headerFgColor);
        closeButton.setBackground(headerBgColor);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        titlePanel.add(closeButton, BorderLayout.EAST);

        JPanel contentPanel = new JPanel(new GridBagLayout()); 
        contentPanel.setBackground(dialogBgColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel messageLabel = new JLabel("<html><center>" + "<div style='color: " + toHex(textColor) + "; font-size: 16px; width: 300px;'>" + message + "</div></center></html>");
        
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setVerticalAlignment(SwingConstants.CENTER);

        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16)); 
        messageLabel.setForeground(textColor);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(messageLabel, gbc);

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 16));
        okButton.setBackground(okButtonBgColor); 
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setPreferredSize(new Dimension(100, 40));
        
        if (themeType.equals("WIN") || themeType.equals("LOSS")) {
             okButton.addActionListener(e -> System.exit(0)); 
        } else {
             okButton.addActionListener(e -> dispose()); 
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(dialogBgColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        buttonPanel.add(okButton);

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public static void showGameEnd(Frame owner, String title, String message, String themeType) {
        CustomMessageDialog dialog = new CustomMessageDialog(owner, title, message, themeType);
        dialog.setVisible(true);
    }

    public static void showLevelUp(Frame owner, String title, String message) {
        CustomMessageDialog dialog = new CustomMessageDialog(owner, title, message, "INFO");
        dialog.setVisible(true);
    }
}