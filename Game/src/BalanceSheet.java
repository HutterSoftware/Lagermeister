import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class BalanceSheet extends JFrame {

    private JTable balanceTable;
    private JPanel panel1;
    private JLabel turnoverLabel;
    private JLabel costLabel;
    private JLabel profitLabel;
    private int positionCounter = 0;
    private String[] columnNames = new String[]{"Position", "Auftragstyp", "Beschreibung", "Betrag"};

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
        if (order.getOrderType().equals(Order.INCOMING_ORDER_STRING) ||
                order.getOrderType().equals(Order.OUTGOING_ORDER_STRING)) {

            this.profitLabel.setText(
                    Integer.toString(Integer.parseInt(this.turnoverLabel.getText()) + order.getCash()));
        } else if (order.getOrderType().equals(Order.MOVING_ORDER_STRING) ||
                order.getOrderType().equals(Order.DESTROY_ORDER_STRING)) {

            this.costLabel.setText(
                    Integer.toString(Integer.parseInt(this.turnoverLabel.getText()) + order.getCash()));
        }

        this.turnoverLabel.setText(
                Integer.toString(Integer.parseInt(this.turnoverLabel.getText()) + order.getCash()));
    }

    public void showBalanceSheet() {
        this.setVisible(true);
    }
}
