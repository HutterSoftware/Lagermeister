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
        content.setText("In diesem Spiel schlüpfen sie in die Haut eines Lageristen. Hier bekommen sie immer Aufträge " +
                "die sie entweder liefern sollen also auslagern oder müssen neue Produkte einlagern. Falls sie " +
                "Platzprobleme bekommen, können sie Produkte umlagern oder zerstören. Diese Funktionen kosten sie " +
                "allerdings Geld. Für eine Umlagerung müssen sie 100€ bezahlen und für die Zerstörung 500€. Während " +
                "des Spielverlaufes kann auch ein negativer Gesamtbetrag enstehen. Das Spiel ist vorbei, wenn sie " +
                "keine Objekte liefern können, weil die Produkte nicht im Lager sind und keine neuen Aufträge " +
                "beziehen können.");
        rootPane.add(content, constraints);
    }
}
