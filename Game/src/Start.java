public class Start {
    public static Order[] orderList;
    public int orderIndex = 0;

    public static void main(String[] args) {

        // Create a list of orders to manage the game main task
        orderList = Order.createOrderListFromFile(args[0]);


    }
}
