import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StorageHouse {

    protected Storage[] storageFields;
    private int usedStorage = 0;
    private static int maxStorage = 27;
    private boolean storageHouseFull = false;

    public static Color FIELD_EMPTY = Color.white;
    public static Color FIELD_ONE = Color.green;
    public static Color FIELD_TWO = Color.yellow;
    public static Color FIELD_THREE = Color.red;


    public Color[] getStorageStatus() {
        Color[] status = new Color[9];

        for (int i = 0; i < 9; i++) {
            switch (storageFields[i].getStorageSize()) {
                case 0:
                    status[i] = FIELD_EMPTY;
                    break;

                case 1:
                    status[i] = FIELD_ONE;
                    break;

                case 2:
                    status[i] = FIELD_TWO;
                    break;

                case 3:
                    status[i] = FIELD_THREE;
            }
        }

        return status;
    }

    /*

     Index definition of storageFields array

     **-------------+-------------+-------------**
     **             |             |             **
     **      6      |       7     |       8     **
     **             |             |             **
     **-------------+-------------+-------------**
     **             |             |             **
     **      3      |       4     |       5     **
     **             |             |             **
     **-------------+-------------+-------------**
     **             |             |             **
     **      0      |       1     |       2     **
     **             |             |             **
     **-------------+-------------+-------------**

     */


    class SearchResult {
        private boolean inStorage;
        private boolean orderOnTop;
        private int locationX;
        private int locationY;

        public SearchResult(boolean inStorage, boolean orderOnTop, int locationX, int locationY) {
            this.inStorage = inStorage;
            this.orderOnTop = orderOnTop;
            this.locationX = locationX;
            this.locationY = locationY;
        }

        public boolean isInStorage() {
            return inStorage;
        }

        public boolean isOrderOnTop() {
            return orderOnTop;
        }

        public int getLocationX() {
            return locationX;
        }

        public int getLocationY() {
            return locationY;
        }

        public int getArrayLocation() {
            return this.locationY * 3 + this.locationX;
        }
    }

    public StorageHouse() {
        storageFields = new Storage[9];

        int level = 0;
        for (int i = 0; i < 9; i++) {
            storageFields[i] = new Storage(i%3,i/3);
        }
    }

    public ArrayList<Integer> findProduct(Order orderObject) {

        ArrayList<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < this.storageFields.length; i++) {
            if (this.storageFields[i].isOrderAtTop(orderObject)) {
                resultList.add(i);
            }
        }

        return resultList;
    }

    public boolean storeOrder(int storageId, Order order) {
        if (storageFields[storageId].addOrder(order)) {
            incrementUsedSpace();
            return true;
        } else {
            JOptionPane.showMessageDialog(null, Messages.STORAGE_FIELD_FULL_MESSAGE);
            return false;
        }
    }

    public boolean deliverOrder(int storageId, Order order) {
        if (this.storageFields[storageId].isOrderAtTop(order)) {
            storageFields[storageId].removeTopElement();
            deincrementUsedSpace();
            return true;
        } else {
            return false;
        }
    }

    public Order[] getAllTopOrders() {
        Order[] orderList = new Order[9];
        for (int i = 0; i < this.storageFields.length; i++) {
            orderList[i] = this.storageFields[i].viewTopOrder();
        }
        return orderList;
    }

    public void destroyOrder(int storageId) {
        if (storageId >= 0 && storageId < storageFields.length) {
            storageFields[storageId].destroyTop();
        }
    }

    public void moveElement(int from, int to) {
        Order moveOrder = this.storageFields[from].viewTopOrder();
        this.storageFields[from].removeTopElement();

        this.storageFields[to].addOrder(moveOrder);
    }

    public void incrementUsedSpace() {
        if (usedStorage < maxStorage) {
            usedStorage++;
            storageHouseFull = false;
        } else {
            storageHouseFull = true;
        }
    }

    public void deincrementUsedSpace() {
        if (usedStorage > 0) {
            usedStorage--;
            storageHouseFull = false;
        }
    }
}
