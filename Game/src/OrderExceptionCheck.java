public class OrderExceptionCheck {

    public static String TIMBER_ATTRIBUTE_STRING = "Balken";
    public static String HEAVY_STONE_ATTRIBUTE_STRING = "Schwer";

    public static boolean areParameterValid(Order order, Storage storage) {
        if (order == null) {
            return false;
        }

        return true;
    }

    public static boolean isTimberLocationValid(Order order, Storage storage) {
        if (!areParameterValid(order, storage)){
            return true;
        }
        else {
            return (order.getAttribute2().equals(OrderExceptionCheck.TIMBER_ATTRIBUTE_STRING) &&
                storage.getStorageSize() == 0);
        }
    }

    public static boolean isHeavyStoneLocationValid(Order order, int storageId) {
        if (!areParameterValid(order, Start.storageHouse.storageFields[storageId])) {
            return true;
        }

        if (!order.getAttribute2().equals(OrderExceptionCheck.HEAVY_STONE_ATTRIBUTE_STRING)){
            return true;
        } else {
            return (order.getAttribute2().equals(OrderExceptionCheck.HEAVY_STONE_ATTRIBUTE_STRING) &&
                    storageId < 3);
        }
    }
}
