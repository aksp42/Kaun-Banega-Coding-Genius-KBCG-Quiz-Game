import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import javax.swing.plaf.basic.BasicButtonUI;
import java.io.File; 
import javax.imageio.ImageIO; 
import java.io.IOException;
import java.io.BufferedReader; 
import java.io.FileReader;    
import java.util.stream.Collectors; 



class Player {
    private String name;
    private int score;
    private int lives;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.lives = 3; 
    }

    public void addScore() { score++; }
    public void loseLife() { lives--; }

    public int getScore() { return score; }
    public int getLives() { return lives; }
    public String getName() { return name; }
}

class Question {
    private String question;
    private String optionA, optionB, optionC, optionD;
    private String answer; 

    public Question(String question, String a, String b, String c, String d, String ans) {
        this.question = question;
        this.optionA = a;
        this.optionB = b;
        this.optionC = c;
        this.optionD = d;
        this.answer = ans.toUpperCase(); 
    }

    public String getQuestionText() { return question; }
    
    public List<String> getOptions() {
        return List.of(optionA, optionB, optionC, optionD);
    }
    
    public String getCorrectOptionText() {
        return switch (answer) {
            case "A" -> optionA;
            case "B" -> optionB;
            case "C" -> optionC;
            case "D" -> optionD;
            default -> "Unknown";
        };
    }
    
    public String getCorrectAnswerCode() {
        return answer;
    }

    public boolean checkAnswer(String ans) {
        return ans.equalsIgnoreCase(answer);
    }
}


class QuizManager {
    private final Player player;
    private final String language;
    private int currentLevel;
    private int questionIndex;
    private List<Question> currentLevelQuestions;

    public QuizManager(Player player, String language) {
        this.player = player;
        this.language = language;
        this.currentLevel = 1;
        this.questionIndex = 0;
        loadNextLevel();
    }
    
    public String getLanguage() {
        return language;
    }
    
    private int getNumQuestionsForLevel(int level) {
         return switch (level) {
             case 1 -> 5; 
             case 2 -> 4; 
             case 3 -> 3; 
             default -> 0;
         };
    }
    
    private List<Question> loadQuestions(String language, int level) {
        List<Question> questions = new ArrayList<>();
        String fileName = language + "_L" + level + ".csv"; 
        
        try {
            
            File file = new File(fileName);
            if (!file.exists()) {
                 System.err.println("File not found: " + fileName);
                 return Collections.emptyList();
            }

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine(); 

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    questions.add(new Question(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error reading file " + fileName + ". Check file path/existence: " + e.getMessage());
            return Collections.emptyList(); 
        }
        return questions;
    }
    
    public boolean loadNextLevel() {
        if (currentLevel > 3) return false;
        
        currentLevelQuestions = loadQuestions(language, currentLevel);
        if (currentLevelQuestions.isEmpty()) return false;
        
        Collections.shuffle(currentLevelQuestions);
        int required = getNumQuestionsForLevel(currentLevel);
        
        if (currentLevelQuestions.size() > required) {
             currentLevelQuestions = currentLevelQuestions.subList(0, required);
        }
        
        questionIndex = 0;
        return true;
    }

    public Question getCurrentQuestion() {
        if (currentLevelQuestions == null || questionIndex >= currentLevelQuestions.size()) {
            return null;
        }
        return currentLevelQuestions.get(questionIndex);
    }
    
    public int getCurrentLevel() { return currentLevel; }
    public int getCurrentQuestionNumber() { return questionIndex + 1; }
    public Player getPlayer() { return player; }
    
    public boolean checkAnswer(String selectedOptionCode) {
        Question q = getCurrentQuestion();
        if (q == null) return false;

        boolean isCorrect = q.checkAnswer(selectedOptionCode);

        if (isCorrect) {
            player.addScore();
        } else {
            player.loseLife();
        }
        return isCorrect;
    }
    
    public boolean moveToNextQuestion() {
        questionIndex++;
        return questionIndex < currentLevelQuestions.size();
    }
    
    public boolean isLevelFinished() {
        return questionIndex >= currentLevelQuestions.size();
    }
    
    public boolean isGameOver() {
        return player.getLives() <= 0;
    }
    
    public boolean advanceToNextStage() {
        if (isLevelFinished()) {
             currentLevel++;
             if (currentLevel > 3) return false;
             return loadNextLevel();
        }
        return true; 
    }
}


class BackgroundPanel extends JPanel {
    private Image backgroundImage;
  
    private static final String BACKGROUND_IMAGE_PATH = "C:\\Users\\HP\\OneDrive\\Desktop\\k2.png"; 

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH)); 
        } catch (IOException e) {
            System.err.println("Background image failed to load: " + BACKGROUND_IMAGE_PATH);
            backgroundImage = null;
        }
        setLayout(new BorderLayout(10, 10)); 
        setOpaque(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(25, 40, 60));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}

