import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class BalanceSheet extends JFrame {

    private JTable balanceTable;
    private JPanel panel1;
    private JLabel turnoverLabel;
    private JLabel costLabel;
    private JLabel profitLabel;
    private int positionCounter = 0;
    final private String[] columnNames = new String[]{"Position", "Auftragstyp", "Beschreibung", "Betrag"};

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

    private DefaultTableModel getDefaultModel() {
        return (DefaultTableModel)balanceTable.getModel();
    }


    public void addNewBill(Order order) {
        DefaultTableModel model = getDefaultModel();
        Object[] orderValues = order.toArray();
        orderValues[0] = ++this.positionCounter;
        model.addRow(orderValues);

        updateConclusion(order);
    }

    private void updateConclusion(Order order) {
        this.costLabel.setText(Integer.toString(Start.accountManager.getCosts()));
        this.profitLabel.setText(Integer.toString(Start.accountManager.getWin()));
        this.turnoverLabel.setText(Integer.toString(Start.accountManager.getTurnover()));
    }

    public void showBalanceSheet() {
        this.setVisible(true);
    }

    private void updateCostLabel(int newCosts) {
        int currentCost = Integer.parseInt(this.costLabel.getText());
        this.costLabel.setText(Integer.toString(currentCost + Math.abs(newCosts)));
    }

    public void updateTable(ArrayList<Order> transactionList) {
        DefaultTableModel table = getDefaultModel();

        for (Order order : transactionList) {
            addNewBill(order);
        }
    }
}
