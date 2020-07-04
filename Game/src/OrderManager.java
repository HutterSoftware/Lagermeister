import javax.swing.*;
import java.util.ArrayList;

public class OrderManager {
    private Order[] allOrders;
    private ArrayList<Order> activeOrders;
    private int selectedOrderIndex = 0, activeOrderCounter = 0, allOrderIndex = 0;
    public static Order NULL_DUMMY = new Order("", "","","","");
    public static int MAXIMUM_STORAGE_SIZE = 9;

    public OrderManager(Order[] allOrders) {
        this.allOrders = allOrders;
        this.activeOrders = new ArrayList<>();
    }

    public void reset() {
        this.activeOrders.clear();
        this.selectedOrderIndex = 0;
        this.activeOrderCounter = 0;
        allOrderIndex = 0;
    }

    public boolean addOrder(Order order) {
        if (this.selectedOrderIndex < allOrders.length - 1) {
            this.activeOrders.add(order);
            this.selectedOrderIndex++;
            return true;
        } else {
            return false;
        }
    }

    public boolean removeOrder(Order order) {
        if (!this.activeOrders.isEmpty()) {
            this.activeOrders.remove(order);
            return true;
        } else {
            return false;
        }
    }

    public void decreaseOrder() {
        this.activeOrderCounter--;
        this.selectedOrderIndex = 0;
    }

    public Order getLastOrder() {
        return this.activeOrders.get(this.activeOrderCounter - 1);
    }

    public boolean isSelectedOrderFirst() {
        if (this.selectedOrderIndex == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSelectedOrderLast() {
        if (this.selectedOrderIndex == 2) {
            return true;
        } else {
            return false;
        }
    }

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

    public boolean hasOrders() {
        if (this.activeOrders.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

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

    public void selectNextOrder() {
        if (this.selectedOrderIndex < 3) {
            this.selectedOrderIndex++;
        } else {
            this.selectedOrderIndex = 2;
        }
    }

    public void selectPrevOrder() {
        if (this.selectedOrderIndex > 0) {
            this.selectedOrderIndex--;
        } else {
            this.selectedOrderIndex = 0;
        }
    }

    public boolean onlyStoreOrders() {
        int counter = 0;
        for (Order order : this.activeOrders) {
            if (order.getOrderType() == Order.INCOMING_ORDER_STRING) {
                counter++;
            }
        }
        if (counter == this.activeOrders.size()) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Order> getActiveOrders() {
        return this.activeOrders;
    }
}
