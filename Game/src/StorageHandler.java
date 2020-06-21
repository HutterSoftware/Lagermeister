import javax.swing.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.System.exit;

public class StorageHandler extends MouseAdapter {
    private int storageId;
    private OrderManager orderManager;
    private AccountManager accountManager;
    private View view;
    private Object[] gameOverButtonCollection = new Object[]{"Neustarten", "Beenden"};
    private Order destroyOrder;

    public StorageHandler(int storageId, OrderManager orderManager, AccountManager accountManager, View view) {
        this.storageId = storageId;
        this.orderManager = orderManager;
        this.accountManager = accountManager;
        this.view = view;
        this.destroyOrder = new Order("Zerstörung", "", "",
                Order.DESTROY_ORDER_STRING, "500");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Order currentOrder = this.orderManager.getCurrentOrder();

        if (this.view.isDestroyButtonPressed()) {
            destroyOrder(e);
        } else {
            storeAndDeliver(currentOrder);

        }

        gameOverCheck();

    }

    private void destroyOrder(MouseEvent e) {
        JPanel storage = (JPanel) e.getSource();
        int containsSource = 0;
        JPanel[] list = this.view.getStoragePanels();
        for (containsSource = 0; containsSource < list.length; containsSource++) {
            if (list[containsSource] == storage) {
                break;
            }
        }

        Start.storageHouse.destroyOrder(
                containsSource);

        if (view.getBalanceSheet() != null) {
            view.getBalanceSheet().addNewBill(destroyOrder);
        }
        this.view.updateAll();
    }

    private boolean getStorageValidation(Order order, Storage storage) {

        if (order != null) {
            if (order.getAttribute2().equals(OrderExceptionCheck.TIMBER_ATTRIBUTE_STRING)) {
                return storage.getStorageSize() == 0;
            }

            if (order.getAttribute2().equals(OrderExceptionCheck.HEAVY_STONE_ATTRIBUTE_STRING)) {
                return storageId < 3;
            }
        }

        return true;
    }

    private void storeAndDeliver(Order order) {
        if (order == OrderManager.NULL_DUMMY) {
            return;
        }

        boolean successful = false;
        if (order.getOrderType() == Order.INCOMING_ORDER_STRING) {
            Storage storage = Start.storageHouse.storageFields[storageId];
            if (getStorageValidation(order, storage)) {
                successful = Start.storageHouse.storeOrder(storageId, order);
            }
        } else {
            successful = Start.storageHouse.deliverOrder(storageId, order);
        }

        if (!successful) {
            return;
        }
        this.accountManager.accountOrder(order);
        this.orderManager.removeOrder(order);
        this.orderManager.decreaseOrder();

        if (Start.view.getBalanceSheet() != null) {
            Start.view.getBalanceSheet().addNewBill(order);
        }

        this.view.updateAll();
    }

    private boolean gameOverCheck() {
        int fullStatusCounter = 0;
        for (Color status : this.view.getStorageHouse().getStorageStatus()) {
            if (status == Color.red) {
                fullStatusCounter++;
            }
        }

        int orderFailCounter = 0;
        for (Order order : Start.orderManager.getActiveOrders()) {
            orderFailCounter += Start.storageHouse.findProduct(order).size();
        }
        // orderFailcounter == 0 means cannot deliver order

        if (this.orderManager.onlyStoreOrders() && fullStatusCounter == OrderManager.MAXIMUM_STORAGE_SIZE &&
                this.orderManager.hasOrders() && orderFailCounter == 0) {

            int answer = JOptionPane.showOptionDialog(null,
                    Messages.GAME_OVER_MESSAGE,
                    "GAME OVER",
                    JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                    null,
                    this.gameOverButtonCollection,
                    this.gameOverButtonCollection[0]);
            switch (answer) {

                case 0: // Restart game
                    Start.resetGame();
                    break;

                case 1: // Exit game
                    exit(0);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, Messages.UNDEFINED_ANSWER);
            }
        }
        return true;
    }
}
