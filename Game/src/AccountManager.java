import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccountManager {
    private int account = 0;
    private OrderManager orderManager;
    private ArrayList<Order> transactionList;

    public AccountManager(OrderManager orderManager) {
        this.orderManager = orderManager;
        this.transactionList = new ArrayList<>();
    }

    public void accountOrder(Order order) {
        this.account += order.getCash();
        this.transactionList.add(order);
    }

    public int getAccount() {
        return this.account;
    }
}
