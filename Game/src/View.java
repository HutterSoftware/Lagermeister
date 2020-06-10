import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View extends JFrame{


    private JPanel panel1;
    private JButton destroyButton;
    private JButton moveStorageButton;
    private JButton newOrderButton;
    private JButton bilanzButton;
    private JButton orderLeftView;
    private JButton orderRightView;
    private JLabel cashLabel;
    private JPanel storage6;
    private JPanel storage7;
    private JPanel storage3;
    private JPanel storage4;
    private JPanel storage5;
    private JPanel storage0;
    private JPanel storage1;
    private JPanel storage2;
    private JPanel storage8;
    private JLabel product;
    private JLabel orderType;
    private JLabel money;

    private static int storage0Id = 0;
    private static int storage1Id = 1;
    private static int storage2Id = 2;
    private static int storage3Id = 3;
    private static int storage4Id = 4;
    private static int storage5Id = 5;
    private static int storage6Id = 6;
    private static int storage7Id = 7;
    private static int storage8Id = 8;

    private JPanel[] storagePanelCollection;

    public View(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        this.setVisible(true);
        newOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNewOrder();
                updateOrderView();
            }
        });
        orderRightView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Start.selectedOrder < Start.orderCounter) {
                    if (++Start.selectedOrder < Start.orderCounter) {
                        updateOrderView();
                    }
                }
                setEnableControlOfViewButtons();
            }
        });
        orderLeftView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Start.selectedOrder > 0) {
                    if (--Start.selectedOrder < Start.orderCounter) {
                        updateOrderView();
                    }
                }
                setEnableControlOfViewButtons();
            }
        });

        storage0.addMouseListener(new StorageHandler(storage0Id));
        storage1.addMouseListener(new StorageHandler(storage1Id));
        storage2.addMouseListener(new StorageHandler(storage2Id));
        storage3.addMouseListener(new StorageHandler(storage3Id));
        storage4.addMouseListener(new StorageHandler(storage4Id));
        storage5.addMouseListener(new StorageHandler(storage5Id));
        storage6.addMouseListener(new StorageHandler(storage6Id));
        storage7.addMouseListener(new StorageHandler(storage7Id));
        storage8.addMouseListener(new StorageHandler(storage8Id));

        this.storagePanelCollection = new JPanel[9];
        this.storagePanelCollection[0] = storage0;
        this.storagePanelCollection[1] = storage1;
        this.storagePanelCollection[2] = storage2;
        this.storagePanelCollection[3] = storage3;
        this.storagePanelCollection[4] = storage4;
        this.storagePanelCollection[5] = storage5;
        this.storagePanelCollection[6] = storage6;
        this.storagePanelCollection[7] = storage7;
        this.storagePanelCollection[8] = storage8;
    }

    private void storageAction(int storageId) {
        if (Start.activeOrderList.get(Start.selectedOrder).getOrderType() == Order.INCOMING_ORDER_STRING) {
            Start.storageHouse.storeOrder(storage6Id);
        } else {

        }
    }

    private void updateOrderView() {
        if (Start.activeOrderList.size() > 0) {
            Order order = Start.activeOrderList.get(Start.selectedOrder);
            this.product.setText(order.getProductName() + " " + order.getAttribute1() + " " + order.getAttribute2());
            this.orderType.setText(order.getOrderType());
            this.money.setText(Integer.toString(order.getCash()) + "€");

            if (order.getOrderType() == Order.OUTGOING_ORDER_STRING) {
                StorageHouse.SearchResult[] results = Start.storageHouse.findProduct(order);
                for (int i = 0; i < results.length; i++) {
                    JPanel storageField = this.storagePanelCollection[i];
                    if (results[i].isInStorage() && results[i].isOrderOnTop()) {
                        storageField.setBackground(Color.CYAN);
                    } else if (results[i].isInStorage() && !results[i].isOrderOnTop()) {
                        storageField.setBackground(Color.BLUE);
                    } else {
                        storageField.setBackground(Color.MAGENTA);
                    }
                }
            }
        } else {
            this.product.setText("");
            this.orderType.setText("");
            this.money.setText("0€");
        }
        setEnableControlOfViewButtons();
    }

    private void setEnableControlOfViewButtons() {
        if (Start.selectedOrder > 0 && Start.selectedOrder < Start.activeOrderList.size() - 1) {
            orderLeftView.setEnabled(true);
            orderRightView.setEnabled(true);
        } else if (Start.selectedOrder == Start.activeOrderList.size() - 1) {
            orderLeftView.setEnabled(true);
            orderRightView.setEnabled(false);
        } else {
            orderLeftView.setEnabled(false);
            orderRightView.setEnabled(true);
        }

    }

    private void loadNewOrder() {
        if (Start.orderCounter < 3) {
            Start.activeOrderList.add(Start.orderList[Start.orderIndex]);
            Start.orderCounter = ++Start.orderCounter;
            Start.orderIndex++;
            Start.selectedOrder = Start.activeOrderList.size() - 1;
            updateOrderView();
            setEnableControlOfViewButtons();
        } else {
            JOptionPane.showMessageDialog(null, Messages.CANNOT_GET_MORE_ORDER_MESSAGE);
        }
    }

    public void addMoney(int value) {
        String cashString = cashLabel.getText();
        Start.absoluteCash += value;
        int currentCash = Integer.parseInt(cashString.substring(0, cashString.length() - 1));
        this.cashLabel.setText(
                Integer.toString(
                currentCash + value) + "€");
        Start.activeOrderList.remove(Start.selectedOrder);
        if (Start.selectedOrder > 0) {
            Start.selectedOrder--;
            Start.orderCounter--;
        }

        updateOrderView();
        setEnableControlOfViewButtons();
    }
}
