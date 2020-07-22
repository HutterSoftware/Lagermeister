import javax.swing.*;
import java.awt.*;

public class HelpDesk extends JFrame {

    // Important constants
    private static final int HEADLINE_FONT_SIZE = 20;

    // GUI elements
    private JScrollPane scrollPane1;
    private JLabel fullStorage;
    private JLabel containsTwoElements;
    private JLabel containsOneElement;
    private JLabel storageColorCodeHeadline;
    private JLabel containsNoElements;
    private JLabel selectAvailableMoveElement;
    private JLabel moveTargetElement;
    private JLabel keyboardSelectedElement;
    private JLabel keyboardShortcutInformationHeadline;
    private int rowNumber;

    /**
     * Initialize attributes
     */
    public HelpDesk() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setTitle("Hilfe");
        initializeGUI();
        setSize(550,500);

        this.storageColorCodeHeadline.setFont(getStandardHeadlineFont());
        this.keyboardShortcutInformationHeadline.setFont(getStandardHeadlineFont());

        this.containsOneElement.setBorder(View.getStandardBorder(Color.GREEN, false));
        this.containsTwoElements.setBorder(View.getStandardBorder(Color.YELLOW, false));
        this.fullStorage.setBorder(View.getStandardBorder(Color.RED, false));
        this.containsNoElements.setBorder(View.getStandardBorder(Color.WHITE, false));
        this.selectAvailableMoveElement.setBorder(View.getStandardBorder(Color.MAGENTA, false));
        this.moveTargetElement.setBorder(View.getStandardBorder(Color.CYAN, false));
        this.keyboardSelectedElement.setBorder(View.getStandardBorder(Color.GREEN, true));
    }

    private void initializeGUI() {
        JPanel rootElement = new JPanel(new GridBagLayout());
        scrollPane1 = new JScrollPane();
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints elementConstraints = new GridBagConstraints();
        elementConstraints.fill = GridBagConstraints.BOTH;
        elementConstraints.gridx = 0;
        elementConstraints.gridy = 0;

        storageColorCodeHeadline = new JLabel("Farbbedeutung");
        addElementToPanel(storageColorCodeHeadline, null, panel);

        fullStorage = new JLabel("Produkt");
        addElementToPanel(fullStorage, "Lagerplatz ist bereits voll", panel);

        containsTwoElements = new JLabel("Produkt");
        addElementToPanel(containsTwoElements, Start.toUtf8("Lagerplatz hat 2 Elemente"), panel);

        containsOneElement = new JLabel("Produkt");
        addElementToPanel(containsOneElement, Start.toUtf8("Lagerplatz hatt nur 1 Element"), panel);

        containsNoElements = new JLabel("Produkt");
        addElementToPanel(containsNoElements, Start.toUtf8("Der Lagerplatz hat kein Element"), panel);

        selectAvailableMoveElement = new JLabel("Produkt");
        addElementToPanel(selectAvailableMoveElement,
                Start.toUtf8("Zeigt Elemente an die man umgelagern kann"),
                panel);

        moveTargetElement = new JLabel("Produkt");
        addElementToPanel(moveTargetElement, Start.toUtf8("<html>Zeigt Plätze an, an denen das<br>" +
                "Objekt umgelagert werden kann</html>"), panel);

        keyboardSelectedElement = new JLabel("Produkt");
        addElementToPanel(keyboardSelectedElement, Start.toUtf8("<html>Der gepunktete Rahmen eines Lagerplatz" +
                "<br>sagt aus, dass der Inhalt fokussiert ist</html>"), panel);

        keyboardShortcutInformationHeadline = new JLabel("Shortcuts");
        addElementToPanel(keyboardShortcutInformationHeadline, null, panel);

        addElementToPanel("h oder H", Start.toUtf8("Zeigt dieses Hilfe-Fenster an"), panel);
        addElementToPanel("b oder B", Start.toUtf8("Zeigt die bisherige Bilanz des Spiels an"), panel);
        addElementToPanel("n oder N", Start.toUtf8("Hole neuen Auftrag"),panel);
        addElementToPanel("t oder T", Start.toUtf8("<html>Im angeklickten Zusatnd wird das erste " +
                "Element<br> des Lagerplatzes entfernt.<br>Im ausgeklickten Zustand bleiben<br>" +
                "angeklickte Elemente erhalten</html>"), panel);

        addElementToPanel("m oder M", Start.toUtf8("<html>Nach erstmaligen klicken kann ein Objekt " +
                "selektiert<br>werden. Danach kann ein neuer Lagerplatz selektiert werden" +
                "</html>"), panel);

        addElementToPanel("q oder Q", Start.toUtf8("<html>Zeigt in der obigen Ansicht den linken<br>" +
                "Auftrag neben dem aktuellen<br>Auftrag an</html>"), panel);

        addElementToPanel("r oder R", Start.toUtf8("<html>Zeigt in der obigen Ansicht den rechten" +
                "<br>Auftrag neben dem aktuellen<br>Auftrag an</html>"), panel);

        addElementToPanel("w oder W", Start.toUtf8("<html>Fokussiert den Lagerplatz oberhalb<br>der " +
                "aktuellen Position</html>"), panel);

        addElementToPanel("d oder D", Start.toUtf8("<html>Fokussiert den Lagerplatz rechts<br>von der " +
                "aktuellen Position</html>"), panel);

        addElementToPanel("s oder S", Start.toUtf8("<html>Fokussiert den Lagerplatz unterhalb<br>der " +
                "aktuellen Position</html>"), panel);

        addElementToPanel("a oder A", Start.toUtf8("<html>Fokussiert den Lagerplatz links<br>von der " +
                "aktuellen Position</html>"), panel);

        addElementToPanel("z oder Z", Start.toUtf8("Öffnet die Spielbeschreibung"), panel);

        addElementToPanel("Leertaste", Start.toUtf8("Hat die gleiche Wirkung wie ein Tastendruck"), panel);

        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.add(panel);
        scrollPane1.setViewportView(panel);
        scrollPane1.setSize(500,800);

        setContentPane(scrollPane1);
    }

    private void addElementToPanel(String label, String descriptionText, JPanel panel) {
        Insets insets = new Insets(5,5,5,5);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = insets;
        constraints.gridx = 0;
        constraints.gridy = rowNumber;

        JLabel labelComponent = new JLabel(label);
        labelComponent.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(labelComponent, constraints);

        if (descriptionText != null) {
            constraints.gridx++;
            panel.add(new JLabel(descriptionText), constraints);
        }

        rowNumber++;
    }

    private void addElementToPanel(JLabel label, String descriptionText, JPanel panel) {
        Insets insets = new Insets(5,5,5,5);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = insets;
        constraints.gridx = 0;
        constraints.gridy = rowNumber;


        label.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(label, constraints);

        if (descriptionText != null) {
            constraints.gridx++;
            panel.add(new JLabel(descriptionText), constraints);
        }

        rowNumber++;
    }

    /**
     * Creating standard headline font
     *
     * @return Font
     */
    public static Font getStandardHeadlineFont() {
        return new Font("san-serif", Font.BOLD, HelpDesk.HEADLINE_FONT_SIZE);
    }
}
