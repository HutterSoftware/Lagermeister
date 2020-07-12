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
        addElementToPanel(containsTwoElements, "Lagerplatz enthält 2 Elemente", panel);

        containsOneElement = new JLabel("Produkt");
        addElementToPanel(containsOneElement, "Lagerplatz enthölt nur 1 Element", panel);

        containsNoElements = new JLabel("Produkt");
        addElementToPanel(containsNoElements, "Der Lagerplatz enthält kein Element", panel);

        selectAvailableMoveElement = new JLabel("Produkt");
        addElementToPanel(selectAvailableMoveElement, "Zeigt Element an die Umgelagert werden können",
                panel);

        moveTargetElement = new JLabel("Produkt");
        addElementToPanel(moveTargetElement, "<html>Zeigt Plätze an, an denen das gewünschte<br>Objekt " +
                "umgelagert werden kann</html>", panel);

        keyboardSelectedElement = new JLabel("Produkt");
        addElementToPanel(keyboardSelectedElement, "<html>Der gepunktete Rahmen eines Lagerplatz<br>" +
                "sagt aus, dass der Inhalt fokussiert ist</html>", panel);

        keyboardShortcutInformationHeadline = new JLabel("Shortcuts");
        addElementToPanel(keyboardShortcutInformationHeadline, null, panel);

        addElementToPanel("h oder H", "Zeigt dieses Hilfe-Fenster an", panel);
        addElementToPanel("b oder B", "Zeigt die bisherige Bilanz des Spiels an", panel);
        addElementToPanel("n oder N", "Lädt neuen Auftrag",panel);
        addElementToPanel("t oder T", "<html>In gedrücktem Zusatnd wird das erste " +
                "Element<br>Elemente des Lagerplatzes gelöscht.<br>In nicht gedrücktem Zustand bleiben<br>" +
                "angeklickte Elemente erhalten</html>", panel);

        addElementToPanel("m oder M", "<html>Nach dem anklicken des Buttons können<br>" +
                "Elemente angeklickt oder mit der<br>Tastatur ausgewählt werden, die umgelagert<br>werden sollten" +
                "</html>", panel);

        addElementToPanel("q oder Q", "<html>Zeigt in der obigen Ansicht den linken<br>" +
                "Auftrag neben dem aktuellen<br>Auftrag an</html>", panel);

        addElementToPanel("r oder R", "<html>Zeigt in der obigen Ansicht den rechten" +
                "<br>Auftrag neben dem aktuellen<br>Auftrag an</html>", panel);

        addElementToPanel("w oder W", "<html>Fokussiert den Lagerplatz oberhalb<br>der aktuellen " +
                "Position</html>", panel);

        addElementToPanel("d oder D", "<html>Fokussiert den Lagerplatz rechts<br>von der aktuellen " +
                "Position</html>", panel);

        addElementToPanel("s oder S", "<html>Fokussiert den Lagerplatz unterhalb<br>der aktuellen " +
                "Position</html>", panel);

        addElementToPanel("a oder A", "<html>Fokussiert den Lagerplatz links<br>von der aktuellen " +
                "Position</html>", panel);

        addElementToPanel("z oder Z", "Öffnet die Spielbeschreibung", panel);

        addElementToPanel("Leertaste", "Hat die gleiche Wirkung wie ein Tastendruck", panel);

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

