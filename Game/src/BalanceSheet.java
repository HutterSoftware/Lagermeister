import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class BalanceSheet extends JFrame {

    // Creating attributes
    private JTable balanceTable;
    private JPanel panel1;
    private JLabel turnoverLabel;
    private JLabel costLabel;
    private JLabel profitLabel;
    private int positionCounter = 0;
    final private String[] columnNames = new String[]{"Position", "Auftragstyp", "Beschreibung", "Betrag"};

    /**
     * Initializing of attributes
     */
    public BalanceSheet() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setTitle("Bilanz");
        initializeGui();

        DefaultTableModel model = getDefaultModel();
        for (String name : columnNames) {
            model.addColumn(name);
        }
    }

    /**
     * Initialize components and add them to JFrame object
     */
    private void initializeGui() {
        panel1 = new JPanel(new GridBagLayout());

        JPanel conclusionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints conclusionConstraints = new GridBagConstraints();
        conclusionConstraints.gridy = 0;
        conclusionConstraints.gridx = 0;
        conclusionConstraints.insets = new Insets(10,10,10,10);

        JLabel turnoverLabelText = new JLabel("Umsatz:");
        conclusionPanel.add(turnoverLabelText,conclusionConstraints);

        turnoverLabel = new JLabel();
        conclusionConstraints.gridx = 1;
        conclusionPanel.add(turnoverLabel, conclusionConstraints);

        profitLabel = new JLabel();
        conclusionConstraints.gridx = 3;
        conclusionPanel.add(profitLabel, conclusionConstraints);

        JLabel costsLabelText = new JLabel("Kosten:");
        conclusionConstraints.gridy = 1;
        conclusionConstraints.gridx = 0;
        conclusionPanel.add(costsLabelText, conclusionConstraints);

        costLabel = new JLabel();
        conclusionConstraints.gridx = 1;
        conclusionPanel.add(costLabel, conclusionConstraints);

        conclusionConstraints.insets = new Insets(10,30,10,10);
        JLabel winLabel = new JLabel("Gewinn:");
        conclusionConstraints.gridx = 2;
        conclusionConstraints.gridy = 0;
        conclusionPanel.add(winLabel, conclusionConstraints);

        JScrollPane tableScrollPane = new JScrollPane();
        this.balanceTable = new JTable();
        tableScrollPane.setViewportView(balanceTable);

        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.BOTH;
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 0;
        panel1.add(conclusionPanel, panelConstraints);

        panelConstraints.gridy = 1;
        panel1.add(tableScrollPane, panelConstraints);

        this.setSize(500,600);
        this.setContentPane(panel1);
    }

    /**
     * Getting DefaultTableModel
     *
     * @return DefaultTableModel
     */
    private DefaultTableModel getDefaultModel() {
        return (DefaultTableModel) balanceTable.getModel();
    }

    /**
     * Resetting all object attributes
     */
    public void reset() {
        turnoverLabel.setText("0€");
        costLabel.setText("0€");
        profitLabel.setText("0€");
        positionCounter = 0;

        // Deleting all rows
        DefaultTableModel table = getDefaultModel();
        while (table.getRowCount() > 0) {
            table.removeRow(0);
        }
    }

    /**
     * Adding new order to balance sheet
     *
     * @param order Order
     */
    public void addNewBill(Order order) {
        updateConclusion();

        DefaultTableModel model = getDefaultModel();
        Object[] orderValues = order.toArray();
        orderValues[0] = ++this.positionCounter;
        if (orderValues[1].equals("Einlagerung") || orderValues[1].equals("Auslagerung")) {
            orderValues[1] += "auftrag";
        }
        model.addRow(orderValues);
    }

    /**
     * Updating all labels
     */
    private void updateConclusion() {
        this.costLabel.setText(Integer.toString(Start.accountManager.getCosts()));
        this.profitLabel.setText(Integer.toString(Start.accountManager.getWin()));
        this.turnoverLabel.setText(Integer.toString(Start.accountManager.getTurnover()));
    }

    /**
     * Showing of balance sheet
     */
    public void showBalanceSheet() {
        this.setVisible(true);
    }

    /**
     * Updating table
     *
     * @param transactionList ArrayList<Order>
     */
    public void updateTable(ArrayList<Order> transactionList) {
        for (Order order : transactionList) {
            addNewBill(order);
        }
    }


}
