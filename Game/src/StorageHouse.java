import java.sql.ResultSet;
import java.util.ArrayList;

public class StorageHouse {

    protected Storage[] storageFields;

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
        for (int i = 0; i < this.storageFields.length; i++) {
            if (this.storageFields[i].containsProduct(orderObject)) {
                resultList.add(new SearchResult(
                        true, this.storageFields[i].isOrderAtTop(orderObject),i%3,i/3));
            }
        }

        return resultList.toArray(new SearchResult[0]);
    }
}
