import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Order {

    // Creating required attributes
    private String productName;
    private String attribute1;
    private String attribute2;
    private int cash;
    private boolean outgoingOrder = false;

    // Creating standard orders
    public static String OUTGOING_ORDER_STRING = "Auslagerung";
    public static String INCOMING_ORDER_STRING = "Einlagerung";
    public static String MOVING_ORDER_STRING = "Umlagerung";
    public static String DESTROY_ORDER_STRING = "Objektzerstörung";
    private static String ORDER_PARAMETER_DELIMITER = ";";

    /**
     * This constructor initialize all attributes
     * @param productName
     * @param attribute1
     * @param attribute2
     * @param orderType
     * @param cash
     */
    public Order(String productName, String attribute1, String attribute2, String orderType, String cash) {
        // Setting parameter to attribute
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

    /**
     * Load orders from Leistungsnachweis.csv and save it into Order array
     * @param fileName String
     * @return Order[]
     */
    public static Order[] createOrderListFromFile(String fileName) {
        File orderFile = new File(fileName);
        ArrayList<Order> orders = new ArrayList<>();
        String line;

        // Checking file requirements
        if (!orderFile.exists()) {
            return orders.toArray(new Order[0]);
        }

        if (!orderFile.canRead()) {
            return orders.toArray(new Order[0]);
        }

        // Reading file and create Order array
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

    /**
     * Returns product name
     * @return String
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Returns the first attribute
     * @return String
     */
    public String getAttribute1() {
        return attribute1;
    }

    /**
     * Returns the second attribte
     * @return String
     */
    public String getAttribute2() {
        return attribute2;
    }

    /**
     * Returns cash of order
     * @return int
     */
    public int getCash() {
        return cash;
    }

    /**
     * Returns the order type (incoming or out coming)
     * @return String
     */
    public String getOrderType() {
        if (this.outgoingOrder) {
            return Order.OUTGOING_ORDER_STRING;
        } else {
            return Order.INCOMING_ORDER_STRING;
        }
    }

    /**
     * Converting object to string
     * @return String
     */
    @Override
    public String toString() {
        String orderTypeString;
        if (this.outgoingOrder) {
            orderTypeString = OUTGOING_ORDER_STRING;
        } else {
            orderTypeString = INCOMING_ORDER_STRING;
        }

        return orderTypeString + "\t" + this.productName + "\t" + this.attribute1 + "\t" + this.attribute2 + "\t" +
                this.cash;
    }

    /**
     * Returns the standard attributes to an Object array
     * @return Object[]
     */
    public Object[] toArray() {

        return new Object[]{ 0,
                this.getOrderType(),
                this.productName + ", " + this.attribute1 + ", " + this.attribute2,
                this.cash};
    }

    /**
     * Its like the method toaArray. The differ is the lost cash value
     * @return String
     */
    public String toStringWithoutCash() {
        return this.productName +
                ", " + this.attribute1 + ", " + this.attribute2;
    }
}
