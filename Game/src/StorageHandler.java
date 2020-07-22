import javax.swing.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.System.exit;

public class StorageHandler extends MouseAdapter {
    // Constants of StorageHandler
    public static int MAX_SIZE_OF_CURRENT_ORDERS = 3;

    // Required attributes
    private final int storageId;
    private final OrderManager orderManager;
    private final AccountManager accountManager;
    private final View view;
    private final Object[] gameOverButtonCollection = new Object[]{"Neustarten", "Beenden"};

    // Creating constants
    public static String TIMBER_ATTRIBUTE_STRING = "Balken";
    public static String HEAVY_STONE_ATTRIBUTE_STRING = "Schwer";

    /**
     * This storage assign parameter to attributes
     * @param storageId storage ID
     * @param orderManager OrderManager
     * @param accountManager AccountManager
     * @param view View
     */
    public StorageHandler(int storageId, OrderManager orderManager, AccountManager accountManager, View view) {
        this.storageId = storageId;
        this.orderManager = orderManager;
        this.accountManager = accountManager;
        this.view = view;
    }

    /**
     * This method handle the storage action
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Order currentOrder = this.orderManager.getCurrentOrder();

        if (this.view.isDestroyButtonPressed()) {
            destroyOrder();
        } else if (this.view.isMoveButtonToggled()) {
            moveTask();
        } else {
            storeAndDeliver(currentOrder);
        }

        startGameOverProcedure();

        this.view.printPanelString();
        this.view.getStoragePanels()[this.storageId].requestFocus();
        this.view.getShortCutFun().setKeySelectedStorage(this.storageId);
        this.view.visualizeStorage();
    }

    /**
     * This method manage the move procedure
     */
    public void moveTask() {
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

            Start.view.setTodoLabelText(Messages.SELECT_STORAGE_OBJECT_TO_MOVE);
        } else {
            if (view.getStorageHouse().storageFields[view.getSelectedMoveId()] != null) {
                if (view.getStorageHouse().storageFields[view.getSelectedMoveId()]
                        .viewTopOrder().getAttribute2().equals(StorageHandler.HEAVY_STONE_ATTRIBUTE_STRING) &&
                        this.storageId >= 3) {
                    view.visualizeStorage();
                    JOptionPane.showMessageDialog(null, Start.toUtf8(Messages.STORE_HEAVY_STONES_ON_THE_FLOOR));
                    return;
                }
            }

            if (view.getStorageHouse().storageFields[view.getSelectedMoveId()]
                .viewTopOrder().getAttribute2().equals(StorageHandler.TIMBER_ATTRIBUTE_STRING) &&
                view.getStorageHouse().storageFields[this.storageId].getStorageSize() > 0) {

                view.visualizeStorage();
                JOptionPane.showMessageDialog(null, Start.toUtf8(Messages.TIMBER_NEED_COMPLETE_A_FIELD));
                return;
            }

            if (!getStorageValidation(view.getStorageHouse().storageFields[this.storageId].viewTopOrder(),
                    view.getStorageHouse().storageFields[this.storageId])) {
                return;
            }
            // Moving the element from source to target
            Start.storageHouse.moveElement(Start.view.getSelectedMoveId(), this.storageId);
            Start.view.resetSelectedMoveId();
            Start.view.disSelectMoveToggleButton();
            Start.view.visualizeStorage();
            Start.accountManager.accountOrder(AccountManager.MOVE_ORDER);

            // Account the move procedure
            Start.view.updateAll();
            Start.view.setTodoLabelText(Messages.SELECT_STORAGE_TARGET_OF_MOVE);
        }
    }

    /**
     * This method manage the destroy procedure
     */
    private void destroyOrder() {
        // Getting source label
        JLabel storage = view.getStoragePanels()[this.storageId];
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
            JOptionPane.showMessageDialog(null, Start.toUtf8(Messages.CANNOT_DESTROY_ITS_EMPTY));
            return;
        }

        // Destroy top element
        Start.storageHouse.destroyOrder(containsSource);

        // Account destroy procedure
        Start.accountManager.accountOrder(AccountManager.DESTROY_ORDER);

        // Updating view elements
        this.view.updateAll();
        this.view.unSelectMoveButton();
    }

    /**
     * This method check storage validation
     * @param order Order
     * @param storage Storage
     * @return Validation result
     */
    private boolean getStorageValidation(Order order, Storage storage) {

        if (order != null) {
            // Checking Timber exception
            if (order.getAttribute2().equals(StorageHandler.TIMBER_ATTRIBUTE_STRING)) {
                if (storage.getStorageSize() == 0) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, Start.toUtf8(Messages.TIMBER_NEED_COMPLETE_A_FIELD));
                    return false;
                }
            }

            if (storage.viewTopOrder() != null) {
                if (storage.viewTopOrder().getAttribute2().equals(StorageHandler.TIMBER_ATTRIBUTE_STRING)) {
                    JOptionPane.showMessageDialog(null, Start.toUtf8(Messages.STORAGE_FIELD_FULL_MESSAGE));
                    return false;
                }
            }

            // Checking heavy stone exception
            if (order.getAttribute2().equals(StorageHandler.HEAVY_STONE_ATTRIBUTE_STRING)) {
                if (storageId < 3) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, Start.toUtf8(Messages.STORE_HEAVY_STONES_ON_THE_FLOOR));
                    return false;
                }
            }

            if (view.getSelectedMoveId() != -1 && this.storageId >= 3 &&
                    order.getAttribute2().equals(StorageHandler.HEAVY_STONE_ATTRIBUTE_STRING)) {

                JOptionPane.showMessageDialog(null, Start.toUtf8(Messages.SELECT_ONE_OF_THE_DEEPEST_FIELDS));
                return false;
            }
        }
        return true;
    }

    /**
     * This method manage the store and deliver procedure
     * @param order Order
     */
    private void storeAndDeliver(Order order) {
        if (order == OrderManager.NULL_DUMMY) {
            return;
        }

        boolean successful = false;
        if (Objects.equals(order.getOrderType(), Order.INCOMING_ORDER_STRING)) {
            Storage storage = Start.storageHouse.storageFields[storageId];
            if (getStorageValidation(order, storage)) {
                successful = Start.storageHouse.storeOrder(storageId, order);
            }
        } else {
            if (Start.storageHouse.deliverOrder(storageId, order) == StorageHouse.IS_AT_TOP) {
                successful = true;
            }
        }

        if (!successful) {
            if (Start.storageHouse.deliverOrder(storageId, order) == StorageHouse.STORAGE_CONTAINS_ORDER) {
                JOptionPane.showMessageDialog(null, Messages.OBJECT_IS_COVERED);
            }
            return;
        }

        // Change global state
        this.accountManager.accountOrder(order);
        this.orderManager.removeOrder(order);
        this.orderManager.decreaseOrder();

        // Updating GUI
        this.view.updateAll();
    }

    /**
     * Checking game over requirement
     * @return game over status
     */
    private static boolean gameOverCheck() {

        // Checking for maximum open order
        if (Start.orderManager.getActiveOrders().size() < StorageHandler.MAX_SIZE_OF_CURRENT_ORDERS) {
            return false;
        }

        // Checking status of each order with StorageHouse
        int orderGameOverCounter = 0;
        ArrayList<Order> orderList = Start.orderManager.getActiveOrders();
        if (!orderList.get(0).getOrderType().equals(Order.OUTGOING_ORDER_STRING) ||
            !orderList.get(1).getOrderType().equals(Order.OUTGOING_ORDER_STRING) ||
            !orderList.get(2).getOrderType().equals(Order.OUTGOING_ORDER_STRING)) {

        }

        for (Order order : orderList) {
            if (order.getOrderType().equals(Order.INCOMING_ORDER_STRING)) {
                for (Color color : Start.storageHouse.getStorageStatus()) {
                    if (color == StorageHouse.FIELD_THREE) {
                        orderGameOverCounter++;
                    }
                }
            } else {
                for (StorageHouse.SearchResult result : Start.storageHouse.findProduct(order)) {
                    if (result.getStatus() == StorageHouse.FIELD_HAS_NOT_THE_REQUIRED_ORDER) {
                        orderGameOverCounter++;
                    }
                }
            }
        }

        // Checking counter value
        return orderGameOverCounter == 27;
    }

    /**
     * Wrapper method of gameOverCheck
     */
    public static void startGameOverProcedure() {
        if (gameOverCheck()) {
            int answer = JOptionPane.showConfirmDialog(null,
                    "Game over! Neues Spiel mit \"Ja\". Spiel beenden mit \"Nein\"","Game Over",
                    JOptionPane.YES_NO_OPTION);

            if (answer == 1) { // Yes
                System.exit(0);
            } else { // No
                Start.resetGame();
            }
        }
    }
}
