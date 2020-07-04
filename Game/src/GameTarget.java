import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class GameTarget extends JFrame{
    private JTextPane gameDescription;
    private JPanel rootPane;
    private JLabel gameDescriptionHeadline;

    public GameTarget() {
        this.setTitle("Ziel");
        this.setContentPane(rootPane);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(true);
        this.pack();

        gameDescriptionHeadline.setFont(HelpDesk.getStandardHeadlineFont());
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                System.out.print(e.getWindow().getWidth());
                System.out.print("  ");
                System.out.println(e.getWindow().getHeight());
            }
        });
        gameDescription.setEditable(false);
    }
}
