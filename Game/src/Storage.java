import javax.swing.*;
import java.util.Stack;

public class Storage {

    // Class attributes
    private Stack storage;

    /**
     * Initialize object
     */
    public Storage() {
        this.storage = new Stack();
    }

    /**
     * Resetting storage
     */
    public void reset() {
        while (!storage.empty()) {
            storage.pop();
        }
    }

    /**
     * Getting the top of stack
     * @return Order
     */
    public Order viewTopOrder() {
        if (this.storage.empty()) {
            return null;
        } else {
            return (Order) this.storage.peek();
        }
    }

    /**
     * Adding new Order to stack
     * @param order Order
     * @return boolean
     */
    public boolean addOrder(Order order) {
        if (this.storage.size() == 3) {
            return false;
        } else {
            this.storage.addElement(order);
            return true;
        }
    }

    /**
     * Removing top element of stack
     * @return boolean
     */
    public boolean removeTopElement() {
        if (this.storage.empty()) {
            return false;
        }

        this.storage.pop();
        return true;
    }

    /**
     * Equalizing two Order objects
     * @param order Order
     * @return boolean
     */
    private boolean equal(Order order) {
        for (int i = 0; i < this.storage.size(); i++) {
            Order checkObject = (Order)this.storage.elementAt(i);
            if (checkObject.getProductName().equals(order.getProductName()) &&
                checkObject.getAttribute1().equals(order.getAttribute1()) &&
                checkObject.getAttribute2().equals(order.getAttribute2())) {

                return true;
            }
        }
        return false;
    }

    /**
     * Searching objects in stack
     * @param order Order
     * @return boolean
     */
    public boolean containsProduct(Order order) {
        return equal(order);
    }

    /**
     * Checking top element with given Order object
     * @param order Order
     * @return boolean
     */
    public boolean isOrderAtTop(Order order) {
        if (this.storage.isEmpty()) {
            return false;
        }

        Order peekOrder = (Order)this.storage.peek();

        return  (peekOrder.getProductName().equals(order.getProductName())
                && peekOrder.getAttribute1().equals(order.getAttribute1())
                && peekOrder.getAttribute2().equals(order.getAttribute2()));
    }

    /**
     * Getting size of stack
     * @return int
     */
    public int getStorageSize() {
        if (this.storage.empty()) {
            return 0;
        }

        Order topOrder = (Order) this.storage.peek();
        if (topOrder == null) {
            return 0;
        }
        if (topOrder.getAttribute2().equals(StorageHandler.TIMBER_ATTRIBUTE_STRING)) {
            return 3;
        } else {
            return this.storage.size();
        }
    }

    /**
     * Destroying of top element in stack
     */
    public void destroyTop() {
        if (!this.storage.empty()){
            this.storage.pop();
        } else {
            JOptionPane.showMessageDialog(null, Messages.CANNOT_DESTROY_ITS_EMPTY);
        }
    }

    /**
     * Creating String from object
     * @return String
     */
    @Override
    public String toString() {
        String t = "";
        for (int i = 0; i < storage.size(); i++) {
            t += storage.get(i).toString() + "\t";
        }
        return t;
    }
}
