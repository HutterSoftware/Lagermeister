import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccountManager {
    private int account = 0;
    private OrderManager orderManager;
    private ArrayList<Order> transactionList;
    private Order moveOrder;
    private Order destroyOrder;

    public AccountManager(OrderManager orderManager) {
        this.moveOrder = new Order("Umlagerung", "","",Order.MOVING_ORDER_STRING,
                "-100");
        this.destroyOrder = new Order("Zerstörung", "", "", Order.DESTROY_ORDER_STRING,
                "-500");
        this.orderManager = orderManager;
        this.transactionList = new ArrayList<>();
    }

    public void accountOrder(Order order) {
        this.account += order.getCash();
        this.transactionList.add(order);
    }

    public void accountMoveOrder() {
        accountOrder(this.moveOrder);
    }

    public void accountDestroyOrder() {
        accountOrder(this.destroyOrder);
    }

    public Order getDestroyOrder() {
        return this.destroyOrder;
    }

    public Order getMoveOrder() {
        return this.moveOrder;
    }

    public int getAccount() {
        return this.account;
    }
}
