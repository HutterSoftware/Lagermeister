import javax.swing.*;
import java.awt.*;

public class HelpDesk extends JFrame {

    private JPanel contentPane;
    private JPanel hasElementPanel;
    private JPanel containsOneElementPanel;
    private JPanel containsTwoElementsPanel;
    private JPanel hasNotElementPanel;
    private JPanel containsThreeElementsPanel;

    public HelpDesk() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setTitle("Hilfe");
        this.setContentPane(contentPane);
        this.pack();

        this.hasElementPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        this.containsOneElementPanel.setBackground(Color.GREEN);
        this.containsTwoElementsPanel.setBackground(Color.YELLOW);
        this.containsThreeElementsPanel.setBackground(Color.RED);
        this.hasNotElementPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        this.setVisible(true);
    }
}
