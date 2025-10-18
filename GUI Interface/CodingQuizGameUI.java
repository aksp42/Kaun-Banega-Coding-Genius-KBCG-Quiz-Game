import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent; 
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;




class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Error: Background image not found at path: " + imagePath);
            System.err.println("Using default solid blue background.");
            setBackground(new Color(10, 20, 50)); 
        }
        setOpaque(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        } else {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}

class RoundButton extends JButton {
    private Color bgColor;

    public RoundButton(String text, Color bgColor) {
        super(text);
        this.bgColor = bgColor;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(bgColor);
        setForeground(Color.WHITE);
        
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker().darker(), 4, true),
            new EmptyBorder(10, 10, 10, 10)
        )); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int arc = 40; 
        
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        }
        
        g2.dispose();
        super.paintComponent(g);
    }
}


public class CodingQuizGameUI extends JFrame {

    private final String coderName; 
    
    
    public CodingQuizGameUI(String name) {
        this.coderName = name; 
        
        setTitle("Language Selection - KBCG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setResizable(false);
        
        
        ImagePanel contentPanel = new ImagePanel("C:\\Users\\HP\\OneDrive\\Desktop\\k2.png");
        contentPanel.setLayout(new GridBagLayout()); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); 

        
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel welcomeLabel = createHeaderLabel("WELCOME,", new Font("Arial", Font.BOLD, 45), new Color(255, 204, 0));
        contentPanel.add(welcomeLabel, gbc);

        gbc.gridy = 1; 
        
        JLabel usernameLabel = createHeaderLabel(coderName + "!", new Font("Arial", Font.BOLD, 35), new Color(255, 204, 0));
        contentPanel.add(usernameLabel, gbc);


        
        JPanel scoreBar = new JPanel(new GridLayout(1, 2, 80, 0));
        scoreBar.setOpaque(false);
        scoreBar.setBorder(new EmptyBorder(10, 80, 10, 80));
        scoreBar.add(createScorePanel("★", "0")); 
        scoreBar.add(createScorePanel("♥", "3")); 
        gbc.gridy = 2; 
        contentPanel.add(scoreBar, gbc);

        
        
        gbc.gridy = 3; 
        JLabel instructionLabel1 = createHeaderLabel("Choose Your", new Font("Arial", Font.BOLD, 32), Color.WHITE);
        contentPanel.add(instructionLabel1, gbc);
        
        gbc.gridy = 4; 
        JLabel instructionLabel2 = createHeaderLabel("Programming Language", new Font("Arial", Font.BOLD, 32), Color.WHITE);
        contentPanel.add(instructionLabel2, gbc);


        
        JPanel buttonGrid = new JPanel(new GridLayout(2, 2, 25, 25));
        buttonGrid.setOpaque(false); 
        buttonGrid.setBorder(new EmptyBorder(30, 30, 30, 30)); 

        JButton pythonButton = createLanguageButton("Python", "dummy_python.png", new Color(50, 150, 255));
        JButton javaButton = createLanguageButton("Java", "dummy_java.png", new Color(255, 100, 50));
        JButton cButton = createLanguageButton("C", "dummy_c.png", new Color(150, 50, 255));
        JButton cPlusPlusButton = createLanguageButton("C++", "dummy_cplusplus.png", new Color(50, 200, 180));
        
       
        ActionListener languageSelector = e -> {
            String language = ((JButton) e.getSource()).getText();
            dispose(); 
            
            SwingUtilities.invokeLater(() -> new QuizChallengeGUI(coderName, language));
        };

        pythonButton.addActionListener(languageSelector);
        javaButton.addActionListener(languageSelector);
        cButton.addActionListener(languageSelector);
        cPlusPlusButton.addActionListener(languageSelector);
        
        buttonGrid.add(pythonButton);
        buttonGrid.add(javaButton);
        buttonGrid.add(cButton);
        buttonGrid.add(cPlusPlusButton);

        gbc.gridy = 5; 
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(buttonGrid, gbc);

        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    

    private static JButton createLanguageButton(String text, String iconPath, Color bgColor) {
        JButton button = new RoundButton(text, bgColor);
        
        button.setPreferredSize(new Dimension(280, 200)); 
        button.setOpaque(false);
        
        try {
              ImageIcon originalIcon = new ImageIcon(iconPath);
              Image img = originalIcon.getImage();
              Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
              button.setIcon(new ImageIcon(scaledImg));
              
              button.setHorizontalTextPosition(SwingConstants.CENTER);
              button.setVerticalTextPosition(SwingConstants.BOTTOM);
              button.setFont(new Font("Arial", Font.BOLD, 32)); 

        } catch (Exception e) {
              button.setText(text.toUpperCase());
              button.setHorizontalTextPosition(SwingConstants.CENTER);
              button.setVerticalTextPosition(SwingConstants.CENTER);
              
              if (text.equals("C") || text.equals("C++")) {
                  button.setFont(new Font("Arial", Font.BOLD, 70)); 
              } else {
                  button.setFont(new Font("Arial", Font.BOLD, 50)); 
              }
        }
        
        return button;
    }

    
    private static JLabel createHeaderLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        label.setOpaque(false); 
        return label;
    }

    
    private static JPanel createScorePanel(String iconText, String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setOpaque(false); 

        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setForeground(new Color(255, 204, 0));
        if (iconText.contains("♥")) {
              iconLabel.setForeground(new Color(255, 50, 50));
        }
        iconLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        textLabel.setOpaque(false);

        panel.add(iconLabel);
        panel.add(textLabel);
        return panel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CodingQuizGameUI("TestUser"));
    }
}