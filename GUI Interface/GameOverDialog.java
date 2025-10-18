import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameOverDialog extends JDialog {

    
    private String toHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    
    public GameOverDialog(Frame owner, String title, String message, boolean isWin) {
        super(owner, title, true); 

        setResizable(false);
       
        setSize(450, 350); 
        setLocationRelativeTo(owner);
        setUndecorated(true);

        
        Color winColor = new Color(50, 168, 82);  
        Color lossColor = new Color(200, 40, 40);  
        
        Color headerBgColor = isWin ? winColor : lossColor;
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

        
        JLabel messageLabel = new JLabel("<html><center style='color: " + toHex(textColor) + "; font-size: 16px;'>" + message + "</center></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setForeground(textColor);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(messageLabel, gbc);

        
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        
        Color okButtonColor = isWin ? winColor : lossColor;
        okButton.setBackground(okButtonColor); 
        
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setPreferredSize(new Dimension(100, 40));
        okButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(dialogBgColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        buttonPanel.add(okButton);

        
        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    
    public static void showAndExit(Frame owner, String title, String message, boolean isWin) {
        GameOverDialog dialog = new GameOverDialog(owner, title, message, isWin);
        dialog.setVisible(true);
        
        System.exit(0);
    }
}