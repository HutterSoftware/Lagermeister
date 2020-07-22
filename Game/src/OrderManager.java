import javax.swing.*;
import java.util.ArrayList;

public class OrderManager {

    // Attributes of class
    private Order[] allOrders;
    private ArrayList<Order> activeOrders;
    private int selectedOrderIndex = 0, activeOrderCounter = 0, allOrderIndex = 0;

    // Class constants
    public static Order NULL_DUMMY = new Order("", "","","","");
    public static int MAXIMUM_STORAGE_SIZE = 9;

    /**
     * Initialize attributes
     * @param allOrders Order[]
     */
    public OrderManager(Order[] allOrders) {
        this.allOrders = allOrders;
        this.activeOrders = new ArrayList<>();
    }

    /**
     * Resetting all attributes
     */
    public void reset() {
        this.activeOrders.clear();
        this.selectedOrderIndex = 0;
        this.activeOrderCounter = 0;
        allOrderIndex = 0;
    }

    /**
     * Removing specific order
     * @param order Order
     * @return boolean
     */
    public boolean removeOrder(Order order) {
        if (!this.activeOrders.isEmpty()) {
            this.activeOrders.remove(order);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Decreasing order count
     */
    public void decreaseOrder() {
        this.activeOrderCounter--;
        this.selectedOrderIndex = 0;
    }

    /**
     * Check index of selected order
     * @return boolean
     */
    public boolean isSelectedOrderFirst() {
        return this.selectedOrderIndex == 0;
    }

    /**
     * Check index of selected order
     * @return boolean
      */
    public boolean isSelectedOrderLast() {
        return this.selectedOrderIndex == 2;
    }

    /**
     * Returning current order
     * @return Order
     */
    public Order getCurrentOrder() {
        if (this.activeOrderCounter == 0) {
            return NULL_DUMMY;
        } else {
            if (this.activeOrders.size() > 0 && this.selectedOrderIndex < this.activeOrders.size()) {
                return this.activeOrders.get(this.selectedOrderIndex);
            } else {
                return NULL_DUMMY;
            }
        }
    }

    /**
     * Checking empty status
     * @return boolean
     */
    public boolean hasOrders() {
        return this.activeOrders.size() > 0;
    }

    /**
     * Loading new order
     */
    public void loadNewOrder() {
        if (this.activeOrders.size() < 3) {
            if (this.allOrderIndex >= this.allOrders.length) {
                this.allOrderIndex = 0;
            }

            this.activeOrders.add(this.allOrders[this.allOrderIndex]);
            this.activeOrderCounter++;
            this.allOrderIndex++;
            this.selectedOrderIndex = this.activeOrderCounter - 1;
        } else {
            JOptionPane.showMessageDialog(null, Messages.CANNOT_GET_MORE_ORDER_MESSAGE);
        }
    }

    /**
     * Showing next order
     * @return Order
     */
    public Order showNewOrder() {
        return this.allOrders[allOrderIndex % this.allOrders.length];
    }

    /**
     * Selecting next order
     */
    public void selectNextOrder() {
        if (this.selectedOrderIndex < 3) {
            this.selectedOrderIndex++;
        } else {
            this.selectedOrderIndex = 2;
        }
    }

    /**
     * Selecting previous order
     */
    public void selectPrevOrder() {
        if (this.selectedOrderIndex > 0) {
            this.selectedOrderIndex--;
        } else {
            this.selectedOrderIndex = 0;
        }
    }

    /**
     * Returning list of current orders
     * @return ArrayList<Order>
     */
    public ArrayList<Order> getActiveOrders() {
        return this.activeOrders;
    }

    /**
     * Getting current index
     * @return int
     */
    public int getCurrentOrderIndex() {
        return this.selectedOrderIndex;
    }

    /**
     * Getting count of active orders
     * @return int
     */
    public int getCountOfCurrentOrders() {
        return this.activeOrders.size();
    }

    /**
     * Increasing order index
     */
    public void increaseGlobalOrderIndex() {
        if (this.allOrderIndex < this.allOrders.length - 1) {
          this.allOrderIndex++;
        } else {
          this.allOrderIndex = 0;
        }
    }
}
