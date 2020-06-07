import java.io.File;

public class Order {
    private String productName;
    private String attribute1;
    private String attribute2;
    private boolean outgoingOrder = false;

    private static String OUTGOING_ORDER_STRING = "Auslagerung";

    public Order(String productName, String attribute1, String attribute2, String orderType) {
        this.productName = productName;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        if (orderType.equals(OUTGOING_ORDER_STRING)) {
            this.outgoingOrder = true;
        }
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
}
