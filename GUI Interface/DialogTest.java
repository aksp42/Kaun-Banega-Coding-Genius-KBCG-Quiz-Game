import javax.swing.*;
import java.awt.*;


public class DialogTest {

    public static void main(String[] args) {
        
        
        SwingUtilities.invokeLater(() -> {
            
           
            JFrame dummyOwner = new JFrame("Dialog Test Runner");
            dummyOwner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dummyOwner.setSize(300, 200);
            dummyOwner.setLocationRelativeTo(null);
            dummyOwner.setVisible(true); 

            System.out.println("Testing Level Up Dialog (INFO theme)...");
            String levelUpTitle = "LEVEL UP!";
            String levelUpMessage = "Excellent work, <b>AkankSha</b>! \uD83C\uDFC6<br><br>" + 
                                    "You have unlocked <b>Level 2</b><br>" + 
                                    "in Python challenge.<br><br>" + 
                                    "Get ready for tougher questions!";
                                    
            
            CustomMessageDialog.showLevelUp(dummyOwner, levelUpTitle, levelUpMessage);
            
            System.out.println("Testing Game Win Dialog (WIN theme)...");
            String winTitle = "CONGRATULATIONS!";
            String winMessage = "Incredible! You have conquered all 3 levels! \u2B50<br>You are a true Code Genius!<br><br>Final Score: 12<br><br>Thank you for playing!";
        
            CustomMessageDialog.showGameEnd(dummyOwner, winTitle, winMessage, "WIN");

           
        });
    }
}