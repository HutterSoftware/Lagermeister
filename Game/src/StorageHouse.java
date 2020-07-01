import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StorageHouse {

    // Defining of class attributes
    protected Storage[] storageFields;
    private int usedStorage = 0;
    private static int maxStorage = 27;
    private boolean storageHouseFull = false;

    // Status constants
    public static Color FIELD_EMPTY = Color.white;
    public static Color FIELD_ONE = Color.green;
    public static Color FIELD_TWO = Color.yellow;
    public static Color FIELD_THREE = Color.red;

    /**
     * Getting status of all storages
     * @return
     */
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

    /**
     * Defining of an standard constructor
     */
    public StorageHouse() {
        storageFields = new Storage[9];

        int level = 0;
        for (int i = 0; i < 9; i++) {
            storageFields[i] = new Storage(i%3,i/3);
        }
    }

    /**
     * This method is searching for needed products
     * @param orderObject
     * @return
     */
    public ArrayList<Integer> findProduct(Order orderObject) {
        ArrayList<Integer> resultList = new ArrayList<>();

        // Getting status of all stoarges
        for (int i = 0; i < this.storageFields.length; i++) {
            if (this.storageFields[i].isOrderAtTop(orderObject)) {
                resultList.add(i);
            }
        }

        return resultList;
    }

    /**
     * Storing order in storage by id
     * @param storageId
     * @param order
     * @return
     */
    public boolean storeOrder(int storageId, Order order) {
        // Storing orders if its possible
        if (storageFields[storageId].addOrder(order)) {
            incrementUsedSpace();
            return true;
        } else {
            // Throwing a warning to user
            JOptionPane.showMessageDialog(null, Messages.STORAGE_FIELD_FULL_MESSAGE);
            return false;
        }
    }

    /**
     * This method is delivering products out of the storage house
     * @param storageId
     * @param order
     * @return
     */
    public boolean deliverOrder(int storageId, Order order) {
        if (this.storageFields[storageId].isOrderAtTop(order)) {
            storageFields[storageId].removeTopElement();
            deincrementUsedSpace();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method will return the top of all stack storage
     * @return
     */
    public Order[] getAllTopOrders() {
        Order[] orderList = new Order[9];
        for (int i = 0; i < this.storageFields.length; i++) {
            orderList[i] = this.storageFields[i].viewTopOrder();
        }
        return orderList;
    }

    /**
     * This method destroy products in stack if destroyButton is selected
     * @param storageId
     */
    public void destroyOrder(int storageId) {
        if (storageId >= 0 && storageId < storageFields.length) {
            storageFields[storageId].destroyTop();
        }
    }

    /**
     * This methid will move objects from one storage to another
     * @param from
     * @param to
     */
    public void moveElement(int from, int to) {
        Order moveOrder = this.storageFields[from].viewTopOrder();
        this.storageFields[from].removeTopElement();

        this.storageFields[to].addOrder(moveOrder);
    }

    /**
     * This method count the complete storage content (increment)
     */
    public void incrementUsedSpace() {
        if (usedStorage < maxStorage) {
            usedStorage++;
            storageHouseFull = false;
        } else {
            storageHouseFull = true;
        }
    }

    /**
     * This method count the complete storage content (deincrement)
     */
    public void deincrementUsedSpace() {
        if (usedStorage > 0) {
            usedStorage--;
            storageHouseFull = false;
        }
    }
}
