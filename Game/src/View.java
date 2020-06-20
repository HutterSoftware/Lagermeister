import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class View extends JFrame{


    private JPanel panel1;
    private JToggleButton destroyButton;
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
    private JLabel todoLabel;

    private static int storage0Id = 0;
    private static int storage1Id = 1;
    private static int storage2Id = 2;
    private static int storage3Id = 3;
    private static int storage4Id = 4;
    private static int storage5Id = 5;
    private static int storage6Id = 6;
    private static int storage7Id = 7;
    private static int storage8Id = 8;

    public static int PREVIOUS_ORDER = -1;
    public static int CURRENT_ORDER = 0;
    public static int NEXT_ORDER = 1;

    private JPanel[] storagePanelCollection;
    private OrderManager orderManager;
    private AccountManager accountManager;
    private StorageHouse storageHouse;
    private BalanceSheet balanceSheet;

    public View(String title, OrderManager orderManager, AccountManager accountManager, StorageHouse storageHouse) {

        this.orderManager = orderManager;
        this.accountManager = accountManager;
        this.storageHouse = storageHouse;

        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        this.setVisible(true);
        newOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderManager.loadNewOrder();
                updateAll();
                visualizeStorage();
            }
        });
        orderRightView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderViewButtonAction(View.NEXT_ORDER);
            }
        });
        orderLeftView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderViewButtonAction(View.PREVIOUS_ORDER);
            }
        });
        destroyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (destroyButton.isSelected()) {
                    destroyButton.setText("Nicht Zerstören");
                } else {
                    destroyButton.setText("Zerstören");
                }
            }
        });

        storage0.addMouseListener(new StorageHandler(storage0Id, this.orderManager, this.accountManager,this));
        storage1.addMouseListener(new StorageHandler(storage1Id, this.orderManager, this.accountManager,this));
        storage2.addMouseListener(new StorageHandler(storage2Id, this.orderManager, this.accountManager,this));
        storage3.addMouseListener(new StorageHandler(storage3Id, this.orderManager, this.accountManager,this));
        storage4.addMouseListener(new StorageHandler(storage4Id, this.orderManager, this.accountManager,this));
        storage5.addMouseListener(new StorageHandler(storage5Id, this.orderManager, this.accountManager,this));
        storage6.addMouseListener(new StorageHandler(storage6Id, this.orderManager, this.accountManager,this));
        storage7.addMouseListener(new StorageHandler(storage7Id, this.orderManager, this.accountManager,this));
        storage8.addMouseListener(new StorageHandler(storage8Id, this.orderManager, this.accountManager,this));

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
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateAll();
            }
        });
        bilanzButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (balanceSheet == null) {
                    balanceSheet = new BalanceSheet();
                }
                balanceSheet.showBalanceSheet();
            }
        });
    }

    public void updateAll() {
        setEnableControlOfViewButtons();
        updateCash(accountManager.getAccount());
        orderViewButtonAction(View.CURRENT_ORDER);
        visualizeStorage();
    }

    public void orderViewButtonAction(int prev) {

        if (prev == View.PREVIOUS_ORDER) {
            orderManager.selectPrevOrder();
        } else if (prev == View.NEXT_ORDER){
            orderManager.selectNextOrder();
        }
        setEnableControlOfViewButtons();visualizeStorage();
        updateOrderViewItems();
        visualizeStorage();
    }

    public void visualizeStorage() {
        Order order = this.orderManager.getCurrentOrder();
        Color[] storageStatus = this.storageHouse.getStorageStatus();
        for (int i = 0; i < storageStatus.length; i++) {
            storagePanelCollection[i].setBackground(storageStatus[i]);
        }

        if (order.getOrderType() == Order.OUTGOING_ORDER_STRING) {
            int resultCounter = 0;
            ArrayList<Integer> results = Start.storageHouse.findProduct(order);
            for (int i = 0; i < this.storagePanelCollection.length; i++) {
                if (resultCounter < results.size()) {
                    if (results.get(resultCounter) == i) {
                        storagePanelCollection[i].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                        resultCounter++;
                    } else {
                        storagePanelCollection[i].setBorder(BorderFactory.createLineBorder(Color.RED));
                    }
                } else {
                    storagePanelCollection[i].setBorder(BorderFactory.createEmptyBorder());
                }
            }
        } else {
            for (JPanel panel : storagePanelCollection) {
                panel.setBorder(null);
            }
        }
    }

    public void updateCash(int value) {
        this.cashLabel.setText(Integer.toString(value));
    }

    private void storageAction(int storageId) {
        if (orderManager.getCurrentOrder().getOrderType() == Order.INCOMING_ORDER_STRING) {
            this.storageHouse.storeOrder(storageId, this.orderManager.getCurrentOrder());
        } else {
            this.storageHouse.deliverOrder(storageId, this.orderManager.getCurrentOrder());
        }
    }

    private void updateOrderViewItems() {
        if (this.orderManager.hasOrders()) {
            Order order = orderManager.getCurrentOrder();

            this.product.setText(order.getProductName() + " " + order.getAttribute1() + " " + order.getAttribute2());
            this.orderType.setText(order.getOrderType());
            this.money.setText(Integer.toString(order.getCash()) + "€");

            if (order.getOrderType() == Order.INCOMING_ORDER_STRING) {
                this.todoLabel.setText(Messages.SELECT_STORAGE_TO_STORE);
            } else {
                this.todoLabel.setText(Messages.SELECT_STORAGE_TO_DELIVER);
            }


        } else {
            this.product.setText("");
            this.orderType.setText("");
            this.money.setText("0€");
        }
        setEnableControlOfViewButtons();
    }

    private void setEnableControlOfViewButtons() {
        if (this.orderManager.isSelectedOrderFirst()) {
            orderLeftView.setEnabled(false);
            orderRightView.setEnabled(true);
        } else if (this.orderManager.isSelectedOrderLast()) {
            orderLeftView.setEnabled(true);
            orderRightView.setEnabled(false);
        } else {
            orderLeftView.setEnabled(true);
            orderRightView.setEnabled(true);
        }
    }

    public StorageHouse getStorageHouse() {
        return this.storageHouse;
    }

    public BalanceSheet getBalanceSheet() {
        return this.balanceSheet;
    }

    public boolean isDestroyButtonPressed() {
        return this.destroyButton.isSelected();
    }

    public JPanel[] getStoragePanels() {
        return this.storagePanelCollection;
    }
}
