import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class GameTarget extends JFrame{

    // Attributes of object
    private JTextPane gameDescription;
    private JPanel rootPane;
    private JLabel gameDescriptionHeadline;

    /**
     * Initializing attributes and JFrame object
     */
    public GameTarget() {
        this.setTitle("Ziel");
        this.setContentPane(rootPane);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(true);
        this.pack();

        // Setting GUI elements up
        gameDescriptionHeadline.setFont(HelpDesk.getStandardHeadlineFont());
        gameDescription.setEditable(false);
    }
}
