import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.System.exit;

public class StorageHandler extends MouseAdapter {
    private int storageId;
    private OrderManager orderManager;
    private AccountManager accountManager;
    private View view;
    private Object[] gameOverButtonCollection = new Object[]{"Neustarten", "Beenden"};

    public StorageHandler(int storageId, OrderManager orderManager, AccountManager accountManager, View view) {
        this.storageId = storageId;
        this.orderManager = orderManager;
        this.accountManager = accountManager;
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        gameOverCheck();

        Order currentOrder = this.orderManager.getCurrentOrder();
        if (currentOrder == OrderManager.NULL_DUMMY) {
            return;
        }

        boolean successful = false;
        if (currentOrder.getOrderType() == Order.INCOMING_ORDER_STRING) {
            successful = Start.storageHouse.storeOrder(storageId, currentOrder);
        } else {
            //successful = Start.storageHouse.deliverOrder(storageId, currentOrder);
        }

        if (successful) {
            this.accountManager.accountOrder(currentOrder);
            this.orderManager.removeOrder(currentOrder);
            this.orderManager.decreaseOrder();
            this.view.updateCash(this.accountManager.getAccount());
            this.view.orderViewButtonAction(View.CURRENT_ORDER);
            this.view.visualizeStorage();
        }
    }

    private boolean gameOverCheck() {
        int fullStatusCounter = 0;
        for (Color status : this.view.getStorageHouse().getStorageStatus()) {
            if (status == Color.red) {
                fullStatusCounter++;
            }
        }

        if (this.orderManager.onlyStoreOrders() && fullStatusCounter == OrderManager.MAXIUMUM_STORAGE_SIZE) {
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
