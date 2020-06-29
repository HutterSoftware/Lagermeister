import java.util.ArrayList;

public class AccountManager {
    private int account = 0, turnover = 0, costs = 0, win = 0;
    private OrderManager orderManager;
    private ArrayList<Order> transactionList;
    public static Order MOVE_ORDER;
    public static Order DESTROY_ORDER;

    public AccountManager(OrderManager orderManager) {
        MOVE_ORDER = new Order("Umlagerung", "","",Order.MOVING_ORDER_STRING,
                "-100");
        DESTROY_ORDER = new Order("Zerstörung", "", "", Order.DESTROY_ORDER_STRING,
                "-500");
        this.orderManager = orderManager;
        this.transactionList = new ArrayList<>();
    }

    public void accountOrder(Order order) {
        this.account += order.getCash();
        this.turnover += Math.abs(order.getCash());
        if (order.getCash() < 0) {
            this.costs += Math.abs(order.getCash());
            this.win = this.turnover - this.costs;
            this.turnover += Math.abs(order.getCash());
        }

        this.transactionList.add(order);
    }

    public int getAccount() {
        return this.account;
    }

    public ArrayList<Order> getAllTransactions() {
        return this.transactionList;
    }

    public int getTurnover() {
        return turnover;
    }

    public int getCosts() {
        return costs;
    }

    public int getWin() {
        return win;
    }
}
