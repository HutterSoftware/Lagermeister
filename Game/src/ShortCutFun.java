import javax.swing.*;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShortCutFun implements KeyListener {

    private View view;

    public static final char TOP_ARROW_CHAR_LOWER = 'w';
    public static final char TOP_ARROW_CHAR_UPPER = 'W';

    public static final char RIGHT_ARROW_CHAR_LOWER = 'd';
    public static final char RIGHT_ARROW_CHAR_UPPER = 'D';

    public static final char BOTTOM_ARROW_CHAR_LOWER = 's';
    public static final char BOTTOM_ARROW_CHAR_UPPER = 'S';

    public static final char LEFT_ARROW_CHAR_LOWER = 'a';
    public static final char LEFT_ARROW_CHAR_UPPER = 'A';


    public static final char ORDER_VIEW_RIGHT_ARROW_CHAR_LOWER = 'e';
    public static final char ORDER_VIEW_RIGHT_ARROW_CHAR_UPPER = 'E';

    public static final char ORDER_VIEW_LEFT_ARROW_CHAR_LOWER = 'q';
    public static final char ORDER_VIEW_LEFT_ARROW_CHAR_UPPER = 'Q';

    public static final char GET_NEW_ORDER_CHAR_LOWER = 'n';
    public static final char GET_NEW_ORDER_CHAR_UPPER = 'N';

    public static final char DESTROY_TOGGLE_CHAR_LOWER = 't';
    public static final char DESTROY_TOGGLE_CHAR_UPPER = 'T';

    public static final char MOVE_TOGGLE_BUTTON_LOWER = 'm';
    public static final char MOVE_TOGGLE_BUTTON_UPPER = 'M';

    public static final char HELP_BUTTON_LOWER = 'h';
    public static final char HELP_BUTTON_UPPER = 'H';

    public static final char BALANCE_SHEET_BUTTON_LOWER = 'b';
    public static final char BALANCE_SHEET_BUTTON_UPPER = 'B';

    public static final char ACTION_CHAR_1 = ' ';
    public static final char ACTION_CHAR_2 = '\n';

    public static final int START_POSITION = 4;
    public static final int UNSET_POSITION = -1;
    private int keySelectedStorage = -1;
    private static final String[] LABEL_TEXT_BLACKLIST = {"Neuer Auftrag", "Umagern", "Zerstören", "Bilanz", "Hilfe", ""};
    private static String PREFERRED_OBJECT_TYPE = "class javax.swing.JLabel";

    /**
     * Initialize all attributes
     * @param view element of key listener
     */
    public ShortCutFun(View view) {
        this.view = view;

        for (int i = 0; i < view.getComponentCount(); i++) {
            addKeyListenerToAllComponents((JComponent)view.getComponent(i));
        }
    }

    /**
     * Adding KeyListener to all GUI elements to every time KeyEvent whatever element is focused
     * @param component GUI component
     */
    private void addKeyListenerToAllComponents(JComponent component) {
        if (component != null) {
            component.addKeyListener(this);

            for (Component childrenComponent : component.getComponents()) {
                addKeyListenerToAllComponents((JComponent) childrenComponent);
            }
        }
    }

    /**
     * // This method set the new key focus position
     * @param stepSize incremental or decremental step size
     * @param max1 limit value 1
     * @param max2 limit value 2
     * @param max3 limit value 3
     */
    private void moveKeyFocus(int stepSize, int max1, int max2, int max3) {
        // Check start position
        if (this.keySelectedStorage == ShortCutFun.UNSET_POSITION) {
            this.keySelectedStorage = ShortCutFun.START_POSITION;
        }

        // Check left level
        if (this.keySelectedStorage != max1 && this.keySelectedStorage != max2 && this.keySelectedStorage != max3) {
            this.keySelectedStorage += stepSize;
        }
    }

    /**
     * Move key focus to top neighbour
     */
    private void moveKeyFocusTop() {
        moveKeyFocus(3,6,7,8);
    }

    /**
     * Move key focus to right neighbour
     */
    private void moveKeyFocusRight() {
        moveKeyFocus(1,2,5,8);
    }

    /**
     * Move key focus to bottom neighbour
     */
    private void moveKeyFocusBottom() {
        moveKeyFocus(-3,0,1,2);
    }

    /**
     * Move key focus to left neighbour
     */
    private void moveKeyFocusLeft() {
        moveKeyFocus(-1,0,3,6);
    }

    /**
     * Returns the current key position
     * @return Key position
     */
    public int getKeySelectedStorage() {
        return this.keySelectedStorage;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!e.getSource().getClass().toString().equals(PREFERRED_OBJECT_TYPE)) {
            return;
        }

        JLabel sourceLabel = (JLabel) e.getSource();

        for (String labelText : this.LABEL_TEXT_BLACKLIST) {
            if (sourceLabel.getText().equals(labelText)) {
                return;
            }
        }

        switch (e.getKeyChar()) {

            case ShortCutFun.GET_NEW_ORDER_CHAR_UPPER:
            case ShortCutFun.GET_NEW_ORDER_CHAR_LOWER:
                Start.orderManager.loadNewOrder();
                view.updateAll();
                break;

            case ShortCutFun.ORDER_VIEW_RIGHT_ARROW_CHAR_UPPER:
            case ShortCutFun.ORDER_VIEW_RIGHT_ARROW_CHAR_LOWER:
                view.orderViewButtonAction(View.NEXT_ORDER);
                view.updateAll();
                break;

            case ShortCutFun.ORDER_VIEW_LEFT_ARROW_CHAR_UPPER:
            case ShortCutFun.ORDER_VIEW_LEFT_ARROW_CHAR_LOWER:
                view.orderViewButtonAction(View.PREVIOUS_ORDER);
                view.updateAll();
                break;

            case ShortCutFun.DESTROY_TOGGLE_CHAR_UPPER:
            case ShortCutFun.DESTROY_TOGGLE_CHAR_LOWER:
                if (view.isDestroyButtonPressed()) {
                    view.setDeSelectedDestroyButton();
                } else {
                    view.setSelectDestroyButton();
                    view.setDeselectedMoveButton();
                }


                break;

            case ShortCutFun.MOVE_TOGGLE_BUTTON_UPPER:
            case ShortCutFun.MOVE_TOGGLE_BUTTON_LOWER:
                if (view.isMoveButtonToggled()) {
                    view.setDeselectedMoveButton();
                } else {
                    view.setSelectedMoveButton();
                    view.setDeSelectedDestroyButton();
                    view.markAvailableMovingTargets();
                }
                view.getMoveStorageButton().getMouseListeners()[0].mouseClicked(null);
                System.out.println(view.getMoveStorageButton().getMouseListeners().length);
                break;

            case ShortCutFun.TOP_ARROW_CHAR_LOWER:
            case ShortCutFun.TOP_ARROW_CHAR_UPPER:
                moveKeyFocusTop();
                break;

            case ShortCutFun.RIGHT_ARROW_CHAR_LOWER:
            case ShortCutFun.RIGHT_ARROW_CHAR_UPPER:
                moveKeyFocusRight();
                break;

            case ShortCutFun.BOTTOM_ARROW_CHAR_LOWER:
            case ShortCutFun.BOTTOM_ARROW_CHAR_UPPER:
                moveKeyFocusBottom();
                break;

            case ShortCutFun.LEFT_ARROW_CHAR_LOWER:
            case ShortCutFun.LEFT_ARROW_CHAR_UPPER:
                moveKeyFocusLeft();
                break;

            case ShortCutFun.ACTION_CHAR_1:
            case ShortCutFun.ACTION_CHAR_2:
                view.getStoragePanels()[this.keySelectedStorage].getMouseListeners()[0].mouseClicked(null);
                break;

            case ShortCutFun.HELP_BUTTON_LOWER:
            case ShortCutFun.HELP_BUTTON_UPPER:
                if (view.getHelpDesk() == null) {
                    return;
                }
                view.getHelpDesk().setVisible(true);
                break;

            case ShortCutFun.BALANCE_SHEET_BUTTON_LOWER:
            case ShortCutFun.BALANCE_SHEET_BUTTON_UPPER:
                if (view.getBalanceSheet() == null) {
                    return;
                }
                view.getBalanceSheet().showBalanceSheet();
                break;
        }
        view.visualizeStorage();
    }
}