class RoundedButtonUI extends BasicButtonUI {
    private final int ARC_WIDTH = 40; 
    private final int ARC_HEIGHT = 40; 

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
        ((AbstractButton) c).setFocusPainted(false);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();
        
        Color buttonColor = button.getBackground();

        if (model.isArmed()) {
            buttonColor = buttonColor.darker();
        }

        int arcWidth;
        int arcHeight = c.getHeight(); 

        if (button.getText().equals("CONTINUE")) { 
            arcWidth = 20; 
            arcHeight = 20;
        } else {
            arcWidth = ARC_WIDTH;
            arcHeight = ARC_HEIGHT;
        }
        
        g2.setColor(buttonColor);
        g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), arcWidth, arcHeight);
        
        g2.dispose();
        
        paintTextAndIcon(g, c, button); 
    }

    protected void paintTextAndIcon(Graphics g, JComponent c, AbstractButton button) {
        FontMetrics fm = g.getFontMetrics();
        Rectangle viewRect = new Rectangle(c.getSize());
        Rectangle iconRect = new Rectangle();
        Rectangle textRect = new Rectangle();
        
        int LEFT_PADDING = 30; 
        viewRect.x += LEFT_PADDING;
        viewRect.width -= LEFT_PADDING;

        String text = SwingUtilities.layoutCompoundLabel(
            button, fm, button.getText(), button.getIcon(), 
            button.getVerticalAlignment(), button.getHorizontalAlignment(), 
            button.getVerticalTextPosition(), button.getHorizontalTextPosition(), 
            viewRect, iconRect, textRect, 
            button.getText() == null ? 0 : button.getIconTextGap()
        );
        
        if (button.isEnabled()) {
            g.setColor(button.getForeground());
        } else {
            g.setColor(button.getForeground()); 
        }
        
        if (text != null && !text.isEmpty()) {
            g.setFont(button.getFont());
            g.drawString(text, textRect.x, textRect.y + fm.getAscent());
        }
    }
}


public class QuizChallengeGUI extends JFrame {
    
    private JPanel headerPanel; 
    private JLabel questionLabel; 
    private JPanel feedbackPanel;
    private JLabel feedbackLabel;
    private JButton continueButton;
    private List<JButton> optionButtons; 
    
    private QuizManager quizManager;
    private final String[] ALPHABET_PREFIXES = {"A.", "B.", "C.", "D."};
    private final String[] OPTION_CODES = {"A", "B", "C", "D"}; 

    private final Color[] OPTION_COLORS = {
        new Color(102, 51, 153), 
        new Color(0, 153, 153),
        new Color(255, 102, 0),
        new Color(139, 69, 19)
    };
    
    private final Color QUESTION_BOX_BG = new Color(50, 70, 90, 200); 
    private final Color WRONG_ANSWER_BG = new Color(200, 40, 40);
    private final Color CORRECT_ANSWER_BG = new Color(50, 168, 82);
    private final Color CONTINUE_BUTTON_BG = new Color(255, 180, 0);
    private final Color QUESTION_BORDER_COLOR = new Color(50, 150, 255); 


