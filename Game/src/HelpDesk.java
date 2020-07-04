import javax.swing.*;
import java.awt.*;

public class HelpDesk extends JFrame {

    // Important constants
    private static final int HEADLINE_FONT_SIZE = 20;

    // GUI elements
    private JScrollPane scrollPane1;
    private JPanel fullStorage;
    private JPanel containsTwoElements;
    private JPanel containsOneElement;
    private JLabel storageColorCodeHeadline;
    private JPanel containsNoElements;
    private JPanel selectAvailableMoveElement;
    private JPanel moveTargetElement;
    private JLabel keyboardShortcutInformationHeadline;

    /**
     * Initialize attributes
     */
    public HelpDesk() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setTitle("Hilfe");
        this.setContentPane(scrollPane1);
        this.pack();

        this.storageColorCodeHeadline.setFont(getStandardHeadlineFont());
        this.keyboardShortcutInformationHeadline.setFont(getStandardHeadlineFont());

        this.containsOneElement.setBackground(Color.GREEN);
        this.containsTwoElements.setBorder(View.getStandardBorder(Color.YELLOW, false));
        this.fullStorage.setBorder(View.getStandardBorder(Color.RED, false));
        this.containsNoElements.setBorder(View.getStandardBorder(Color.WHITE, false));
        this.selectAvailableMoveElement.setBorder(View.getStandardBorder(Color.CYAN, false));
        this.moveTargetElement.setBorder(View.getStandardBorder(Color.MAGENTA, false));
    }

    public static Font getStandardHeadlineFont() {
        return new Font("san-serif", Font.BOLD, HelpDesk.HEADLINE_FONT_SIZE);
    }
}
