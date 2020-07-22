import javax.swing.*;
import java.awt.*;

public class GameTarget extends JFrame {

    // Attributes of object
    private JTextPane gameDescription;
    private JPanel rootPane;
    private JLabel gameDescriptionHeadline;

    /**
     * Initializing attributes and JFrame object
     */
    public GameTarget() {
        this.setTitle("Ziel");
        initializeGUI();
        this.setContentPane(rootPane);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(true);
    }

    private void initializeGUI() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = 1;
        constraints.gridwidth = 5;
        constraints.gridx = 2;
        constraints.gridy = 0;

        rootPane = new JPanel(new GridBagLayout());
        JLabel headline = new JLabel("Spielziel");
        headline.setFont(HelpDesk.getStandardHeadlineFont());
        rootPane.add(headline, constraints);

        constraints.gridy++;
        constraints.insets = new Insets(20,0,0,0);
        constraints.gridheight = 10;
        constraints.weightx = 1;

        JTextPane content = new JTextPane();
        content.setEditable(false);
        content.setText(Start.toUtf8("In diesem Spiel spieln sie einen Lageristen. Hier bekommen sie immer " +
                "Aufträge die sie entweder liefern sollen also auslagern oder müssen neue Produkte einlagern. Bei " +
                "Platzproblemen kann umgelagert oder verschrottet werden. Diese Funktionen kosten sie allerdings " +
                "Geld. Umalgern kostet 100 Euro und Verschrotten 500 Euro. Im Spielverlauf kann auch ein negativer " +
                "Gesamtbetrag enstehen. Das Spiel ist vorbei, wenn sie 3 Auslieferungsaufträge haben und keinen " +
                "abarbeiten können, weil die Produkte fehlen."));
        rootPane.add(content, constraints);
    }
}
