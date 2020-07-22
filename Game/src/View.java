import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class View extends JFrame {

    // Setting of picture paths and settings
    private static final int STORAGE_FONT_SIZE = 12;
    private final String storagePicturePath = "img/order-pic.png";
    private final String menuPanelPicturePath = "img/menu_panel_background.png";
    private final String infoPanelPicturePath = "img/info_panel_background.png";
    private final String leftArrowPicturePath = "img/left-arrow.png";
    private final String rightArrowPicturePath = "img/right-arrow.png";
    private final int[] menuPanelPictureSettings = {0, 0, 132, 516};
    private final int[] infoPanelPictureSettings = {0, 0, 900, 62};
    private final Dimension gameTargetStartDimension = new Dimension(300, 400);

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
    private JPanel orderInfoGrid;
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
    private Order orderPunishment;
    public CharsetDecoder utf8Decoder;

    /**
     * This constructor initialize all important variables and assin them values
     *
     * @param title          JFrame title
     * @param orderManager   Contains the full order management (getting new order, controlling of orders)
     * @param accountManager Save all actions of destroying, delivering, storing and moving. Contains Basic values of BalanceSheet JFrame
     * @param storageHouse   Management of all storage fields
     */
    public View(String title, OrderManager orderManager, AccountManager accountManager, StorageHouse storageHouse) {

        utf8Decoder = StandardCharsets.UTF_8.newDecoder();

        this.orderManager = orderManager;
        this.accountManager = accountManager;
        this.storageHouse = storageHouse;

        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(900, 600);
        this.storagePanelCollection = new JLabel[9];
        initializeGui();
        setVisible(true);
        setResizable(false);

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
     * Initialize components and add them to JFrame object
     */
    private void initializeGui() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0,0));

        // Adding buttons to menu JPanel
        menuItemGrid = new JPanel(new GridBagLayout());
        newOrderButton = new JButton(Start.toUtf8("Neuer Auftrag"));
        moveStorageButton = new JToggleButton(Start.toUtf8("Umalgern"));
        destroyButton = new JToggleButton(Start.toUtf8("Verschrotten"));
        bilanzButton = new JButton(Start.toUtf8("Bilanz"));
        helpButton = new JButton(Start.toUtf8("Steuerung"));
        questButton = new JButton(Start.toUtf8("Ziel"));

        GridBagConstraints menuConstraints = new GridBagConstraints();
        menuConstraints.fill = GridBagConstraints.BOTH;
        menuConstraints.insets = new Insets(5,0,5,0);
        menuConstraints.gridx = 0;
        menuConstraints.gridy = 0;
        menuItemGrid.add(newOrderButton, menuConstraints);
        menuConstraints.gridy = 1;
        menuItemGrid.add(moveStorageButton, menuConstraints);
        menuConstraints.gridy = 2;
        menuItemGrid.add(destroyButton, menuConstraints);
        menuConstraints.gridy = 3;
        menuItemGrid.add(bilanzButton, menuConstraints);
        menuConstraints.gridy = 4;
        menuItemGrid.add(helpButton, menuConstraints);
        menuConstraints.gridy = 5;
        menuItemGrid.add(questButton, menuConstraints);

        // Adding information labels to informationGrid
        informationGrid = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 20));
        todoLabel = new JLabel(Start.toUtf8(""));
        todoLabel.setFont(HelpDesk.getStandardHeadlineFont());
        cashLabel = new JLabel(Start.toUtf8("0€"));
        cashLabel.setFont(HelpDesk.getStandardHeadlineFont());

        GridBagConstraints informationConstrains = new GridBagConstraints();
        informationConstrains.gridx = 0;
        informationConstrains.gridy = 0;
        informationConstrains.insets = new Insets(0,50,0,50);
        informationGrid.add(todoLabel, informationConstrains);
        informationConstrains.gridx = 2;
        informationGrid.add(cashLabel, informationConstrains);

        // Adding order information elements to grid
        storageGrid = new JPanel(new BorderLayout(0,0));
        orderInformationRootPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,3,3));
        orderLeftView = new JButton(getImage(this.leftArrowPicturePath));
        orderInformationRootPanel.add(orderLeftView);

        GridBagConstraints orderInfoConstrains = new GridBagConstraints();
        orderInfoConstrains.insets = new Insets(0,40,0,40);
        orderInfoConstrains.fill = GridBagConstraints.BOTH;
        orderInfoConstrains.gridx = 0;
        orderInfoConstrains.gridy = 0;
        orderInfoGrid = new JPanel(new GridBagLayout());
        product = new JLabel();
        orderInfoGrid.add(product, orderInfoConstrains);
        orderType = new JLabel();
        orderInfoConstrains.gridy = 1;
        orderInfoGrid.add(orderType, orderInfoConstrains);
        money = new JLabel();
        orderInfoConstrains.gridy = 2;
        orderInfoGrid.add(money, orderInfoConstrains);
        orderInformationRootPanel.add(orderInfoGrid);

        orderRightView = new JButton(getImage(this.rightArrowPicturePath));
        orderInformationRootPanel.add(orderRightView);

        // Adding storage labels to grid
        storageRootPanel = new JPanel(new GridBagLayout());
        initializeStorageLabel(storage0,0,2, storageRootPanel, storage0Id);
        initializeStorageLabel(storage1,1,2, storageRootPanel, storage1Id);
        initializeStorageLabel(storage2,2,2, storageRootPanel, storage2Id);
        initializeStorageLabel(storage3,0,1, storageRootPanel, storage3Id);
        initializeStorageLabel(storage4,1,1, storageRootPanel, storage4Id);
        initializeStorageLabel(storage5,2,1, storageRootPanel, storage5Id);
        initializeStorageLabel(storage6,0,0, storageRootPanel, storage6Id);
        initializeStorageLabel(storage7,1,0, storageRootPanel, storage7Id);
        initializeStorageLabel(storage8,2,0, storageRootPanel, storage8Id);

        storageGrid.add(orderInformationRootPanel, BorderLayout.NORTH);
        storageGrid.add(storageRootPanel, BorderLayout.CENTER);

        panel1.add(menuItemGrid, BorderLayout.WEST);
        panel1.add(informationGrid, BorderLayout.SOUTH);
        panel1.add(storageGrid, BorderLayout.CENTER);
        setContentPane(panel1);

        // Add action listener to GUI elements
        setActionListener();

        updateOrderViewItems();
    }

    /**
     * This method will initialize storage labels
     * @param label JLabel
     * @param xLocation int
     * @param yLocation int
     * @param grid JPanel
     * @param storageArrayIndex in
     * @return JLabel
     */
    private JLabel initializeStorageLabel(JLabel label, int xLocation, int yLocation, JPanel grid, int storageArrayIndex) {
        label = new JLabel();
        label.setHorizontalTextPosition(JLabel.CENTER);

        GridBagConstraints storageLabelConstraints = new GridBagConstraints();
        storageLabelConstraints.gridy = yLocation;
        storageLabelConstraints.gridx = xLocation;
        storageLabelConstraints.insets = new Insets(1,1,1,1);
        grid.add(label, storageLabelConstraints);
        this.storagePanelCollection[storageArrayIndex] = label;
        label.addMouseListener(new StorageHandler(storageArrayIndex, this.orderManager, this.accountManager, this));
        label.setFont(new Font("Arial", Font.CENTER_BASELINE, STORAGE_FONT_SIZE));

        return label;
    }

    /**
     * After Initializing of Swing components will create this method all needed ActionListener
     */
    private void setActionListener() {

        // Creating ActionListener of newButtonOrder
        newOrderButton.addActionListener(e -> {
            loadNewOrder();
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
                destroyButton.setText(Start.toUtf8("Verschrotten"));

                // Set information text to label
                todoLabel.setText(Start.toUtf8(Messages.SELECT_STORAGE_OBJECT_TO_MOVE));

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

    /**
     * Setting user assignment text to todoLabel
     *
     * @param text text
     */
    public void setTodoLabelText(String text) {
        this.todoLabel.setText(Start.toUtf8(text));
    }

    /**
     * Deselecting move toggle button
     */
    public void unSelectMoveButton() {
        this.moveStorageButton.setSelected(false);
    }

    /**
     * Selection move toggle button
     */
    public void setSelectedMoveButton() {
        this.moveStorageButton.setSelected(true);
    }

    /**
     * Loading new order
     */
    public void loadNewOrder() {
        if (orderManager.getActiveOrders().size() >= 3) {
            JOptionPane.showMessageDialog(null,Start.toUtf8("Arbeiten sie erst einen Auftrag ab bevor sie einen neuen ordern"));
            return;
        }

        int answer = JOptionPane.showConfirmDialog(null, Start.toUtf8("<html>Auftrag annhemen mit " +
                        "(Ja) oder Verweigern(Nein). <br>Bei verweigerung wird der Auftragswert als Strafe " +
                        "gerechnet.<br>Der kommende Auftrag geinhaltet dieses Produkt: " +
                        orderManager.showNewOrder().getProductName() + ", " + orderManager.showNewOrder().getAttribute1() + ", " + orderManager.showNewOrder().getAttribute2() + " und bringt " +
                        orderManager.showNewOrder().getCash() + " Euro als "+ orderManager.showNewOrder().getOrderType() +"</html>"),
                "Auftragsannahme", JOptionPane.YES_NO_OPTION);

        if (answer == 1) { // 1 == No
            this.orderPunishment = new Order(Start.toUtf8("Vertragsstrafe"), Start.toUtf8(""),
                                             Start.toUtf8(""), Start.toUtf8(""),
                    Start.toUtf8(Integer.toString(-1 * orderManager.showNewOrder().getCash())));

            accountManager.accountOrder(orderPunishment);
            orderManager.increaseGlobalOrderIndex();
            updateAll();
            return;
        }

        // Loading a new order from file (Leistungsnachweis.csv)
        orderManager.loadNewOrder();

        // Updating all GUI elements
        updateAll();
    }

    /**
     * Returning GameTarget object
     *
     * @return GameTarget
     */
    public GameTarget getGameTarget() {
        return this.gameTarget;
    }

    /**
     * Returns HelpDesk object
     *
     * @return HelpDesk
     */
    public HelpDesk getHelpDesk() {
        return this.helpDesk;
    }

    /**
     * Returns ShortCut object
     *
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

        Timer paintTimer = new Timer(50, e -> {
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
     *
     * @param graphic   Graphic of the Swing element
     * @param imagePath Path of image
     * @param settings  Settings like x, y, width and height
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
     *
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
                    panel.setSize(500, 500);
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
            if (allTopOrders[i] != null) {
                panel.setText(Start.toUtf8(allTopOrders[i].toStringWithoutCash()));
            } else {
                panel.setText(Start.toUtf8("leer"));
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
     *
     * @return boolean
     */
    public boolean isMoveButtonToggled() {
        return this.moveStorageButton.isSelected();
    }

    /**
     * returns the selected id of element to move
     *
     * @return boolean
     */
    public int getSelectedMoveId() {
        return this.selectedMoveId;
    }

    /**
     * This method is the second part of the moving procedure
     *
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
        orderViewButtonAction(View.CURRENT_ORDER);
        setEnableControlOfViewButtons();
        updateCash();
        visualizeStorage();

        if (moveStorageButton.isSelected()) {
            if (getSelectedMoveId() == -1) {
                setTodoLabelText(Start.toUtf8(Messages.SELECT_STORAGE_OBJECT_TO_MOVE));
            } else {
                setTodoLabelText(Start.toUtf8(Messages.SELECT_STORAGE_TARGET_OF_MOVE));
            }
        } else if (destroyButton.isSelected()) {
            setTodoLabelText(Start.toUtf8(Messages.SELECT_STORAGE_TO_DESTROY));
        } else {
            if (orderManager.getCurrentOrder().getOrderType().equals(Order.INCOMING_ORDER_STRING)) {
                setTodoLabelText(Start.toUtf8(Messages.SELECT_STORAGE_TO_STORE));
            } else {
                setTodoLabelText(Start.toUtf8(Messages.SELECT_STORAGE_TO_DELIVER));
            }
        }
    }

    /**
     * Getting the previous of next order from active order list of OrderManager
     *
     * @param prev previous active order id
     */
    public void orderViewButtonAction(int prev) {
        // Selecting the wanted order
        if (prev == View.PREVIOUS_ORDER) {
            if (orderManager.getCurrentOrderIndex() > 0) {
                orderManager.selectPrevOrder();
            }
        } else if (prev == View.NEXT_ORDER) {
            if (orderManager.getCurrentOrderIndex() < orderManager.getCountOfCurrentOrders() - 1) {
                orderManager.selectNextOrder();
            }
        }

        // Updating GUI
        visualizeStorage();
        updateOrderViewItems();
    }

    /**
     * This method create a standard border
     *
     * @param color Color of border
     * @return returns border object
     */
    public static Border getStandardBorder(Color color, boolean dashing) {
        if (!dashing) {
            return BorderFactory.createLineBorder(color, View.BORDER_THICKNESS);
        } else {
            return BorderFactory.createDashedBorder(color, 15, 2, 1, true);
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
            if (selectedMoveId == -1 && moveStorageButton.isSelected()) {
                markSelectableElements();
            } else if (selectedMoveId != -1) {
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
     */
    public void updateCash() {
        this.cashLabel.setText(Start.toUtf8(Integer.toString(accountManager.getWin() - accountManager.getCosts())));
    }

    /**
     * Updating order view in the header area of the game
     */
    private void updateOrderViewItems() {
        if (this.orderManager.hasOrders()) {
            // Load current order
            Order order = orderManager.getCurrentOrder();

            // Updating order Information
            this.product.setText(Start.toUtf8(order.getProductName() + " " + order.getAttribute1() + " " + order.getAttribute2()));
            this.orderType.setText(Start.toUtf8(order.getOrderType()));
            this.money.setText(Start.toUtf8(order.getCash() + " Euro"));

            if (Objects.equals(order.getOrderType(), Order.INCOMING_ORDER_STRING)) {
                 this.todoLabel.setText(Start.toUtf8(Messages.SELECT_STORAGE_TO_STORE));
            } else {
                this.todoLabel.setText(Start.toUtf8(Messages.SELECT_STORAGE_TO_DELIVER));
            }
        } else {
            this.product.setText(Start.toUtf8(""));
            this.orderType.setText(Start.toUtf8("Kein Auftrag"));
            this.money.setText(Start.toUtf8(""));
        }

        // Set new enable status
        setEnableControlOfViewButtons();
    }

    /**
     * Setting enable status of order buttons
     */
    private void setEnableControlOfViewButtons() {
        if (!orderManager.hasOrders()) {
            orderLeftView.setEnabled(false);
            orderRightView.setEnabled(false);
        } else {
            if (orderManager.getCurrentOrderIndex() == 0) {
                orderLeftView.setEnabled(false);
                if (orderManager.getCountOfCurrentOrders() > 1) {
                    orderRightView.setEnabled(true);
                } else {
                    orderRightView.setEnabled(false);
                }
            } else if (orderManager.getCurrentOrderIndex() == orderManager.getCountOfCurrentOrders() - 1) {
                orderRightView.setEnabled(false);
                if (orderManager.getCountOfCurrentOrders() > 1) {
                    orderLeftView.setEnabled(true);
                } else {
                    orderLeftView.setEnabled(false);
                }
            } else {
                orderRightView.setEnabled(true);
                orderLeftView.setEnabled(true);
            }
        }
    }

    /**
     * Returning of storageHouse
     *
     * @return Returns the StorageHouse object
     */
    public StorageHouse getStorageHouse() {
        return this.storageHouse;
    }

    /**
     * Returning of balance sheet
     *
     * @return Returns the complete balance sheet JFrame
     */
    public BalanceSheet getBalanceSheet() {
        return this.balanceSheet;
    }

    /**
     * Returning of destroyButton pressed status
     *
     * @return Status of destroyButton
     */
    public boolean isDestroyButtonPressed() {
        return this.destroyButton.isSelected();
    }

    /**
     * Returning of storage array
     *
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
