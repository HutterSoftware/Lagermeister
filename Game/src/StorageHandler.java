import javax.swing.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.System.exit;

public class StorageHandler extends MouseAdapter {
    // Creating of required attributes
    private int storageId;
    private OrderManager orderManager;
    private AccountManager accountManager;
    private View view;
    private Object[] gameOverButtonCollection = new Object[]{"Neustarten", "Beenden"};

    // Creating constants
    public static String TIMBER_ATTRIBUTE_STRING = "Balken";
    public static String HEAVY_STONE_ATTRIBUTE_STRING = "Schwer";

    /**
     * This storage assign parameter to attributes
     * @param storageId
     * @param orderManager
     * @param accountManager
     * @param view
     */
    public StorageHandler(int storageId, OrderManager orderManager, AccountManager accountManager, View view) {
        this.storageId = storageId;
        this.orderManager = orderManager;
        this.accountManager = accountManager;
        this.view = view;
    }

    /**
     * This method handle the storage action
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Order currentOrder = this.orderManager.getCurrentOrder();

        if (this.view.isDestroyButtonPressed()) {
            destroyOrder(e);
        } else if (this.view.isMoveButtonToggled()) {
            moveTask();
        } else {
            storeAndDeliver(currentOrder);
        }

        gameOverCheck();
        this.view.printPanelString();
    }

    /**
     * This method manage the move procedur
     */
    private void moveTask() {
        // Checking enable status
        if (!Start.view.isMoveButtonToggled()){
            return;
        }

        // Checking ID
        if (Start.view.getSelectedMoveId() == -1) { // -1 means the source storage is not selected
            if (view.getStorageHouse().storageFields[this.storageId].getStorageSize() == 0) {
                JOptionPane.showMessageDialog(null, Messages.MOVE_SELECT_STORAGE_WITH_CONTENT);
                return;
            }
            // Setting the source id
            Start.view.setSelectedMoveId(this.storageId);

            // Mark available target storage
            Start.view.markAvailableMovingTargets();
        } else {
            // Movinng the element from source to target
            Start.storageHouse.moveElement(Start.view.getSelectedMoveId(), this.storageId);
            Start.view.resetSelectedMoveI();
            Start.view.disSelectMoveToggleButton();
            Start.view.visualizeStorage();
            Start.accountManager.accountOrder(AccountManager.MOVE_ORDER);
            if (Start.view.getBalanceSheet() != null) {
                Start.view.getBalanceSheet().addNewBill(AccountManager.MOVE_ORDER);
            }

            // Account the move procedure
            view.updateCash(AccountManager.MOVE_ORDER.getCash());
            this.view.updateAll();
        }
    }

    /**
     * This method manage the destroy procedure
     * @param e
     */
    private void destroyOrder(MouseEvent e) {
        // Getting source label
        JLabel storage = (JLabel) e.getSource();
        int containsSource = 0;

        // Loading of all storage labels
        JLabel[] list = this.view.getStoragePanels();

        // Searching after storage ID
        for (containsSource = 0; containsSource < list.length; containsSource++) {
            if (list[containsSource] == storage) {
                break;
            }
        }

        // Checking of destroy requirements
        if (Start.storageHouse.storageFields[containsSource].getStorageSize() == 0) {
            JOptionPane.showMessageDialog(null, Messages.CANNOT_DESTROY_ITS_EMPTY);
            return;
        }

        // Destroy top element
        Start.storageHouse.destroyOrder(containsSource);

        // Account destroy procedure
        if (view.getBalanceSheet() != null) {
            view.getBalanceSheet().addNewBill(AccountManager.DESTROY_ORDER);
        }
        Start.accountManager.accountOrder(AccountManager.DESTROY_ORDER);

        // Updating view elements
        view.updateCash(accountManager.getAccount());
        this.view.updateAll();
    }

    /**
     * This method check storage validation
     * @param order
     * @param storage
     * @return
     */
    private boolean getStorageValidation(Order order, Storage storage) {

        if (order != null) {
            // Checking Timber exception
            if (order.getAttribute2().equals(StorageHandler.TIMBER_ATTRIBUTE_STRING)) {
                return storage.getStorageSize() == 0;
            }

            if (storage.viewTopOrder() != null) {
                if (storage.viewTopOrder().getAttribute2().equals(StorageHandler.TIMBER_ATTRIBUTE_STRING)) {
                    JOptionPane.showMessageDialog(null, Messages.STORAGE_FIELD_FULL_MESSAGE);
                    return false;
                }
            }

            // Checking heavy stone exception
            if (order.getAttribute2().equals(StorageHandler.HEAVY_STONE_ATTRIBUTE_STRING)) {
                return storageId < 3;
            }
        }

        return true;
    }

    /**
     * This method manage the store and deliver procedure
     * @param order
     */
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

        // Change global state
        this.accountManager.accountOrder(order);
        this.orderManager.removeOrder(order);
        this.orderManager.decreaseOrder();

        // Update balance sheet
        if (Start.view.getBalanceSheet() != null) {
            Start.view.getBalanceSheet().addNewBill(order);
        }

        // Updating GUI
        this.view.updateAll();
    }

    /**
     * Checking game over requirement
     * @return
     */
    private boolean gameOverCheck() {

        //TODO: Writing game over requirement
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
