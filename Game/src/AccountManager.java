import java.util.ArrayList;

public class AccountManager {
    /**
     * Creating needed attributes
     */
    private int account = 0, turnover = 0, costs = 0, win = 0;
    private OrderManager orderManager;
    private ArrayList<Order> transactionList;
    private BalanceSheet balanceSheet;
    public static Order MOVE_ORDER;
    public static Order DESTROY_ORDER;

    /**
     * This constructor initialize all attributes and the transaction lost
     * @param orderManager
     */
    public AccountManager(OrderManager orderManager) {
        MOVE_ORDER = new Order("Umlagerung", "","",Order.MOVING_ORDER_STRING,
                "-100");
        DESTROY_ORDER = new Order("Zerstörung", "", "", Order.DESTROY_ORDER_STRING,
                "-500");
        this.orderManager = orderManager;
        this.transactionList = new ArrayList<>();
    }

    /**
     * This method reset all unreferenced attributes
     */
    public void reset() {
        this.account = 0;
        this.turnover = 0;
        this.costs = 0;
        this.win = 0;
        this.transactionList.clear();
    }

    /**
     * Setting balance sheet to object
     * @param balanceSheet BalanceSheet
     */
    public void setBalanceSheet(BalanceSheet balanceSheet) {
        this.balanceSheet = balanceSheet;
    }

    /**
     * This method will manage incoming transactions
     * @param order Order
     */
    public void accountOrder(Order order) {

        // Sum the new order
        this.account += order.getCash();
        this.turnover += Math.abs(order.getCash());

        if (order.getCash() < 0) {
            this.costs += Math.abs(order.getCash());
        } else {
            this.win += order.getCash();
        }

        // Adding to transaction list
        this.transactionList.add(order);

        this.balanceSheet.addNewBill(order);
    }

    /**
     * Retuns the account
     * @return int
     */
    public int getAccount() {
        return this.account;
    }

    /**
     * Returns the transaction list
     * @return ArrayList<Order>
     */
    public ArrayList<Order> getAllTransactions() {
        return this.transactionList;
    }

    /**
     * Returns the turnover
     * @return int
     */
    public int getTurnover() {
        return turnover;
    }

    /**
     * Returns the costs
     * @return int
     */
    public int getCosts() {
        return costs;
    }

    /**
     * Returns the win
     * @return int
     */
    public int getWin() {
        return win;
    }
}
