import javax.swing.*;
import java.util.Stack;

public class Storage {
    private int fieldPositionX;
    private int fieldPositionY;
    private Stack storage;

    public Storage(int positionX, int positionY) {
        this.fieldPositionX = positionX;
        this.fieldPositionY = positionY;
        this.storage = new Stack();
    }

    public Order viewTopOrder() {
        if (this.storage.empty()) {
            return null;
        } else {
            return (Order) this.storage.peek();
        }
    }

    public boolean addOrder(Order order) {
        if (this.storage.size() < 3) {
            this.storage.push(order);
            return true;
        } else{
            return false;
        }
    }

    public boolean removeTopElement() {
        if (!this.storage.empty()) {
            this.storage.pop();
            return true;
        } else {
            return false;
        }
    }

    private boolean equal(Order order) {
        this.storage.contains(order);

        boolean result = false;
        Stack btStack = new Stack<>();
        while (!this.storage.empty()) {
            Order btOrder = (Order) this.storage.peek();
            if (btOrder.getProductName() == order.getProductName()
                    && btOrder.getAttribute1() == order.getAttribute1()
                    && btOrder.getAttribute2() == order.getAttribute2()) {
                result = true;
            }
            btStack.push(this.storage.peek());
            this.storage.pop();
        }

        while(!btStack.empty()) {
            this.storage.push(btStack.peek());
            btStack.pop();
        }
        return result;
    }

    public boolean containsProduct(Order order) {
        if (equal(order)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOrderAtTop(Order order) {
        if (this.storage.isEmpty()) {
            return false;
        }

        Order peekOrder = (Order)this.storage.peek();

        if  (peekOrder.getProductName().equals(order.getProductName())
                && peekOrder.getAttribute1().equals(order.getAttribute1())
                && peekOrder.getAttribute2().equals(order.getAttribute2())) {

            return true;
        } else {
            return false;
        }
    }

    public int getFieldPositionY() {
        return fieldPositionY;
    }

    public int getFieldPositionX() {
        return fieldPositionX;
    }

    public int getStorageSize() {
        if (this.storage.empty()) {
            return 0;
        }

        Order topOrder = (Order) this.storage.peek();
        if (topOrder == null) {
            return 0;
        }
        if (topOrder.getAttribute2().equals(OrderExceptionCheck.TIMBER_ATTRIBUTE_STRING)) {
            return 3;
        } else {
            return this.storage.size();
        }
    }

    public void destroyTop() {
        if (!this.storage.empty()){
            this.storage.pop();
        } else {
            JOptionPane.showMessageDialog(null, Messages.CANNOT_DESTROY_ITS_EMPTY);
        }
    }

    @Override
    public String toString() {
        String t = "";
        for (int i = 0; i < storage.size(); i++) {
            t += storage.get(i).toString() + "\t";
        }
        return t;
    }
}
