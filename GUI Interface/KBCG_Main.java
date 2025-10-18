import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class KBCG_Main {
    public static void main(String[] args) {
        try {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }
        
       
        SwingUtilities.invokeLater(() -> new KBCG_Interface());
    }
}