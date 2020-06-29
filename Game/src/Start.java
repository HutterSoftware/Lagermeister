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

        // Initialize accountManager
        accountManager = new AccountManager(orderManager);

        // Initialize StorageHouse
        storageHouse = new StorageHouse();

        view = new View("Lagermeister", orderManager, accountManager, storageHouse);
    }

    public static void resetGame() {

    }
}
