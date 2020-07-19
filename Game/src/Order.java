import java.io.*;
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
     * @param productName String
     * @param attribute1 String
     * @param attribute2 String
     * @param orderType String
     * @param cash String
     */
    public Order(String productName, String attribute1, String attribute2, String orderType, String cash) {
        // Setting parameter to attribute
        this.productName = Start.toUtf8(productName);
        this.attribute1 = Start.toUtf8(attribute1);
        this.attribute2 = Start.toUtf8(attribute2);
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
            //BufferedReader reader = new BufferedReader(new InputStreamReader(orderFile));
            BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(new FileInputStream(orderFile),"UTF-8"));

            // Skip first line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] orderSplit = line.split(ORDER_PARAMETER_DELIMITER);
                orders.add(new Order(Start.toUtf8(orderSplit[2]), Start.toUtf8(orderSplit[3]),
                                     Start.toUtf8(orderSplit[4]), Start.toUtf8(orderSplit[1]),
                                     Start.toUtf8(orderSplit[5])));
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
        return Start.toUtf8(productName);
    }

    /**
     * Returns the first attribute
     * @return String
     */
    public String getAttribute1() {
        return Start.toUtf8(attribute1);
    }

    /**
     * Returns the second attribte
     * @return String
     */
    public String getAttribute2() {
        return Start.toUtf8(attribute2);
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
            return Start.toUtf8(Order.OUTGOING_ORDER_STRING);
        } else {
            return Start.toUtf8(Order.INCOMING_ORDER_STRING);
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

        return Start.toUtf8(orderTypeString + "\t" + this.productName + "\t" + this.attribute1 + "\t" + this.attribute2 + "\t" +
                this.cash);
    }

    /**
     * Returns the standard attributes to an Object array
     * @return Object[]
     */
    public Object[] toArray() {

        return new Object[]{ 0,
                Start.toUtf8(this.getOrderType()),
                Start.toUtf8(this.productName + ", " + this.attribute1 + ", " + this.attribute2),
                this.cash};
    }

    /**
     * Its like the method toaArray. The differ is the lost cash value
     * @return String
     */
    public String toStringWithoutCash() {
        return Start.toUtf8(this.productName +
                ", " + this.attribute1 + ", " + this.attribute2);
    }
}