/*    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     *
    private void $$$setupUI$$$() {
        scrollPane1 = new JScrollPane();
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(17, 7, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setForeground(new Color(-9274759));
        scrollPane1.setViewportView(panel1);
        storageColorCodeHeadline = new JLabel();
        storageColorCodeHeadline.setText("Farbbedeutung der Lagerplätze");
        panel1.add(storageColorCodeHeadline, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(1, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        fullStorage = new JPanel();
        fullStorage.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(fullStorage, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setHorizontalAlignment(2);
        label1.setHorizontalTextPosition(2);
        label1.setText("Produkt");
        fullStorage.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Lagerplatz ist bereits voll");
        panel1.add(label2, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        containsTwoElements = new JPanel();
        containsTwoElements.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(containsTwoElements, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Produkt");
        containsTwoElements.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Platz enthält 2 Produkte");
        panel1.add(label4, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        containsOneElement = new JPanel();
        containsOneElement.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(containsOneElement, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Produkt");
        containsOneElement.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Platz enthält 1 Element");
        panel1.add(label6, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        containsNoElements = new JPanel();
        containsNoElements.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(containsNoElements, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Produkt");
        containsNoElements.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Platz enthält keine Elemente");
        panel1.add(label8, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel1.add(spacer5, new GridConstraints(16, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        keyboardShortcutInformationHeadline = new JLabel();
        keyboardShortcutInformationHeadline.setText("Spiel Shortcuts");
        panel1.add(keyboardShortcutInformationHeadline, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("h oder H");
        panel1.add(label9, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("b oder B");
        panel1.add(label10, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("n oder N");
        panel1.add(label11, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("m oder M");
        panel1.add(label12, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("t oder T");
        panel1.add(label13, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("q oder Q");
        panel1.add(label14, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("r oder R");
        panel1.add(label15, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("w oder W");
        panel1.add(label16, new GridConstraints(10, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("d oder D");
        panel1.add(label17, new GridConstraints(11, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("s oder S");
        panel1.add(label18, new GridConstraints(12, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        label19.setText("a oder A");
        panel1.add(label19, new GridConstraints(13, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("Hilfe Fenster anzeigen");
        panel1.add(label20, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel1.add(spacer6, new GridConstraints(1, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label21 = new JLabel();
        label21.setText("Vergangene Aufträge anzeigen");
        panel1.add(label21, new GridConstraints(4, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("Neuen Auftrag anfordern");
        panel1.add(label22, new GridConstraints(5, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setText("1. Drücken aktiviert Zerstörung bei auswählen. 2. Mal deaktiviert den Mechanismus");
        panel1.add(label23, new GridConstraints(6, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setText("Zeigt Elemente an die umgelager werden können. Deaktiviert sich nach umlagern");
        panel1.add(label24, new GridConstraints(7, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label25 = new JLabel();
        label25.setText("Zeigt den vorherigen Auftrag an");
        panel1.add(label25, new GridConstraints(8, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label26 = new JLabel();
        label26.setText("Zeigt den nächsten der aktuellen Aufträge an");
        panel1.add(label26, new GridConstraints(9, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label27 = new JLabel();
        label27.setText("Fokussiert den Lagerplatz über der aktuellen Position");
        panel1.add(label27, new GridConstraints(10, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label28 = new JLabel();
        label28.setText("Fokussiert den Lagerplatz rechts von der aktuellen Position");
        panel1.add(label28, new GridConstraints(11, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label29 = new JLabel();
        label29.setText("Fokussiert den Lagerplatz unter der aktuellen Position");
        panel1.add(label29, new GridConstraints(12, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label30 = new JLabel();
        label30.setText("Fokussiert den Lagerplatz links von der aktuellen Position");
        panel1.add(label30, new GridConstraints(13, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label31 = new JLabel();
        label31.setText("Leertaste");
        panel1.add(label31, new GridConstraints(15, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label32 = new JLabel();
        label32.setText("Auswahl bzw. Aktionstaste");
        panel1.add(label32, new GridConstraints(15, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label33 = new JLabel();
        label33.setText("Mögliche Auswahl zum Umlagern");
        panel1.add(label33, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectAvailableMoveElement = new JPanel();
        selectAvailableMoveElement.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(selectAvailableMoveElement, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label34 = new JLabel();
        label34.setText("Produkt");
        selectAvailableMoveElement.add(label34, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moveTargetElement = new JPanel();
        moveTargetElement.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(moveTargetElement, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label35 = new JLabel();
        label35.setText("Produkt");
        moveTargetElement.add(label35, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label36 = new JLabel();
        label36.setText("Auswahl eines neuen Lagerplatzes");
        panel1.add(label36, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label37 = new JLabel();
        label37.setText("z oder Z");
        panel1.add(label37, new GridConstraints(14, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label38 = new JLabel();
        label38.setText("Öffnet die Zielbeschreibung des Spiels");
        panel1.add(label38, new GridConstraints(14, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     *
    public JComponent $$$getRootComponent$$$() {
        return scrollPane1;
    }*/

}
