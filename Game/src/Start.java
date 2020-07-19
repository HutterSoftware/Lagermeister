import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

public class Start {
    public static OrderManager orderManager;
    public static StorageHouse storageHouse;
    public static AccountManager accountManager;
    public static View view;

    /**
     * This method starts the game
     * @param args One parameter is needed. This parameters contains a path to CSV file.
     */
    public static void main(String[] args) {
        // Create a list of orders to manage the game main task
        Order[]orderList = Order.createOrderListFromFile(args[0]);

        // Initialize orderManager
        orderManager = new OrderManager(orderList);

        // Initialize StorageHouse
        storageHouse = new StorageHouse();

        // Initialize accountManager
        accountManager = new AccountManager(orderManager);

        view = new View(toUtf8("Lagermeister"), orderManager, accountManager, storageHouse);

    }

    /**
     * Resetting all game objects to start a new game
     */
    public static void resetGame() {
        Start.orderManager.reset();
        Start.accountManager.reset();
        Start.storageHouse.reset();
        Start.view.reset();
    }

    public static String toUtf8 (String string) {
        try {
            return new String(string.getBytes("8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
