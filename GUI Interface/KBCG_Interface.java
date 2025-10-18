import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import java.io.File; 
import java.awt.BasicStroke; 



public class KBCG_Interface extends JFrame {

    private static final Color NEON_PINK = new Color(255, 0, 255);
    private static final Color NEON_BLUE = new Color(0, 255, 255);
    
    private static final Color DARK_BG = new Color(10, 0, 30); 
    private static final Color BUTTON_INNER_COLOR = new Color(90, 0, 150); 
    
    private final JTextField nameField = new RoundedTextField(20); 

    public KBCG_Interface() {
        setTitle("Kaun Banega Coding Genius!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800); 
        setResizable(false);
        
        BackgroundPanel mainPanel = new BackgroundPanel("C:\\Users\\HP\\OneDrive\\Desktop\\k2.png"); 
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 20, 50));
        
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30))); 
        

        JLabel title1 = new JLabel("KAUN BANEGA");
        title1.setFont(new Font("Arial", Font.BOLD, 40)); 
        title1.setForeground(NEON_PINK);
        title1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title2 = new JLabel("CODING GENIUS!");
        title2.setFont(new Font("Arial", Font.BOLD, 55)); 
        title2.setForeground(NEON_PINK);
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel welcomeLine1 = new JLabel("WELCOME TO");
        welcomeLine1.setFont(new Font("Arial", Font.BOLD, 50));
        welcomeLine1.setForeground(Color.YELLOW);
        welcomeLine1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLine2 = new JLabel("KBCG!");
        welcomeLine2.setFont(new Font("Arial", Font.BOLD, 60));
        welcomeLine2.setForeground(Color.YELLOW);
        welcomeLine2.setAlignmentX(Component.CENTER_ALIGNMENT);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(welcomeLine1);
        verticalBox.add(Box.createVerticalStrut(10)); 
        verticalBox.add(welcomeLine2);
        verticalBox.setOpaque(false); 

        JPanel welcomePanel = new WelcomeContainerPanel(verticalBox);
        welcomePanel.setMaximumSize(new Dimension(500, 150)); 
        welcomePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel inputLabel = new JLabel("Enter Coder Name:");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 24)); 
        inputLabel.setForeground(NEON_BLUE);
        inputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        nameField.setFont(new Font("Arial", Font.PLAIN, 33)); 
        nameField.setForeground(Color.WHITE); 
        nameField.setBackground(new Color(5, 5, 50)); 
        nameField.setMaximumSize(new Dimension(350, 100)); 
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new StartGameButton("START GAME"); 
        startButton.setFont(new Font("Arial", Font.BOLD, 36));
        startButton.setForeground(NEON_PINK); 
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(350, 80);
        startButton.setMaximumSize(buttonSize); 
        startButton.setPreferredSize(buttonSize); 
        startButton.setMinimumSize(buttonSize); 

       
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String coderName = nameField.getText().trim();
                
                if (!coderName.isEmpty()) {
                    
                    dispose(); 
                    
                    SwingUtilities.invokeLater(() -> {
                      
                        new CodingQuizGameUI(coderName); 
                    });
                } else {
                    JOptionPane.showMessageDialog(KBCG_Interface.this, "Please Enter the Player name!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JLabel footer = new JLabel("© KBCG 2025");
        footer.setFont(new Font("Arial", Font.BOLD, 20)); 
        footer.setForeground(NEON_BLUE);
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(title1);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(title2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50))); 
        mainPanel.add(welcomePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
        mainPanel.add(inputLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(nameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); 
        mainPanel.add(startButton);
        mainPanel.add(Box.createVerticalGlue()); 
        mainPanel.add(footer);

        setContentPane(mainPanel);
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String path) {
            try {
                backgroundImage = new ImageIcon(path).getImage();
            } catch (Exception e) {
                System.err.println("Error loading background image: " + e.getMessage());
                setBackground(DARK_BG); 
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    class StartGameButton extends JButton {
        public StartGameButton(String text) {
            super(text);
            setContentAreaFilled(false); 
            setFocusPainted(false); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int arc = height; 

            g2.setColor(BUTTON_INNER_COLOR);
            g2.fillRoundRect(0, 0, width, height, arc, arc);

            super.paintComponent(g); 
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int arc = height; 
            int borderWidth = 6; 

            g2.setColor(NEON_PINK); 
            g2.setStroke(new BasicStroke(borderWidth)); 

            g2.drawRoundRect(borderWidth / 2, borderWidth / 2, 
                             width - borderWidth, height - borderWidth, 
                             arc, arc);

            g2.dispose();
        }

        @Override
        public void setFocusPainted(boolean b) {
            super.setFocusPainted(false);
        }
    }
    
    class RoundedTextField extends JTextField {
        private final int cornerRadius = 35; 

        public RoundedTextField(int columns) {
            super(columns);
            setOpaque(false); 
            setBorder(null); 
            setUI(new CustomTextFieldUI()); 
        }

        @Override
        public Border getBorder() {
            return BorderFactory.createEmptyBorder(5, 10, 5, 10); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            g2.setColor(getBackground()); 
            g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

            super.paintComponent(g); 
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int borderWidth = 5; 

            g2.setColor(Color.BLUE); 
            g2.setStroke(new BasicStroke(borderWidth));

            g2.drawRoundRect(borderWidth / 2, borderWidth / 2, 
                             width - borderWidth, height - borderWidth, 
                             cornerRadius, cornerRadius);

            g2.dispose();
        }

        private class CustomTextFieldUI extends BasicTextFieldUI {
        }
    }

    class WelcomeContainerPanel extends JPanel {
        private final Container contentContainer; 
        private final Color BORDER_COLOR = Color.BLUE; 
        private final int BORDER_THICKNESS = 5;
        private final int CORNER_RADIUS = 20;

        public WelcomeContainerPanel(Container container) {
            this.contentContainer = container;
            setLayout(new BorderLayout()); 
            setOpaque(false); 
            
            add(contentContainer, BorderLayout.CENTER); 
        }
        
        @Override
        protected void paintComponent(Graphics g) {
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            int w = getWidth();
            int h = getHeight();

            g2.setStroke(new BasicStroke(BORDER_THICKNESS));
            g2.setColor(BORDER_COLOR);
            g2.drawRoundRect(0, 0, w - 1, h - 1, CORNER_RADIUS, CORNER_RADIUS);

            g2.dispose();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g); 
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KBCG_Interface());
    }
}