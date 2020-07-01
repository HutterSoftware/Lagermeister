import javax.swing.JComponent;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShortCutFun implements KeyListener {

    private View view;

    public static final char TOP_ARROW_CHAR = 'w';
    public static final char RIGHT_ARROW_CHAR = 'd';
    public static final char BOTTOM_ARROW_CHAR = 's';
    public static final char LEFT_ARROW_CHAR = 'a';

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

    public static final char ACTION_CHAR_1 = ' ';
    public static final char ACTION_CHAR_2 = '\n';

    public ShortCutFun(View view) {
        this.view = view;

        for (int i = 0; i < view.getComponentCount(); i++) {
            addKeyListenerToAllComponents((JComponent)view.getComponent(i));
        }
    }

    private void addKeyListenerToAllComponents(JComponent component) {
        if (component != null) {
            component.addKeyListener(this);

            for (Component childrenComponent : component.getComponents()) {
                addKeyListenerToAllComponents((JComponent) childrenComponent);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
                }
        }
    }
}
