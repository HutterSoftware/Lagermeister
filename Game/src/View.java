import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class View extends JFrame {

    // Setting of picture paths and settings
    private static final int STORAGE_FONT_SIZE = 12;
    private final String storagePicturePath = "img/order-pic.png";
    private final String menuPanelPicturePath = "img/menu_panel_background.png";
    private final String infoPanelPicturePath = "img/info_panel_background.png";
    private final String leftArrowPicturePath = "img/left-arrow.png";
    private final String rightArrowPicturePath = "img/right-arrow.png";
    private final int[] menuPanelPictureSettings = {0,0,132,516};
    private final int[] infoPanelPictureSettings = {0,0,900,62};
    private final Dimension gameTargetStartDimension = new Dimension(300,400);

    // Initializing of Swing components
    private JPanel panel1;
    private JToggleButton destroyButton;
    private JToggleButton moveStorageButton;
    private JButton newOrderButton;
    private JButton bilanzButton;
    private JButton orderLeftView;
    private JButton orderRightView;
    private JLabel cashLabel;

    private JLabel storage6;
    private JLabel storage7;
    private JLabel storage3;
    private JLabel storage4;
    private JLabel storage5;
    private JLabel storage0;
    private JLabel storage1;
    private JLabel storage2;
    private JLabel storage8;

    private JLabel product;
    private JLabel orderType;
    private JLabel money;
    private JLabel todoLabel;
    private JButton helpButton;
    private JPanel menuItemGrid;
    private JPanel informationGrid;
    private JPanel storageRootPanel;
    private JPanel orderInformationRootPanel;
    private JPanel storageGrid;
    private JButton questButton;

    // Setting id's to all storage panels
    private static final int storage0Id = 0;
    private static final int storage1Id = 1;
    private static final int storage2Id = 2;
    private static final int storage3Id = 3;
    private static final int storage4Id = 4;
    private static final int storage5Id = 5;
    private static final int storage6Id = 6;
    private static final int storage7Id = 7;
    private static final int storage8Id = 8;

    // Initializing of view constants (Order view management)
    public static int PREVIOUS_ORDER = -1;
    public static int CURRENT_ORDER = 0;
    public static int NEXT_ORDER = 1;

    // Creating of
    public static final int BORDER_THICKNESS = 15;
    private final JLabel[] storagePanelCollection;
    private final OrderManager orderManager;
    private final AccountManager accountManager;
    private final StorageHouse storageHouse;
    private BalanceSheet balanceSheet;
    private HelpDesk helpDesk;
    private GameTarget gameTarget;
    private int selectedMoveId = -1;
    private ShortCutFun shortCutFun;

    /**
     * This constructor initialize all important variables and assin them values
     * @param title JFrame title
     * @param orderManager Contains the full order management (getting new order, controlling of orders)
     * @param accountManager Save all actions of destroying, delivering, storing and moving. Contains Basic values of BalanceSheet JFrame
     * @param storageHouse Management of all storage fields
     */
    public View(String title, OrderManager orderManager, AccountManager accountManager, StorageHouse storageHouse) {

        this.orderManager = orderManager;
        this.accountManager = accountManager;
        this.storageHouse = storageHouse;

        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        this.setSize(900,600);
        this.setResizable(false);

        setActionListener();

        // Creating handlers to storage element
        storage0.addMouseListener(new StorageHandler(storage0Id, this.orderManager, this.accountManager,this));
        storage1.addMouseListener(new StorageHandler(storage1Id, this.orderManager, this.accountManager,this));
        storage2.addMouseListener(new StorageHandler(storage2Id, this.orderManager, this.accountManager,this));
        storage3.addMouseListener(new StorageHandler(storage3Id, this.orderManager, this.accountManager,this));
        storage4.addMouseListener(new StorageHandler(storage4Id, this.orderManager, this.accountManager,this));
        storage5.addMouseListener(new StorageHandler(storage5Id, this.orderManager, this.accountManager,this));
        storage6.addMouseListener(new StorageHandler(storage6Id, this.orderManager, this.accountManager,this));
        storage7.addMouseListener(new StorageHandler(storage7Id, this.orderManager, this.accountManager,this));
        storage8.addMouseListener(new StorageHandler(storage8Id, this.orderManager, this.accountManager,this));

        // Saving all Swing storage elements into to locate the Storage element easier.
        this.storagePanelCollection = new JLabel[9];
        this.storagePanelCollection[0] = storage0;
        this.storagePanelCollection[1] = storage1;
        this.storagePanelCollection[2] = storage2;
        this.storagePanelCollection[3] = storage3;
        this.storagePanelCollection[4] = storage4;
        this.storagePanelCollection[5] = storage5;
        this.storagePanelCollection[6] = storage6;
        this.storagePanelCollection[7] = storage7;
        this.storagePanelCollection[8] = storage8;

        // Setting font size of all storage panels
        for (JLabel storageLabel : storagePanelCollection) {
            storageLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, STORAGE_FONT_SIZE));
        }

        // Setting effects to elements and draw many pictures to the specific elements

        orderLeftView.setIcon(getImage(this.leftArrowPicturePath));
        orderRightView.setIcon(getImage(this.rightArrowPicturePath));

        // Show JFrame before draw images
        this.setVisible(true);
        loadGamePictures(true);

        this.helpDesk = new HelpDesk();
        this.balanceSheet = new BalanceSheet();
        Start.accountManager.setBalanceSheet(this.balanceSheet);
        this.gameTarget = new GameTarget();
        this.gameTarget.setSize(this.gameTargetStartDimension);
    }

    /**
     * After Initializing of Swing components will create this method all needed ActionListener
     */
    private void setActionListener() {

        // Creating ActionListener of newButtonOrder
        newOrderButton.addActionListener(e -> {
            // Loading a new order from file (Leistungsnachweis.csv)
            orderManager.loadNewOrder();

            // Updating all GUI elements
            updateAll();
        });

        // Creating ActionListener of orderRightView
        orderRightView.addActionListener(e -> {
            // Updating view elements
            orderViewButtonAction(View.NEXT_ORDER);
        });

        // Creating ActionListener orderLeftView
        orderLeftView.addActionListener(e -> {
            // Updating of view elements
            orderViewButtonAction(View.PREVIOUS_ORDER);
        });

        // Creating ActionListener of destroyButton
        destroyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // Checking of destroyButton status
                if (destroyButton.isSelected()) {
                    moveStorageButton.setSelected(false);
                }

                loadGamePictures(false);
            }
        });

        // Creating ActionListener of bilanzButton
        bilanzButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check of initializing status. If its null -> initialize
                if (balanceSheet == null) {
                    balanceSheet = new BalanceSheet();

                    // Updating table with gone transactions
                    balanceSheet.updateTable(accountManager.getAllTransactions());
                }

                // Setting visibility
                balanceSheet.showBalanceSheet();
            }
        });

        // Creating ActionListener of helpButton
        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Checking of initializing status. If its null, than initialize
                if (helpDesk == null) {
                    helpDesk = new HelpDesk();
                } else {
                    // If its initialize, than show the JFrame
                    helpDesk.setVisible(true);
                }
            }
        });

        // Creating ActionListener of moveStorageButton
        moveStorageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                destroyButton.setSelected(false);
                destroyButton.setText("Zerstören");

                // Set information text to label
                todoLabel.setText(Messages.SELECT_STORAGE_OBJECT_TO_MOVE);

                // Checking toggle status of moveStorageButton
                if (!moveStorageButton.isSelected()) {
                    // If its unselected than reset view
                    resetSelectedMoveId();
                }

                visualizeStorage();
            }
        });

        questButton.addActionListener(e13 -> this.gameTarget.setVisible(true));
    }

    public void unSelectMoveButton() {
        this.moveStorageButton.setSelected(false);
    }

    public void setSelectedMoveButton() {
        this.moveStorageButton.setSelected(true);
    }

    /**
     * Returning GameTarget object
     * @return GameTarget
     */
    public GameTarget getGameTarget() {
        return this.gameTarget;
    }

    /**
     * Returns HelpDesk object
     * @return HelpDesk
     */
    public HelpDesk getHelpDesk() {
        return this.helpDesk;
    }

    /**
     * Returns ShortCut object
     * @return ShortCutFun
     */
    public ShortCutFun getShortCutFun() {
        return this.shortCutFun;
    }

    /**
     * This method set selected status to destroy button
     */
    public void setSelectDestroyButton() {
        destroyButton.setSelected(true);
    }

    /**
     * This method set deselected status to destroy button
     */
    public void setDeSelectedDestroyButton() {
        destroyButton.setSelected(false);
    }

    /**
     * This method set deselected status to move button
     */
    public void setDeselectedMoveButton() {
        moveStorageButton.setSelected(false);
    }

    /**
     * This method will paint all needed pictures to JPanels delayed
     */
    private void loadGamePictures(boolean all) {
        // Setting of background status
        this.menuItemGrid.setOpaque(true);
        this.informationGrid.setOpaque(false);

        Timer paintTimer = new Timer(5, e -> {
            // Printing of images
            drawImage(this.menuItemGrid.getGraphics(), this.menuPanelPicturePath, this.menuPanelPictureSettings);
            drawImage(this.informationGrid.getGraphics(), this.infoPanelPicturePath, this.infoPanelPictureSettings);
            newOrderButton.requestFocus();
            moveStorageButton.requestFocus();
            destroyButton.requestFocus();
            bilanzButton.requestFocus();
            helpButton.requestFocus();
            questButton.requestFocus();
        });
        paintTimer.setRepeats(false);
        paintTimer.start();

        if (all) {
            // Creating of focus Timer
            Timer timer = new Timer(1000, e -> {
                // Focusing of all elements in JPanel to set it in foreground
                for (Component component : menuItemGrid.getComponents()) {
                    component.requestFocus();
                }

                printStoragePictures();
                focusInformationGridElements();
                shortCutFun = new ShortCutFun(this);
                visualizeStorage();

            });

            // Setting one shot timer. Starting of Timer
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Focusing of todoLabel and cashLabel (preventing of overdrawing)
     */
    private void focusInformationGridElements() {
        todoLabel.setVisible(true);
        cashLabel.setVisible(true);
    }

    /**
     * Basic method to draw pictures to graphic
     * @param graphic Graphic of the Swing element
     * @param imagePath Path of image
     * @param settings Settings like x, y, width and height
     */
    private void drawImage(Graphics graphic, String imagePath, int[] settings) {
        try {
            // Getting Image and paint the component
            Image menuPanelBackground = Objects.requireNonNull(getImage(imagePath)).getImage();
            paintComponents(graphic);

            // Drawing the image
            graphic.drawImage(menuPanelBackground, settings[0],
                    settings[1],
                    settings[2],
                    settings[3],
                    null);
            graphic.dispose();
        } catch (NullPointerException ex) {
            throw ex;
        }
    }

    /**
     * This method will only returns an ImageIcon based on the path parameter
     * @param path Path of the image
     * @return Returns an ImageIcon to use it later
     */
    private ImageIcon getImage(String path) {
        try {
            // Load picture by using File object
            BufferedImage bImage = ImageIO.read(new File(path));
            return new ImageIcon(bImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method paint the pictures of all storage components
     */
    private void printStoragePictures() {
        // Load image
        ImageIcon icon = getImage(storagePicturePath);

        // Setting panel size
        for (JLabel panel : storagePanelCollection) {
            Thread graphicThread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    panel.setLayout(null);
                    panel.setSize(500,500);
                    panel.setIcon(icon);
                }
            };
            graphicThread.start();
        }
    }

    /**
     * Printing order content to panel
     */
    public void printPanelString() {
        // Get all top orders
        Order[] allTopOrders = storageHouse.getAllTopOrders();
        for (int i = 0; i < allTopOrders.length; i++) {
            JLabel panel = storagePanelCollection[i];
            // Setting text to panel
            if (allTopOrders[i] != null){
                panel.setText(allTopOrders[i].toStringWithoutCash());
            } else {
                panel.setText("leer");
            }
        }
    }

    /**
     * If moveButton is selected, than will be changed the border color. Border color meaning is described in help
     */
    private void markSelectableElements() {
        // Get all storage status
        Color[] status = Start.storageHouse.getStorageStatus();

        for (int i = 0; i < status.length; i++) {
            // Printing border to storage panel
            if (status[i] == StorageHouse.FIELD_TWO || status[i] == StorageHouse.FIELD_ONE ||
                status[i] == StorageHouse.FIELD_THREE) {
                if (this.shortCutFun.getKeySelectedStorage() == i) {
                    storagePanelCollection[i].setBorder(getStandardBorder(Color.MAGENTA, true));
                } else {
                    storagePanelCollection[i].setBorder(getStandardBorder(Color.MAGENTA, false));
                }
            } else {
                if (this.shortCutFun.getKeySelectedStorage() == i) {
                    storagePanelCollection[i].setBorder(getStandardBorder(Color.WHITE, true));
                } else {
                    storagePanelCollection[i].setBorder(getStandardBorder(Color.MAGENTA, false));
                }
            }
        }
    }

    /**
     * Changing border color for moving
     */
    public void markAvailableMovingTargets() {
        // Getting storage status
        Color[] status = Start.storageHouse.getStorageStatus();

        // Setting border color
        for (int i = 0; i < status.length; i++) {
            if (status[i] != StorageHouse.FIELD_THREE) {
                if (this.shortCutFun.getKeySelectedStorage() == i) {
                    storagePanelCollection[i].setBorder(getStandardBorder(Color.CYAN, true));
                } else {
                    storagePanelCollection[i].setBorder(getStandardBorder(Color.CYAN, false));
                }
            } else {
                if (this.shortCutFun.getKeySelectedStorage() == i) {
                    storagePanelCollection[i].setBorder(getStandardBorder(Color.WHITE, true));
                } else {
                    storagePanelCollection[i].setBorder(getStandardBorder(Color.WHITE, false));
                }
            }
        }
    }

    /**
     * return moveButton selected state
     * @return boolean
     */
    public boolean isMoveButtonToggled() {
        return this.moveStorageButton.isSelected();
    }

    /**
     * returns the selected id of element to move
     * @return boolean
     */
    public int getSelectedMoveId() {
        return this.selectedMoveId;
    }

    /**
     * This method is the second part of the moving procedure
     * @param id int value of storage
     */
    public void setSelectedMoveId(int id) {
        this.selectedMoveId = id;
    }

    /**
     * After moving this variable will be reset to -1
     */
    public void resetSelectedMoveId() {
        this.selectedMoveId = -1;
    }

    /**
     * Change select state of move toggle button
     */
    public void disSelectMoveToggleButton() {
        this.moveStorageButton.setSelected(false);
    }

    /**
     * This method will be update all Swing components
     */
    public void updateAll() {
        setEnableControlOfViewButtons();
        updateCash(accountManager.getAccount());
        orderViewButtonAction(View.CURRENT_ORDER);
        visualizeStorage();
    }

    /**
     * Getting the previous of next order from active order list of OrderManager
     * @param prev previous active order id
     */
    public void orderViewButtonAction(int prev) {
        // Selecting the wanted order
        if (prev == View.PREVIOUS_ORDER) {
            orderManager.selectPrevOrder();
        } else if (prev == View.NEXT_ORDER){
            orderManager.selectNextOrder();
        }

        // Updating GUI
        setEnableControlOfViewButtons();visualizeStorage();
        updateOrderViewItems();
        visualizeStorage();
    }

    /**
     * This method create a standard border
     * @param color Color of border
     * @return returns border object
     */
    public static Border getStandardBorder(Color color, boolean dashing) {
        if (!dashing) {
            return BorderFactory.createLineBorder(color, View.BORDER_THICKNESS);
        } else {
            return BorderFactory.createDashedBorder(color, 15,2,1,true);
        }
    }

    /**
     * This method will paint border lines and set order text to panel
     */
    public void visualizeStorage() {
        // Get current order
        Order order = this.orderManager.getCurrentOrder();

        // Get status of any storage
        Color[] storageStatus = this.storageHouse.getStorageStatus();


        // Printing border
        if (moveStorageButton.isSelected()) {
            if (selectedMoveId == -1 &&  moveStorageButton.isSelected()) {
                markSelectableElements();
            } else if (selectedMoveId != -1){
                markAvailableMovingTargets();
            }
        } else if (Objects.equals(order.getOrderType(), Order.INCOMING_ORDER_STRING)) {
            for (int i = 0; i < storageStatus.length; i++) {
                if (this.shortCutFun.getKeySelectedStorage() == i) {
                    storagePanelCollection[i].setBorder(getStandardBorder(storageStatus[i], true));
                } else {
                    storagePanelCollection[i].setBorder(getStandardBorder(storageStatus[i], false));
                }
            }

        } else if (Objects.equals(order.getOrderType(), Order.OUTGOING_ORDER_STRING)) {
            StorageHouse.SearchResult[] results = Start.storageHouse.findProduct(order);
            for (int i = 0; i < this.storagePanelCollection.length; i++) {
                if (this.shortCutFun.getKeySelectedStorage() == i) {
                    storagePanelCollection[i].setBorder(getStandardBorder(results[i].getStatus(), true));
                } else {
                    storagePanelCollection[i].setBorder(getStandardBorder(results[i].getStatus(), false));
                }
            }
        } else {
            for (int i = 0; i < storagePanelCollection.length; i++) {
                if (this.shortCutFun.getKeySelectedStorage() == i) {
                    storagePanelCollection[i].setBorder(getStandardBorder(Color.WHITE, true));
                } else {
                    storagePanelCollection[i].setBorder(getStandardBorder(Color.WHITE, false));
                }
            }
        }

        // Printing panel text
        printPanelString();
    }

    /**
     * Updating cash label
     * @param value New accounted value
     */
    public void updateCash(int value) {
        this.cashLabel.setText(Integer.toString(value));
    }

    /**
     *  Updating order view in the header area of the game
     */
    private void updateOrderViewItems() {
        if (this.orderManager.hasOrders()) {
            // Load current order
            Order order = orderManager.getCurrentOrder();

            // Updating order Information
            this.product.setText(order.getProductName() + " " + order.getAttribute1() + " " + order.getAttribute2());
            this.orderType.setText(order.getOrderType());
            this.money.setText(order.getCash() + "€");

            if (Objects.equals(order.getOrderType(), Order.INCOMING_ORDER_STRING)) {
                this.todoLabel.setText(Messages.SELECT_STORAGE_TO_STORE);
            } else {
                this.todoLabel.setText(Messages.SELECT_STORAGE_TO_DELIVER);
            }
        } else {
            this.product.setText("");
            this.orderType.setText("");
            this.money.setText("0€");
        }

        // Set new enable status
        setEnableControlOfViewButtons();
    }

    /**
     * Setting enable status of order buttons
     */
    private void setEnableControlOfViewButtons() {
        if (this.orderManager.isSelectedOrderFirst()) {
            orderLeftView.setEnabled(false);
            orderRightView.setEnabled(true);
        } else if (this.orderManager.isSelectedOrderLast()) {
            orderLeftView.setEnabled(true);
            orderRightView.setEnabled(false);
        } else {
            orderLeftView.setEnabled(true);
            orderRightView.setEnabled(true);
        }
    }

    /**
     * Returning of storageHouse
     * @return Returns the StorageHouse object
     */
    public StorageHouse getStorageHouse() {
        return this.storageHouse;
    }

    /**
     * Returning of balance sheet
     * @return Returns the complete balance sheet JFrame
     */
    public BalanceSheet getBalanceSheet() {
        return this.balanceSheet;
    }

    /**
     * Returning of destroyButton pressed status
     * @return Status of destroyButton
     */
    public boolean isDestroyButtonPressed() {
        return this.destroyButton.isSelected();
    }

    /**
     * Returning of storage array
     * @return Array of all storage labels
     */
    public JLabel[] getStoragePanels() {
        return this.storagePanelCollection;
    }

    /**
     * Resetting attributes
     */
    public void reset() {
        this.moveStorageButton.setSelected(false);
        this.destroyButton.setSelected(false);
        updateAll();
        this.balanceSheet.reset();
    }
}
