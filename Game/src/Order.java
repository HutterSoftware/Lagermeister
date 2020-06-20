import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Order {
    private String productName;
    private String attribute1;
    private String attribute2;
    private int cash;
    private boolean outgoingOrder = false;

    public static String OUTGOING_ORDER_STRING = "Auslagerung";
    public static String INCOMING_ORDER_STRING = "Einlagerung";
    public static String MOVING_ORDER_STRING = "Umlagerung";
    public static String DESTROY_ORDER_STRING = "Objektzerstörung";
    private static String ORDER_PARAMETER_DELIMITER = ";";

    public Order(String productName, String attribute1, String attribute2, String orderType, String cash) {
        this.productName = productName;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        if (orderType.equals(OUTGOING_ORDER_STRING)) {
            this.outgoingOrder = true;
        }

        try {
            this.cash = Integer.parseInt(cash);
        } catch(Exception ex) {
            this.cash = 0;
        }
    }

    public static Order[] createOrderListFromFile(String fileName) {
        File orderFile = new File(fileName);
        ArrayList<Order> orders = new ArrayList<>();
        String line = "";

        if (!orderFile.exists()) {
            return orders.toArray(new Order[0]);
        }

        if (!orderFile.canRead()) {
            return orders.toArray(new Order[0]);
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(orderFile));

            // Skip first line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] orderSplit = line.split(ORDER_PARAMETER_DELIMITER);
                orders.add(new Order(orderSplit[2], orderSplit[3], orderSplit[4], orderSplit[1], orderSplit[5]));
            }

        } catch(IOException ex) {
            return orders.toArray(new Order[0]);
        }

        return orders.toArray(new Order[0]);
    }

    public String getProductName() {
        return productName;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public int getCash() {
        return cash;
    }

    public String getOrderType() {
        if (this.outgoingOrder == true) {
            return Order.OUTGOING_ORDER_STRING;
        } else {
            return Order.INCOMING_ORDER_STRING;
        }
    }

    @Override
    public String toString() {
        String orderTypeString;
        if (this.outgoingOrder) {
            orderTypeString = OUTGOING_ORDER_STRING;
        } else {
            orderTypeString = INCOMING_ORDER_STRING;
        }

        return orderTypeString + "\t" + this.productName + "\t" + this.attribute1 + "\t" + this.attribute2 + "\t" +
                Integer.toString(this.cash);
    }

    public Object[] toArray() {

        return new Object[]{ 0,
                this.getOrderType(),
                this.productName + ", " + this.attribute1 + ", " + this.attribute2,
                this.cash};
    }
}
