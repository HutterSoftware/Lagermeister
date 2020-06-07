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

    public Order removeTopElement() {
        Order order = (Order)this.storage.peek();
        Start.absoluteCash += order.getCash();
        this.storage.pop();
        return order;
    }

    public boolean containsProduct(Order order) {
        if (this.storage.search(order) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOrderAtTop(Order order) {
        if (this.storage.peek() == order) {
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
}
