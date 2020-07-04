import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
        this.setContentPane(panel1);
        this.pack();

        DefaultTableModel model = getDefaultModel();
        for (String name : columnNames) {
            model.addColumn(name);
        }
    }

    /**
     * Getting DefaultTableModel
     * @return DefaultTableModel
     */
    private DefaultTableModel getDefaultModel() {
        return (DefaultTableModel)balanceTable.getModel();
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
     * @param order Order
     */
    public void addNewBill(Order order) {
        updateConclusion(order);

        DefaultTableModel model = getDefaultModel();
        Object[] orderValues = order.toArray();
        orderValues[0] = ++this.positionCounter;
        model.addRow(orderValues);
    }

    /**
     * Updating all labels
     * @param order Order
     */
    private void updateConclusion(Order order) {
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
     * @param transactionList ArrayList<Order>
     */
    public void updateTable(ArrayList<Order> transactionList) {
        for (Order order : transactionList) {
            addNewBill(order);
        }
    }
}