    public QuizChallengeGUI(String name, String lang) {
        
        Player player = new Player(name); 
        this.quizManager = new QuizManager(player, lang);
        
        
        if (quizManager.getCurrentQuestion() == null) {
            JOptionPane.showMessageDialog(null, 
                "Error: No quiz questions loaded for " + lang + ".\nPlease ensure CSV files (e.g., " + lang + "_L1.csv) are in the correct directory.", 
                "Loading Error", 
                JOptionPane.ERROR_MESSAGE);
            
           
            SwingUtilities.invokeLater(() -> new KBCG_Interface());
            dispose();
            return;
        }
        
        setTitle(lang.toUpperCase() + " Quiz Challenge - KBCG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800); 
        setResizable(false);
        setLocationRelativeTo(null);
        optionButtons = new ArrayList<>();
        
        BackgroundPanel mainPanel = new BackgroundPanel(""); 
        add(mainPanel);

        
        headerPanel = createHeaderPanel(); 
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(60, 30, 0, 30)); 

        JPanel questionWrapper = createQuestionPanel("Initial Question Text...");
        contentPanel.add(questionWrapper);
        contentPanel.add(Box.createVerticalStrut(15)); 

        for (int i = 0; i < 4; i++) {
            JButton optionButton = createOptionButton("Option " + (i + 1), OPTION_COLORS[i], Color.WHITE);
            optionButton.setActionCommand(OPTION_CODES[i]); 
            optionButton.addActionListener(new AnswerListener());
            optionButtons.add(optionButton);
            contentPanel.add(optionButton);
            contentPanel.add(Box.createVerticalStrut(15));
        }

        feedbackPanel = createFeedbackPanel();
        contentPanel.add(feedbackPanel);
        contentPanel.add(Box.createVerticalGlue());

        continueButton = createContinueButton("CONTINUE", CONTINUE_BUTTON_BG, Color.BLACK);
        continueButton.addActionListener(new ContinueListener()); 
        
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30)); 
        footerPanel.add(continueButton, BorderLayout.CENTER);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        continueButton.setEnabled(false);
        
        loadQuestionToUI();
        
        setVisible(true);
    }
    
    
    private void loadQuestionToUI() {
        Question currentQ = quizManager.getCurrentQuestion();
        if (currentQ == null) return;

        headerPanel.removeAll();
        headerPanel.add(createHeaderComponents(), BorderLayout.CENTER);
        headerPanel.revalidate();
        headerPanel.repaint();
        
        
        questionLabel.setText("<html><div style='text-align: center;'>" + 
                              "Q" + quizManager.getCurrentQuestionNumber() + ". " + 
                              currentQ.getQuestionText() + "</div></html>");

        List<String> options = currentQ.getOptions();
        for (int i = 0; i < 4; i++) {
            JButton button = optionButtons.get(i);
            button.setText(ALPHABET_PREFIXES[i] + " " + options.get(i));
            button.setActionCommand(OPTION_CODES[i]); 
            button.setBackground(OPTION_COLORS[i]);
            button.setEnabled(true);
        }
        
        feedbackPanel.setVisible(false);
        continueButton.setEnabled(false);
        
        revalidate();
        repaint();
    }
    
    

    private void showGameOverScreen() {
        String title = "GAME OVER!";
        String message = "Oops! You ran out of lives. \u274C<br>Your final score: " + quizManager.getPlayer().getScore() + "<br><br>Better luck next time, Coder!";
        CustomMessageDialog.showGameEnd(this, title, message, "LOSS"); 
    }
    
    private void showGameWinScreen() {
        String title = "CONGRATULATIONS!";
        String message = "Incredible! You have conquered all " + (quizManager.getCurrentLevel() - 1) + " levels! \u2B50<br>You are a true Code Genius!<br><br>Final Score: " + quizManager.getPlayer().getScore() + "<br><br>Thank you for playing!";
        CustomMessageDialog.showGameEnd(this, title, message, "WIN"); 
    }


    
    private JPanel createHeaderComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0)); 
        
        JLabel titleLabel = new JLabel(quizManager.getLanguage().toUpperCase() + " QUIZ"); 
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel nameLabel = new JLabel("Player: " + quizManager.getPlayer().getName());
        nameLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        nameLabel.setForeground(new Color(255, 200, 0));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel levelLabel = new JLabel(
            "<html>" +
            "<span style='font-size: 24px; color: white;'>LEVEL " + quizManager.getCurrentLevel() + "</span> &nbsp;" + 
            "<span style='font-size: 30px; color: #FFD700;'>\u2605</span> &nbsp;" + 
            "<span style='font-size: 24px; color: white;'>" + quizManager.getPlayer().getScore() + "</span> &nbsp;" + 
            "<span style='font-size: 30px; color: #FF4500;'>\u2764</span>" + 
            "<span style='font-size: 24px; color: white;'>" + quizManager.getPlayer().getLives() + "</span>" +
            "</html>"
        );
        
        JPanel levelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        levelWrapper.setOpaque(false);
        levelWrapper.add(levelLabel);
        
        panel.add(titleLabel);
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(levelWrapper);
        return panel;
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout()); 
        panel.setOpaque(false);
        return panel; 
    }

    
    private JPanel createQuestionPanel(String text) {
        JPanel qPanel = new JPanel();
        qPanel.setLayout(new BorderLayout()); 
        
        qPanel.setBackground(QUESTION_BOX_BG); 
        qPanel.setBorder(
            BorderFactory.createCompoundBorder(
                new LineBorder(QUESTION_BORDER_COLOR, 3), 
               
                BorderFactory.createEmptyBorder(20, 20, 20, 20) 
            )
        );
        qPanel.setMaximumSize(new Dimension(540, 200)); 
        qPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        questionLabel = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setForeground(Color.WHITE);
        
        qPanel.add(questionLabel, BorderLayout.CENTER);
        
        return qPanel;
    }
    
    private JButton createOptionButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setUI(new RoundedButtonUI());
        button.setBackground(bgColor);
        button.setForeground(fgColor); 
        button.setFocusPainted(false); 
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Arial", Font.BOLD, 22)); 
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); 
        button.setMaximumSize(new Dimension(540, 60)); 
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
    
    private JPanel createFeedbackPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.setMaximumSize(new Dimension(540, 60)); 
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        feedbackLabel = new JLabel();
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 16));
        feedbackLabel.setForeground(Color.WHITE);
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(feedbackLabel, BorderLayout.CENTER);
        
        panel.setVisible(false);
        return panel;
    }

    private JButton createContinueButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setUI(new RoundedButtonUI());
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 20)); 
        button.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0)); 
        return button;
    }
    
    

    private class AnswerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            String selectedOptionCode = selectedButton.getActionCommand(); 
            
            Question currentQ = quizManager.getCurrentQuestion();
            if (currentQ == null) return;
            
            boolean isCorrect = quizManager.checkAnswer(selectedOptionCode);
            
            for (JButton button : optionButtons) {
                button.setEnabled(false);
            }
            continueButton.setEnabled(true);
            
            String correctOptionText = currentQ.getCorrectOptionText();
            String correctOptionCode = currentQ.getCorrectAnswerCode();

            if (isCorrect) {
                selectedButton.setBackground(CORRECT_ANSWER_BG);
                feedbackPanel.setBackground(CORRECT_ANSWER_BG);
                feedbackLabel.setText("Correct Answer! \u2705 (+1 Score)"); 
            } else {
                selectedButton.setBackground(WRONG_ANSWER_BG);
                feedbackPanel.setBackground(WRONG_ANSWER_BG);
                
                if (quizManager.isGameOver()) {
                     feedbackLabel.setText("<html><div style='text-align: center;'>Wrong Answer! \u274C Game Over!</div></html>");
                } else {
                     feedbackLabel.setText("<html><div style='text-align: center;'>Wrong Answer! \u274C (-1 Life)<br>Correct Answer was: " + correctOptionCode + ". " + correctOptionText + "</div></html>");
                }
                
                for (JButton button : optionButtons) {
                    if (button.getActionCommand().equalsIgnoreCase(correctOptionCode)) { 
                        button.setBackground(CORRECT_ANSWER_BG);
                    }
                }
            }
            
            headerPanel.removeAll();
            headerPanel.add(createHeaderComponents(), BorderLayout.CENTER);
            headerPanel.revalidate();
            headerPanel.repaint();

            feedbackPanel.setVisible(true);
            revalidate();
            repaint();
        }
    }
    
    private class ContinueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (quizManager.isGameOver()) {
                showGameOverScreen();
                return;
            }
            
            if (quizManager.isLevelFinished()) {
                if (quizManager.advanceToNextStage()) {
                    
                    String title = "LEVEL UP!";
                    
                    
                    String message = "Excellent work, <b>" + quizManager.getPlayer().getName() + "</b>! \uD83C\uDFC6<br><br>" + 
                                     "You have unlocked <b>Level " + quizManager.getCurrentLevel() + "</b><br>" + 
                                     "in " + quizManager.getLanguage() + " challenge.<br><br>" + 
                                     "Get ready for tougher questions!"; 
                    
                    CustomMessageDialog.showLevelUp(QuizChallengeGUI.this, title, message);
                    
                    loadQuestionToUI();
                } else {
                    showGameWinScreen(); 
                }
            } else {
                quizManager.moveToNextQuestion();
                loadQuestionToUI();
            }
        }
    }
}