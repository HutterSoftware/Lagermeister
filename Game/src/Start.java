import java.util.LinkedList;

public class Start {
    public static Order[] orderList;
    public static StorageHouse storageHouse;
    public static int orderIndex = 0, orderCounter = 0, selectedOrder = 0;
    public static LinkedList<Order> activeOrderList;
    public static int absoluteCash = 0;
    public static View view;

    public static void main(String[] args) {
        // Create a list of orders to manage the game main task
        orderList = Order.createOrderListFromFile(args[0]);

        // Initialize activeOrderList
        activeOrderList = new LinkedList<>();

        // Initialize StorageHouse
        storageHouse = new StorageHouse();

        view = new View("Lagermeister");
    }
}
