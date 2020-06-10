import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StorageHandler extends MouseAdapter {
    public int storageId;

    public StorageHandler(int storageId) {
        this.storageId = storageId;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        System.out.println(storageId);

        if (Start.activeOrderList.get(Start.selectedOrder).getOrderType() == Order.INCOMING_ORDER_STRING) {
            Start.storageHouse.storeOrder(storageId);
        } else {
            Start.storageHouse.deliverOrder(storageId);
        }

    }
}
