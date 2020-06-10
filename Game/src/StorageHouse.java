import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StorageHouse {

    protected Storage[] storageFields;
    private int usedStorage = 0;
    private static int maxStorage = 27;
    private boolean storageHouseFull = false;

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

    public SearchResult[] findProduct(Order orderObject) {
        ArrayList<SearchResult> resultList = new ArrayList<>();
        /*for (int i = 0; i < this.storageFields.length; i++) {
            if (this.storageFields[i].containsProduct(orderObject)) {
                resultList.add(new SearchResult(
                        true, this.storageFields[i].isOrderAtTop(orderObject),i%3,i/3));
            }
        }*/

        for (int i = 0; i < this.storageFields.length; i++) {
            System.out.println(this.storageFields[i].toString());
        }

        return resultList.toArray(new SearchResult[0]);
    }

    public void storeOrder(int storageId) {
        Order order = Start.activeOrderList.get(Start.selectedOrder);
        if (storageFields[storageId].addOrder(order)) {
            Start.view.addMoney(order.getCash());
            incrementUsedSpace();
        } else {
            JOptionPane.showMessageDialog(null, Messages.STORAGE_FIELD_FULL_MESSAGE);
        }
    }

    public void deliverOrder(int storageId) {
        Order order = Start.activeOrderList.get(Start.selectedOrder);
        if (this.storageFields[storageId].isOrderAtTop(order)) {
            Start.view.addMoney(order.getCash());
            deincrementUsedSpace();
        }
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
