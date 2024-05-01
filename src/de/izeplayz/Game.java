package de.izeplayz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import de.izeplayz.api.ApiFunctions;
import de.izeplayz.api.ApiLanguage;
import de.izeplayz.api.ApiLanguage.LANGUAGE;
import de.izeplayz.api.ApiVariables;
import de.izeplayz.api.ApiVariables.CARD_VALUE;
import de.izeplayz.api.ApiVariables.GAME_STATE;
import de.izeplayz.api.ApiVariables.RESULT_TYPE;
import de.izeplayz.guicustom.GuiCustomButton;
import de.izeplayz.guicustom.GuiCustomLabelWithShadow;
import de.izeplayz.guicustom.GuiCustomButton.CustomButtonType;
import de.izeplayz.guicustom.GuiCustomLabelWithShadow.CustomLabelType;

public class Game {
    // GUI
    private JFrame frame;
    private int spacing;
    private int spacingHalf;
    private int defaultSize;
    private int defaultSizeSmaller;
    private int width;
    private int height;
    private int cardWidth;
    private int cardHeight;
    private int itemSizeViewWidth;
    private int itemSizeViewHeight;
    private int yStartTableAndInfo;
    private GAME_STATE currentGameState = GAME_STATE.ROUND_BETTING;
    private int[] lineValues = {16, 8, 6, 4, 3, 2};

    // Images
    private BufferedImage bImgInterfaceCoinAmount;
    private BufferedImage bImgInterfaceStarAmount;
    private BufferedImage bImgInterfaceBetCoinActive;
    private BufferedImage bImgInterfaceBetCoinInactive;
    private BufferedImage bImgInterfaceItemDefault;
    private BufferedImage bImgInterfaceItemStar;
    private BufferedImage bImgInterfaceItemMario;
    private BufferedImage bImgInterfaceItemLuigi;
    private BufferedImage bImgInterfaceItemFireflower;
    private BufferedImage bImgInterfaceItemMushroom;
    private BufferedImage bImgInterfaceItemCloud;
    private BufferedImage bImgCardItemDefault;
    private BufferedImage bImgCardItemStar;
    private BufferedImage bImgCardItemMario;
    private BufferedImage bImgCardItemLuigi;
    private BufferedImage bImgCardItemFireflower;
    private BufferedImage bImgCardItemMushroom;
    private BufferedImage bImgCardItemCloud;

    // Basic
    private GuiCustomButton btnMinimize;
    private GuiCustomButton btnClose;
    private GuiCustomButton btnMusic;
    private GuiCustomButton btnScale;
    private GuiCustomButton btnDifficulty;
    private GuiCustomButton btnLanguage;
    private GuiCustomLabelWithShadow labelVersionPart1;
    private GuiCustomLabelWithShadow labelVersionPart2;

    // Actions
    private GuiCustomButton btnStart;
    private GuiCustomButton btnAction;

    // Current Coins and Stars Display
    private JLabel labelCoinAmountImage;
    private GuiCustomLabelWithShadow labelCoinAmount;
    private JLabel labelStarAmountImage;
    private GuiCustomLabelWithShadow labelStarAmount;

    // Bet
    private GuiCustomButton btnBetPlus;
    private GuiCustomButton btnBetMinus;
    private JLabel[] aLabelsBetCoins;

    // Card Value Info
    private JLabel[] aLabelsInfoItems;
    private JLabel[][] aLabelsTableItems;
    private GuiCustomLabelWithShadow[] aLabelsTableText;

    // Cards
    private GuiCustomButton[] aButtonsCardEnemy;
    private GuiCustomButton[] aButtonsCardPlayer;

    // Result
    private GuiCustomLabelWithShadow labelEnemyResult;
    private String strCurrentEnemyResult = "";
    private GuiCustomLabelWithShadow labelPlayerResult;
    private String strCurrentPlayerResult = "";
    private GuiCustomLabelWithShadow labelResult;
    private String strCurrentResult = "";
    private String strCurrentResultCoins = "";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        ApiFunctions.loadSettings();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Game window = new Game();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Game() {
        initialize();
    }

    private void cardButtonClicked(ActionEvent e) {
        if (currentGameState == GAME_STATE.ROUND_FINISHED) {
            startButtonFinished();
        } else {
            if (currentGameState == GAME_STATE.ROUND_BETTING) {
                startButtonBetting();
            }

            if (currentGameState == GAME_STATE.ROUND_PLAYING) {
                GuiCustomButton btnCard = (GuiCustomButton) e.getSource();
                if (btnCard.isCardPlayer()) {
                    if (btnCard.isCardSelected()) {
                        btnCard.setLocation((int) btnCard.getLocation().getX(), ((int) btnCard.getLocation().getY() + ApiFunctions.scale(50)));
                    } else {
                        btnCard.setLocation((int) btnCard.getLocation().getX(), ((int) btnCard.getLocation().getY() - ApiFunctions.scale(50)));
                    }
                    btnCard.setCardSelected(!btnCard.isCardSelected());
                    updateActionButton();
                }
            }
        }
    }

    private void actionButtonClicked() {
        if (isAnyPlayerCardSelected()) {
            for (GuiCustomButton c : aButtonsCardPlayer) {
                if (c.isCardSelected()) {
                    changePlayerCard(c);
                }
            }
        }
        finishRound();
    }

    private void betButtonClickedPlus() {
        if (currentGameState == GAME_STATE.ROUND_FINISHED) {
            startButtonFinished();
        }
        if (ApiVariables.currentBetValue < 5) {
            if (ApiVariables.coinAmount - ApiVariables.currentBetValue > 0) {
                ApiVariables.currentBetValue++;
                updateBetCoins();
            }
        }
        ApiFunctions.saveSettings();
    }

    private void betButtonClickedMinus() {
        if (currentGameState == GAME_STATE.ROUND_FINISHED) {
            startButtonFinished();
        }
        if (ApiVariables.currentBetValue > 1) {
            ApiVariables.currentBetValue--;
            updateBetCoins();
        }
        ApiFunctions.saveSettings();
    }

    private void startButtonClicked() {
        if (currentGameState == GAME_STATE.ROUND_BETTING) {
            startButtonBetting();
        } else if (currentGameState == GAME_STATE.ROUND_FINISHED) {
            startButtonFinished();
        }
    }

    private void startButtonBetting() {
        btnStart.setEnabled(false);
        btnAction.setEnabled(true);
        ApiVariables.coinAmount -= ApiVariables.currentBetValue;
        updateCoinAmount();
        currentGameState = GAME_STATE.ROUND_PLAYING;
    }

    private void startButtonFinished() {
        labelResult.setText("");
        strCurrentResult = "";
        strCurrentResultCoins = "";
        labelEnemyResult.setText("");
        strCurrentEnemyResult = "";
        labelPlayerResult.setText("");
        strCurrentPlayerResult = "";
        resetCards(true);
        resetCards(false);
        btnStart.setText(ApiLanguage.getText("start"));
        btnAction.setText(ApiLanguage.getText("hold"));
        btnAction.setButtonColors(CustomButtonType.CYAN);
        currentGameState = GAME_STATE.ROUND_BETTING;
    }

    private void scaleBtnClick() {
        double defaultScale = 1.0;
        double[] scaleOptions = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9, 3.0};

        JComboBox<String> jComboBoxScaleOptions = new JComboBox<String>();
        for (int i = 0; i < scaleOptions.length; i++) {
            jComboBoxScaleOptions.addItem(ApiVariables.language.equalsIgnoreCase("EN") ? String.valueOf(scaleOptions[i]) : String.valueOf(scaleOptions[i]).replace(".", ","));
        }

        boolean currentScaleFound = false;
        for (double d : scaleOptions) {
            if (d == ApiVariables.scale) {
                jComboBoxScaleOptions.setSelectedItem(ApiVariables.language.equalsIgnoreCase("EN") ? String.valueOf(d) : String.valueOf(d).replace(".", ","));
                currentScaleFound = true;
                break;
            }
        }

        if (currentScaleFound == false) {
            jComboBoxScaleOptions.setSelectedItem(ApiVariables.language.equalsIgnoreCase("EN") ? String.valueOf(defaultScale) : String.valueOf(defaultScale).replace(".", ","));
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel jSubPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jSubPanel.add(new JLabel(ApiLanguage.getText("popupscalelabel")));
        jSubPanel.add(jComboBoxScaleOptions);
        mainPanel.add(jSubPanel);

        String[] options = {ApiLanguage.getText("popupsaverestart"), ApiLanguage.getText("popupcancel")};

        int result = JOptionPane.showOptionDialog(null, mainPanel, ApiLanguage.getText("popupscaletitle"), JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[options.length - 1]);

        if (result == 0) {

            ApiVariables.scale = ApiVariables.language.equalsIgnoreCase("EN") ? Double.valueOf((String) jComboBoxScaleOptions.getSelectedItem()) : Double.valueOf(((String) jComboBoxScaleOptions.getSelectedItem()).replace(",", "."));
            btnScale.setText(ApiLanguage.getText("scale") + ": " + (ApiVariables.language.equalsIgnoreCase("EN") ? String.valueOf(ApiVariables.scale) : String.valueOf(ApiVariables.scale).replace(".", ",")));
            ApiFunctions.saveSettings();
            try {
                String currentPath = String.valueOf(Game.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toLowerCase();

                if (currentPath.endsWith(".jar")) {
                    final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                    final File currentJar = new File(Game.class.getProtectionDomain().getCodeSource().getLocation().toURI());

                    if (!currentJar.getName().endsWith(".jar"))
                        return;

                    final ArrayList<String> command = new ArrayList<String>();
                    command.add(javaBin);
                    command.add("-jar");
                    command.add(currentJar.getPath());

                    final ProcessBuilder builder = new ProcessBuilder(command);
                    builder.start();
                    ApiFunctions.exit();
                } else {
                    ApiFunctions.showMsgBox("THE PROGRAM WAS STARTED INSIDE A JAVA-IDE!\nRESTARTING IS NOT POSSIBLE INSIDE A JAVA-IDE!\n\nTHE PROGRAM WILL JUST BE CLOSED NOW!\n", result);
                    ApiFunctions.exit();
                }
            } catch (Exception e) {
                ApiFunctions.crash("FAILED TO RESTART GAME");
            }
        }
    }

    private void difficultyBtnClick() {
        JSpinner jSelectDifficulty = new JSpinner(new SpinnerNumberModel(ApiVariables.difficulty, 1, 999, 1));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel jSubPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jSubPanel.add(new JLabel(ApiLanguage.getText("popupdifficultylabel")));
        jSubPanel.add(jSelectDifficulty);
        mainPanel.add(jSubPanel);

        String[] options = {ApiLanguage.getText("popupsave"), ApiLanguage.getText("popupcancel")};

        int result = JOptionPane.showOptionDialog(null, mainPanel, ApiLanguage.getText("popupdifficultytitle"), JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[options.length - 1]);

        if (result == 0) {
            ApiVariables.difficulty = (int) jSelectDifficulty.getValue();
            btnDifficulty.setText(ApiLanguage.getText("difficulty") + ": " + ApiVariables.difficulty);
            ApiFunctions.saveSettings();
        }
    }

    private void languageBtnClick() {
        LANGUAGE languageBefore = ApiLanguage.getLanguage(ApiVariables.language);
        if (ApiVariables.language.equalsIgnoreCase("EN")) {
            ApiVariables.language = "DE";
        } else if (ApiVariables.language.equalsIgnoreCase("DE")) {
            ApiVariables.language = "EN";
        } else {
            ApiFunctions.crash("INVALID LANGUAGE!");
            return;
        }

        btnScale.setText(ApiLanguage.getText("scale") + ": " + (ApiVariables.language.equalsIgnoreCase("EN") ? String.valueOf(ApiVariables.scale) : String.valueOf(ApiVariables.scale).replace(".", ",")));
        btnLanguage.setText(ApiLanguage.getText("language"));
        btnMusic.setText(ApiLanguage.getText("music") + ": " + (ApiVariables.music == true ? ApiLanguage.getText("on") : ApiLanguage.getText("off")));
        btnDifficulty.setText(ApiLanguage.getText("difficulty") + ": " + ApiVariables.difficulty);
        btnBetPlus.setText(ApiLanguage.getText("betmore"));
        btnBetMinus.setText(ApiLanguage.getText("betless"));

        if (btnStart.getText().equals(ApiLanguage.getText("next", languageBefore))) {
            btnStart.setText(ApiLanguage.getText("next"));
        } else if (btnStart.getText().equals(ApiLanguage.getText("start", languageBefore))) {
            btnStart.setText(ApiLanguage.getText("start"));
        } else {
            ApiFunctions.crash("INVALID LANGUAGE");
        }

        if (btnAction.getText().equals(ApiLanguage.getText("hold", languageBefore))) {
            btnAction.setText(ApiLanguage.getText("hold"));
        } else if (btnAction.getText().equals(ApiLanguage.getText("swap", languageBefore))) {
            btnAction.setText(ApiLanguage.getText("swap"));
        } else {
            ApiFunctions.crash("INVALID LANGUAGE");
        }

        if (strCurrentEnemyResult.equals(ApiLanguage.getText("5same", languageBefore))) {
            labelEnemyResult.setText(ApiLanguage.getText("5same"));
            strCurrentEnemyResult = ApiLanguage.getText("5same");
        } else if (strCurrentEnemyResult.equals(ApiLanguage.getText("4same", languageBefore))) {
            labelEnemyResult.setText(ApiLanguage.getText("4same"));
            strCurrentEnemyResult = ApiLanguage.getText("4same");
        } else if (strCurrentEnemyResult.equals(ApiLanguage.getText("fullhouse", languageBefore))) {
            labelEnemyResult.setText(ApiLanguage.getText("fullhouse"));
            strCurrentEnemyResult = ApiLanguage.getText("fullhouse");
        } else if (strCurrentEnemyResult.equals(ApiLanguage.getText("3same", languageBefore))) {
            labelEnemyResult.setText(ApiLanguage.getText("3same"));
            strCurrentEnemyResult = ApiLanguage.getText("3same");
        } else if (strCurrentEnemyResult.equals(ApiLanguage.getText("2pairs", languageBefore))) {
            labelEnemyResult.setText(ApiLanguage.getText("2pairs"));
            strCurrentEnemyResult = ApiLanguage.getText("2pairs");
        } else if (strCurrentEnemyResult.equals(ApiLanguage.getText("1pair", languageBefore))) {
            labelEnemyResult.setText(ApiLanguage.getText("1pair"));
            strCurrentEnemyResult = ApiLanguage.getText("1pair");
        } else if (strCurrentEnemyResult.equals(ApiLanguage.getText("nothing", languageBefore))) {
            labelEnemyResult.setText(ApiLanguage.getText("nothing"));
            strCurrentEnemyResult = ApiLanguage.getText("nothing");
        }

        if (strCurrentPlayerResult.equals(ApiLanguage.getText("5same", languageBefore))) {
            labelPlayerResult.setText(ApiLanguage.getText("5same"));
            strCurrentPlayerResult = ApiLanguage.getText("5same");
        } else if (strCurrentPlayerResult.equals(ApiLanguage.getText("4same", languageBefore))) {
            labelPlayerResult.setText(ApiLanguage.getText("4same"));
            strCurrentPlayerResult = ApiLanguage.getText("4same");
        } else if (strCurrentPlayerResult.equals(ApiLanguage.getText("fullhouse", languageBefore))) {
            labelPlayerResult.setText(ApiLanguage.getText("fullhouse"));
            strCurrentPlayerResult = ApiLanguage.getText("fullhouse");
        } else if (strCurrentPlayerResult.equals(ApiLanguage.getText("3same", languageBefore))) {
            labelPlayerResult.setText(ApiLanguage.getText("3same"));
            strCurrentPlayerResult = ApiLanguage.getText("3same");
        } else if (strCurrentPlayerResult.equals(ApiLanguage.getText("2pairs", languageBefore))) {
            labelPlayerResult.setText(ApiLanguage.getText("2pairs"));
            strCurrentPlayerResult = ApiLanguage.getText("2pairs");
        } else if (strCurrentPlayerResult.equals(ApiLanguage.getText("1pair", languageBefore))) {
            labelPlayerResult.setText(ApiLanguage.getText("1pair"));
            strCurrentPlayerResult = ApiLanguage.getText("1pair");
        } else if (strCurrentPlayerResult.equals(ApiLanguage.getText("nothing", languageBefore))) {
            labelPlayerResult.setText(ApiLanguage.getText("nothing"));
            strCurrentPlayerResult = ApiLanguage.getText("nothing");
        }

        if (strCurrentResult.equals(ApiLanguage.getText("gameover", languageBefore))) {
            labelResult.setText(ApiLanguage.getText("gameover"));
            strCurrentResult = ApiLanguage.getText("gameover");
        } else if (strCurrentResult.equals(ApiLanguage.getText("won", languageBefore))) {
            labelResult.setText(ApiLanguage.getText("won") + strCurrentResultCoins);
            strCurrentResult = ApiLanguage.getText("won");
        } else if (strCurrentResult.equals(ApiLanguage.getText("lost", languageBefore))) {
            labelResult.setText(ApiLanguage.getText("lost") + strCurrentResultCoins);
            strCurrentResult = ApiLanguage.getText("lost");
        } else if (strCurrentResult.equals(ApiLanguage.getText("draw", languageBefore))) {
            labelResult.setText(ApiLanguage.getText("draw"));
            strCurrentResult = ApiLanguage.getText("draw");
        }

        ApiFunctions.saveSettings();
    }

    private void musicBtnClick() {
        ApiVariables.music = !ApiVariables.music;
        ApiFunctions.toggleMusic(ApiVariables.music);
        btnMusic.setText(ApiLanguage.getText("music") + ": " + (ApiVariables.music == true ? ApiLanguage.getText("on") : ApiLanguage.getText("off")));
        ApiFunctions.saveSettings();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        spacing = ApiFunctions.scale(16);
        spacingHalf = spacing / 2;
        defaultSize = ApiFunctions.scale(80);
        defaultSizeSmaller = defaultSize / 4 * 3;
        width = ApiFunctions.scale(1900);
        height = ApiFunctions.scale(1060);
        cardHeight = ApiFunctions.scale(250);
        cardWidth = ApiFunctions.scale(150);
        itemSizeViewWidth = ApiFunctions.scale(48);
        itemSizeViewHeight = itemSizeViewWidth;

        yStartTableAndInfo = (defaultSize / 2) + (spacing / 2) + (defaultSize / 2) + spacing + spacing;

        frame = new JFrame(ApiVariables.titleComplete);
        frame.setBounds(0, 0, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, ApiFunctions.scale(50), ApiFunctions.scale(50)));
        frame.setLocationRelativeTo(null);

        FrameDragListener frameDragListener = new FrameDragListener(frame);
        frame.addMouseListener(frameDragListener);
        frame.addMouseMotionListener(frameDragListener);

        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));

        try {
            frame.setIconImage(ImageIO.read(ApiFunctions.getResourcesFile("program/icon.png")));
        } catch (Exception e) {
            ApiFunctions.crash("Program icon could not be loaded!");
        }

        try {
            BufferedImage buffImgBackground = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/background.png")), width, height);
            frame.setContentPane(new JLabel(new ImageIcon(buffImgBackground)));
        } catch (Exception e) {
            ApiFunctions.crash("Background image could not be loaded!");
        }

        ApiFunctions.toggleMusic(ApiVariables.music);

        try {
            bImgInterfaceCoinAmount = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/coin_glow.png")), defaultSizeSmaller, defaultSizeSmaller);
            bImgInterfaceStarAmount = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/star_glow.png")), defaultSizeSmaller, defaultSizeSmaller);

            bImgInterfaceBetCoinActive = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/coin_active.png")), defaultSizeSmaller, defaultSizeSmaller);
            bImgInterfaceBetCoinInactive = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/coin_inactive_transparent_50.png")), defaultSizeSmaller, defaultSizeSmaller);

            bImgInterfaceItemDefault = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_default_transparent_25_bw.png")), itemSizeViewWidth, itemSizeViewHeight);
            bImgInterfaceItemStar = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_star.png")), itemSizeViewWidth, itemSizeViewHeight);
            bImgInterfaceItemMario = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_mario.png")), itemSizeViewWidth, itemSizeViewHeight);
            bImgInterfaceItemLuigi = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_luigi.png")), itemSizeViewWidth, itemSizeViewHeight);
            bImgInterfaceItemFireflower = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_fireflower.png")), itemSizeViewWidth, itemSizeViewHeight);
            bImgInterfaceItemMushroom = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_mushroom.png")), itemSizeViewWidth, itemSizeViewHeight);
            bImgInterfaceItemCloud = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_cloud.png")), itemSizeViewWidth, itemSizeViewHeight);

            bImgCardItemDefault = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_default_transparent_25_bw.png")), cardWidth - spacing, cardWidth - spacing);
            bImgCardItemStar = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_star.png")), cardWidth - spacing, cardWidth - spacing);
            bImgCardItemMario = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_mario.png")), cardWidth - spacing, cardWidth - spacing);
            bImgCardItemLuigi = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_luigi.png")), cardWidth - spacing, cardWidth - spacing);
            bImgCardItemFireflower = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_fireflower.png")), cardWidth - spacing, cardWidth - spacing);
            bImgCardItemMushroom = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_mushroom.png")), cardWidth - spacing, cardWidth - spacing);
            bImgCardItemCloud = ApiFunctions.resizeImage(ImageIO.read(ApiFunctions.getResourcesFile("gui/cards/card_cloud.png")), cardWidth - spacing, cardWidth - spacing);
        } catch (Exception e) {
            ApiFunctions.crash("GUI Images could not be loaded!");
        }

        setupGame();
    }

    class FrameDragListener extends MouseAdapter {

        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
            frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, ApiFunctions.scale(40), ApiFunctions.scale(40)));
        }

        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }

    private ImageIcon getCoinImage(boolean active) {
        return active ? new ImageIcon(bImgInterfaceBetCoinActive) : new ImageIcon(bImgInterfaceBetCoinInactive);
    }

    private void setupGame() {
        int labelVersionStartY = (yStartTableAndInfo + spacing + defaultSizeSmaller + spacing + defaultSizeSmaller + spacing + spacing + spacing + spacing) + (((itemSizeViewHeight + spacing) * 6) + spacing) + spacingHalf;

        labelVersionPart1 = createCustomLabel(ApiVariables.titlePart1, CustomLabelType.GREY_DARK, ApiFunctions.getFontBig(), 0, labelVersionStartY, ApiFunctions.scale(650) + spacing, defaultSize, false);
        frame.getContentPane().add(labelVersionPart1);

        labelVersionPart2 = createCustomLabel(ApiVariables.titlePart2, CustomLabelType.GREY_DARK, ApiFunctions.getFontBig(), 0, labelVersionStartY + (defaultSize / 5 * 2), ApiFunctions.scale(650) + spacing, defaultSize, false);
        frame.getContentPane().add(labelVersionPart2);

        aButtonsCardEnemy = new GuiCustomButton[5];
        for (int i = 0; i < aButtonsCardEnemy.length; i++) {
            aButtonsCardEnemy[i] = createCardButton(width - (spacing + cardWidth) * (5 - i), spacing, cardWidth, cardHeight, false, (i + 1));
            frame.getContentPane().add(aButtonsCardEnemy[i]);
        }

        aButtonsCardPlayer = new GuiCustomButton[5];
        for (int i = 0; i < aButtonsCardPlayer.length; i++) {
            aButtonsCardPlayer[i] = createCardButton(width - (spacing + cardWidth) * (5 - i), height - spacing - cardHeight, cardWidth, cardHeight, true, (i + 1));
            frame.getContentPane().add(aButtonsCardPlayer[i]);
        }

        labelCoinAmountImage = new JLabel(new ImageIcon(bImgInterfaceCoinAmount));
        labelCoinAmountImage.setBounds(spacing + spacing, yStartTableAndInfo + spacing + spacing, defaultSizeSmaller, defaultSizeSmaller);
        frame.getContentPane().add(labelCoinAmountImage);
        labelCoinAmount = createCustomLabel(getCoinAmountText(), CustomLabelType.GREY_BRIGHT, ApiFunctions.getFont((float) defaultSizeSmaller), spacing + defaultSizeSmaller + (spacing / 2) + spacing,
                yStartTableAndInfo + (spacing / 2) + spacing - ApiFunctions.scale(4), ApiFunctions.scale(600), defaultSize, false);
        frame.getContentPane().add(labelCoinAmount);

        labelStarAmountImage = new JLabel(new ImageIcon(bImgInterfaceStarAmount));
        labelStarAmountImage.setBounds(spacing + spacing, yStartTableAndInfo + spacing + defaultSizeSmaller + spacing + spacing, defaultSizeSmaller, defaultSizeSmaller);
        frame.getContentPane().add(labelStarAmountImage);
        labelStarAmount = createCustomLabel(getStarAmountText(), CustomLabelType.GREY_BRIGHT, ApiFunctions.getFont((float) defaultSizeSmaller), spacing + defaultSizeSmaller + (spacing / 2) + spacing,
                yStartTableAndInfo + (spacing / 2) + defaultSizeSmaller + spacing + spacing - ApiFunctions.scale(4), ApiFunctions.scale(600), defaultSize, false);
        frame.getContentPane().add(labelStarAmount);

        Color c = new Color(0, 0, 0, 128);
        JPanel panel = new JPanel();
        panel.setBounds(spacing, yStartTableAndInfo + spacing, ApiFunctions.scale(650), spacing + defaultSizeSmaller + spacing + defaultSizeSmaller + spacing);
        panel.setBackground(c);
        panel.setForeground(c);
        frame.getContentPane().add(panel);

        drawInfo();
        drawTable();

        btnStart = createNormalButton(ApiLanguage.getText("start"), ApiFunctions.getFontGiant(), CustomButtonType.GREEN_BRIGHT, width - ((spacing + cardWidth) * 5) - spacing - ApiFunctions.scale(320) - (spacing * 2),
                height - spacing - cardHeight, ApiFunctions.scale(320), (cardHeight - spacing) / 2, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startButtonClicked();
                    }
                });
        frame.getContentPane().add(btnStart);

        btnAction = createNormalButton(ApiLanguage.getText("hold"), ApiFunctions.getFontGiant(), CustomButtonType.CYAN, width - ((spacing + cardWidth) * 5) - spacing - ApiFunctions.scale(320) - (spacing * 2),
                height - spacing - cardHeight + ((cardHeight - spacing) / 2) + spacing, ApiFunctions.scale(320), (cardHeight - spacing) / 2, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionButtonClicked();
                    }
                });
        btnAction.setEnabled(false);
        frame.getContentPane().add(btnAction);

        btnBetPlus = createNormalButton(ApiLanguage.getText("betmore"), ApiFunctions.getFontHuge(), CustomButtonType.PINK, spacing, height - defaultSize - spacing, ApiFunctions.scale(280), defaultSize, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betButtonClickedPlus();
            }
        });
        frame.getContentPane().add(btnBetPlus);

        btnBetMinus = createNormalButton(ApiLanguage.getText("betless"), ApiFunctions.getFontHuge(), CustomButtonType.PINK, spacing + ApiFunctions.scale(280) + spacing, height - defaultSize - spacing, ApiFunctions.scale(280), defaultSize,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        betButtonClickedMinus();
                    }
                });
        frame.getContentPane().add(btnBetMinus);

        setupButtonsTools();

        aLabelsBetCoins = new JLabel[5];
        for (int i = 0; i < aLabelsBetCoins.length; i++) {
            aLabelsBetCoins[i] = getCoinLabel((i + 1), false);
            frame.getContentPane().add(aLabelsBetCoins[i]);
        }
        updateBetCoins();

        labelEnemyResult = createCustomLabel("", CustomLabelType.BRIGHT_RED, ApiFunctions.getFont((float) defaultSize / 2), width - ((spacing + cardWidth) * 5), cardHeight, (spacing + cardWidth) * 5, defaultSize, true);
        strCurrentEnemyResult = "";
        frame.getContentPane().add(labelEnemyResult);

        labelPlayerResult = createCustomLabel("", CustomLabelType.BRIGHT_BLUE, ApiFunctions.getFont((float) defaultSize / 2), width - ((spacing + cardWidth) * 5), height - spacing - cardHeight - defaultSize, (spacing + cardWidth) * 5,
                defaultSize, true);
        strCurrentPlayerResult = "";
        frame.getContentPane().add(labelPlayerResult);

        int yResult = cardHeight + ((height - (spacing + cardHeight + spacing) - (spacing + cardHeight + spacing)) / 2);
        labelResult = createCustomLabel("", CustomLabelType.YELLOW, ApiFunctions.getFont((float) defaultSize), width - ((spacing + cardWidth) * 5), yResult, (spacing + cardWidth) * 5, defaultSize, true);
        strCurrentResult = "";
        strCurrentResultCoins = "";
        frame.getContentPane().add(labelResult);
    }

    private void setupButtonsTools() {
        int btnWidth = (ApiFunctions.scale(650) - (defaultSize / 2) - spacing - spacing - (spacing / 2)) / 2;
        int btnWidthSmall = btnWidth - (defaultSize / 2);
        int btnWidthBig = btnWidth + (defaultSize / 2);

        btnClose = createNormalButton("X", ApiFunctions.getFontBig(), CustomButtonType.RED, spacing, spacing, defaultSize / 2, defaultSize / 2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApiFunctions.exit();
            }
        });
        frame.getContentPane().add(btnClose);

        btnMinimize = createNormalButton("_", ApiFunctions.getFontBig(), CustomButtonType.YELLOW, spacing, spacing + (defaultSize / 2) + (spacing / 2), defaultSize / 2, defaultSize / 2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(Frame.ICONIFIED);
            }
        });
        frame.getContentPane().add(btnMinimize);

        btnScale = createNormalButton(ApiLanguage.getText("scale") + ": " + (ApiVariables.language.equalsIgnoreCase("EN") ? String.valueOf(ApiVariables.scale) : String.valueOf(ApiVariables.scale).replace(".", ",")),
                ApiFunctions.getFontBig(), CustomButtonType.BLUE, spacing + spacing + (defaultSize / 2) + spacing, spacing, btnWidthSmall, defaultSize / 2, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        scaleBtnClick();
                    }
                });
        frame.getContentPane().add(btnScale);

        btnLanguage = createNormalButton(ApiLanguage.getText("language"), ApiFunctions.getFontBig(), CustomButtonType.BLUE, spacing + spacing + (defaultSize / 2) + spacing + btnWidthSmall + (spacing / 2), spacing, btnWidthBig,
                defaultSize / 2, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        languageBtnClick();
                    }
                });
        frame.getContentPane().add(btnLanguage);

        btnMusic = createNormalButton(ApiLanguage.getText("music") + ": " + (ApiVariables.music == true ? ApiLanguage.getText("on") : ApiLanguage.getText("off")), ApiFunctions.getFontBig(), CustomButtonType.BLUE,
                spacing + spacing + (defaultSize / 2) + spacing, spacing + (defaultSize / 2) + (spacing / 2), btnWidthSmall, defaultSize / 2, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        musicBtnClick();
                    }
                });
        frame.getContentPane().add(btnMusic);

        btnDifficulty = createNormalButton(ApiLanguage.getText("difficulty") + ": " + ApiVariables.difficulty, ApiFunctions.getFontBig(), CustomButtonType.BLUE,
                spacing + spacing + (defaultSize / 2) + spacing + btnWidthSmall + (spacing / 2), spacing + (defaultSize / 2) + (spacing / 2), btnWidthBig, defaultSize / 2, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        difficultyBtnClick();
                    }
                });
        frame.getContentPane().add(btnDifficulty);

    }

    private void drawInfo() {
        aLabelsInfoItems = new JLabel[6];
        CARD_VALUE[] cardValues = {CARD_VALUE.STAR, CARD_VALUE.MARIO, CARD_VALUE.LUIGI, CARD_VALUE.FIREFLOWER, CARD_VALUE.MUSHROOM, CARD_VALUE.CLOUD};

        for (int i = 0; i < aLabelsInfoItems.length; i++) {
            aLabelsInfoItems[i] = getInfoItem((i + 1), cardValues[i]);
            frame.getContentPane().add(aLabelsInfoItems[i]);
        }

        Color c = new Color(0, 0, 0, 96);
        JPanel panel = new JPanel();
        panel.setBounds(spacing, yStartTableAndInfo + spacing + defaultSizeSmaller + spacing + defaultSizeSmaller + spacing + spacing + spacing + spacing + spacing, spacing + itemSizeViewWidth + spacing,
                ((itemSizeViewHeight + spacing) * 6) + spacing);
        panel.setBackground(c);
        panel.setForeground(c);
        frame.getContentPane().add(panel);
    }

    private void drawTable() {
        CARD_VALUE matrixLine1[] = {CARD_VALUE.STAR, CARD_VALUE.LUIGI, CARD_VALUE.STAR, CARD_VALUE.STAR, CARD_VALUE.STAR};
        CARD_VALUE matrixLine2[] = {CARD_VALUE.STAR, CARD_VALUE.STAR, CARD_VALUE.STAR, CARD_VALUE.STAR, CARD_VALUE.DEFAULT};
        CARD_VALUE matrixLine3[] = {CARD_VALUE.STAR, CARD_VALUE.STAR, CARD_VALUE.STAR, CARD_VALUE.MUSHROOM, CARD_VALUE.MUSHROOM};
        CARD_VALUE matrixLine4[] = {CARD_VALUE.STAR, CARD_VALUE.STAR, CARD_VALUE.STAR, CARD_VALUE.DEFAULT, CARD_VALUE.DEFAULT};
        CARD_VALUE matrixLine5[] = {CARD_VALUE.STAR, CARD_VALUE.STAR, CARD_VALUE.MUSHROOM, CARD_VALUE.MUSHROOM, CARD_VALUE.DEFAULT};
        CARD_VALUE matrixLine6[] = {CARD_VALUE.STAR, CARD_VALUE.STAR, CARD_VALUE.DEFAULT, CARD_VALUE.DEFAULT, CARD_VALUE.DEFAULT};
        CARD_VALUE matrix[][] = {matrixLine1, matrixLine2, matrixLine3, matrixLine4, matrixLine5, matrixLine6};

        int amountLines = 6;
        int amountItems = 5;
        aLabelsTableItems = new JLabel[amountLines][amountItems];
        aLabelsTableText = new GuiCustomLabelWithShadow[amountLines];

        for (int lineNumber = 0; lineNumber < amountLines; lineNumber++) {
            for (int itemNumber = 0; itemNumber < amountItems; itemNumber++) {
                aLabelsTableItems[lineNumber][itemNumber] = getTableItem((lineNumber + 1), (itemNumber + 1), matrix[lineNumber][itemNumber]);
                frame.getContentPane().add(aLabelsTableItems[lineNumber][itemNumber]);
            }
            aLabelsTableText[lineNumber] = getTableItemText((lineNumber + 1), lineValues[lineNumber]);
            frame.getContentPane().add(aLabelsTableText[lineNumber]);
        }

        Color c = new Color(0, 0, 0, 96);
        JPanel panel = new JPanel();
        panel.setBounds((spacing + itemSizeViewWidth + spacing) + spacing + spacing + (spacing / 2), yStartTableAndInfo + spacing + defaultSizeSmaller + spacing + defaultSizeSmaller + spacing + spacing + spacing + spacing + spacing,
                ((itemSizeViewWidth + spacingHalf) * 5) + spacing + spacing + itemSizeViewWidth + itemSizeViewWidth, ((itemSizeViewHeight + spacing) * 6) + spacing);
        panel.setBackground(c);
        panel.setForeground(c);
        frame.getContentPane().add(panel);
    }

    private JLabel getInfoItem(int lineNumber, CARD_VALUE cardValue) {
        int tableItemStartX = spacing + spacing;
        int tableItemStartY = yStartTableAndInfo + spacing + defaultSizeSmaller + spacing + defaultSizeSmaller + spacing + spacing + spacing + spacing + spacing + spacing;

        JLabel label = new JLabel(new ImageIcon(getTableOrInfoImage(cardValue)));
        label.setBounds(tableItemStartX, tableItemStartY + ((lineNumber - 1) * (itemSizeViewHeight + spacing)), itemSizeViewWidth, itemSizeViewHeight);
        return label;
    }

    private JLabel getTableItem(int lineNumber, int itemNumber, CARD_VALUE cardValue) {
        int tableItemStartX = (spacing + itemSizeViewWidth + spacing) + spacing + spacing + spacing + (spacing / 2);
        int tableItemStartY = yStartTableAndInfo + spacing + defaultSizeSmaller + spacing + defaultSizeSmaller + spacing + spacing + spacing + spacing + spacing + spacing;

        JLabel label = new JLabel(new ImageIcon(getTableOrInfoImage(cardValue)));
        label.setBounds(tableItemStartX + ((itemNumber - 1) * (itemSizeViewWidth + spacingHalf)), tableItemStartY + ((lineNumber - 1) * (itemSizeViewHeight + spacing)), itemSizeViewWidth, itemSizeViewHeight);
        return label;
    }

    private GuiCustomLabelWithShadow getTableItemText(int lineNumber, int value) {
        int tableTextStartX = (spacing + itemSizeViewWidth + spacing) + spacing + spacing + spacing + ((6 - 1) * (itemSizeViewWidth + spacingHalf)) + spacing + (spacing / 2);
        int tableTextStartY = yStartTableAndInfo + spacing + defaultSizeSmaller + spacing + defaultSizeSmaller + spacing + spacing + spacing + spacing + ((lineNumber - 1) * (itemSizeViewHeight + spacing)) + (spacing / 2);

        GuiCustomLabelWithShadow label = new GuiCustomLabelWithShadow(String.valueOf(value * ApiVariables.currentBetValue), CustomLabelType.RED, ApiFunctions.getFont((float) defaultSize / 2), tableTextStartX, tableTextStartY,
                itemSizeViewWidth * 3, itemSizeViewHeight * 2, false);
        return label;
    }


    public GuiCustomLabelWithShadow createCustomLabel(String text, CustomLabelType customLabelType, Font font, int x, int y, int width, int height, boolean center) {
        GuiCustomLabelWithShadow label = new GuiCustomLabelWithShadow(text, customLabelType, font, x, y, width, height, center);
        return label;
    }

    private void updateCoinAmount() {
        labelCoinAmount.setText(getCoinAmountText());
    }

    private String getCoinAmountText() {
        return "X " + ApiVariables.coinAmount;
    }

    private void updateStarAmount() {
        labelStarAmount.setText(getStarAmountText());
    }

    private String getStarAmountText() {
        return "X " + ApiVariables.starAmount;
    }

    private void updateBetCoins() {
        ImageIcon tempCoinActive = getCoinImage(true);
        ImageIcon tempCoinInactive = getCoinImage(false);

        int activeLeft = ApiVariables.currentBetValue;
        for (int i = 0; i < aLabelsBetCoins.length; i++) {
            aLabelsBetCoins[i].setIcon((activeLeft > 0) ? tempCoinActive : tempCoinInactive);
            activeLeft--;
        }
        updateTableTextValues();
    }

    private void updateTableTextValues() {
        for (int i = 0; i < aLabelsTableText.length; i++) {
            aLabelsTableText[i].setText(String.valueOf(lineValues[i] * ApiVariables.currentBetValue));
        }
    }

    private JLabel getCoinLabel(int coinNumber, boolean active) {
        JLabel label = new JLabel(getCoinImage(active));
        label.setBounds(spacing + ((defaultSizeSmaller) * (coinNumber - 1)), height - spacing - defaultSize - (spacing / 2) - defaultSize, defaultSizeSmaller, defaultSize);
        return label;
    }

    private GuiCustomButton createCardButton(int x, int y, int width, int height, boolean isPlayer, int cardNumber) {
        GuiCustomButton button = new GuiCustomButton("", isPlayer ? CustomButtonType.BRIGHT_BLUE : CustomButtonType.BRIGHT_RED);

        button.setCardPlayer(isPlayer);
        button.setCardSelected(false);
        button.setCardValue(ApiFunctions.getRandomNumber(1, 6));

        button.setIcon(isPlayer ? getCardImage(getCardValueFromInt(button.getCardValue())) : getCardImage(CARD_VALUE.DEFAULT));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setOpaque(false);
        button.setBounds(x, y, width, height);
        button.setFont(ApiFunctions.getFontGiant());
        if (isPlayer) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardButtonClicked(e);
                }
            });
        }
        return button;
    }

    private GuiCustomButton createNormalButton(String text, Font font, CustomButtonType customButtonType, int x, int y, int width, int height, ActionListener onClick) {
        GuiCustomButton button = new GuiCustomButton(text, customButtonType);

        button.setBorder(BorderFactory.createEmptyBorder());
        button.setOpaque(false);
        button.setBounds(x, y, width, height);
        button.setFont(font);
        button.addActionListener(onClick);
        return button;
    }

    private void updateActionButton() {
        btnAction.setText(isAnyPlayerCardSelected() ? ApiLanguage.getText("swap") : ApiLanguage.getText("hold"));
        btnAction.setButtonColors(isAnyPlayerCardSelected() ? CustomButtonType.CYAN2 : CustomButtonType.CYAN);
    }

    private boolean isAnyPlayerCardSelected() {
        for (GuiCustomButton c : aButtonsCardPlayer) {
            if (c.isCardSelected()) {
                return true;
            }
        }

        return false;
    }

    private void closeCurrentRound() {
        ApiVariables.statsAmountRoundsPlayed++;

        btnBetMinus.setEnabled(true);
        btnBetPlus.setEnabled(true);
        btnStart.setEnabled(true);
        btnAction.setEnabled(false);

        if (ApiVariables.currentBetValue > ApiVariables.coinAmount) {
            ApiVariables.currentBetValue = ApiVariables.coinAmount;
            updateBetCoins();
        }

        ApiFunctions.saveSettings();

        btnStart.setText(ApiLanguage.getText("next"));
        currentGameState = GAME_STATE.ROUND_FINISHED;
    }

    class CardCalc {
        public int value;
        public int amount;

        public CardCalc(int value, int amount) {
            this.value = value;
            this.amount = amount;
        }
    }

    private int calcResult(boolean isPlayer) {
        CardCalc[] allCards = new CardCalc[6];
        allCards[0] = new CardCalc(1, 0); // cloud
        allCards[1] = new CardCalc(2, 0); // mushroom
        allCards[2] = new CardCalc(3, 0); // fireflower
        allCards[3] = new CardCalc(4, 0); // luigi
        allCards[4] = new CardCalc(5, 0); // mario
        allCards[5] = new CardCalc(6, 0); // star

        for (int i = 0; i < 5; i++) {
            allCards[(6 - ((isPlayer ? aButtonsCardPlayer[i] : aButtonsCardEnemy[i]).getCardValue()))].amount++;
        }

        int score = 0;
        boolean boolean3SameFound = false;
        int intAmountPairs = 0;

        for (CardCalc cc : allCards) {
            if (cc.amount == 5) {
                return cc.value * ApiVariables.multiplicator5x;
            } else if (cc.amount == 4) {
                score += cc.value * ApiVariables.multiplicator4x;
                cc.amount = 0;
                break;
            } else if (cc.amount == 3) {
                boolean3SameFound = true;
            } else if (cc.amount == 2) {
                intAmountPairs++;
            }
        }

        if (boolean3SameFound) {
            for (CardCalc cc : allCards) {
                if (cc.amount == 3) {
                    score += cc.value * (intAmountPairs == 1 ? ApiVariables.multiplicatorFH : ApiVariables.multiplicator3x);
                    cc.amount = 0;
                    break;
                }
            }
        }

        for (CardCalc cc : allCards) {
            if (cc.amount == 2) {
                score += cc.value * (boolean3SameFound ? 1 : (intAmountPairs > 1 ? ApiVariables.multiplicator2x2x : ApiVariables.multiplicator2x));
                cc.amount = 0;
            }
        }

        for (CardCalc cc : allCards) {
            score += cc.value * cc.amount;
        }

        return score;
    }

    private void changePlayerCard(GuiCustomButton btn) {
        btn.setCardValue(ApiFunctions.getRandomNumber(1, 6));
        btn.setIcon(getCardImage(getCardValueFromInt(btn.getCardValue())));
        btn.setLocation((int) btn.getLocation().getX(), ((int) btn.getLocation().getY() + ApiFunctions.scale(50)));
        btn.setCardSelected(false);
    }

    private void resetCards(boolean isPlayer) {
        for (int i = 0; i < 5; i++) {
            newCard((isPlayer ? aButtonsCardPlayer[i] : aButtonsCardEnemy[i]), isPlayer);
        }
    }

    private void newCard(GuiCustomButton btn, boolean isPlayer) {
        btn.setCardValue(ApiFunctions.getRandomNumber(1, 6));
        btn.setIcon(getCardImage(isPlayer ? getCardValueFromInt(btn.getCardValue()) : CARD_VALUE.DEFAULT));
        btn.setCardSelected(false);
    }


    private void finishRound() {
        if (ApiVariables.difficulty > 1) {
            int bestResultInt = calcResult(false);
            int[] bestResultCards = new int[5];
            for (int i = 0; i < 5; i++) {
                bestResultCards[i] = aButtonsCardEnemy[i].getCardValue();
            }

            for (int i = 0; i < (ApiVariables.difficulty - 1); i++) {
                resetCards(false);
                int currentCalcEnemy = calcResult(false);
                if (currentCalcEnemy > bestResultInt) {
                    bestResultInt = currentCalcEnemy;
                    for (int j = 0; j < 5; j++) {
                        bestResultCards[j] = aButtonsCardEnemy[j].getCardValue();
                    }
                }
            }

            for (int k = 0; k < 5; k++) {
                aButtonsCardEnemy[k].setCardValue(bestResultCards[k]);
            }
        }

        for (int l = 0; l < 5; l++) {
            aButtonsCardEnemy[l].setIcon(getCardImage(getCardValueFromInt(aButtonsCardEnemy[l].getCardValue())));
        }

        int resultIntEnemy = calcResult(false);
        RESULT_TYPE resultTypeEnemy = getResultType(resultIntEnemy);
        labelEnemyResult.setText(getResultText(resultTypeEnemy));
        strCurrentEnemyResult = getResultText(resultTypeEnemy);
        if (resultTypeEnemy == RESULT_TYPE.RESULT_5SAME) {
            ApiVariables.statsAmountEnemy5Same++;
        } else if (resultTypeEnemy == RESULT_TYPE.RESULT_4SAME) {
            ApiVariables.statsAmountEnemy4Same++;
        } else if (resultTypeEnemy == RESULT_TYPE.RESULT_FULLHOUSE) {
            ApiVariables.statsAmountEnemyFullHouse++;
        } else if (resultTypeEnemy == RESULT_TYPE.RESULT_3SAME) {
            ApiVariables.statsAmountEnemy3Same++;
        } else if (resultTypeEnemy == RESULT_TYPE.RESULT_2PAIRS) {
            ApiVariables.statsAmountEnemy2Pairs++;
        } else if (resultTypeEnemy == RESULT_TYPE.RESULT_1PAIR) {
            ApiVariables.statsAmountEnemy1Pair++;
        } else if (resultTypeEnemy == RESULT_TYPE.RESULT_NOTHING) {
            ApiVariables.statsAmountEnemyNothing++;
        }

        int resultIntPlayer = calcResult(true);
        RESULT_TYPE resultTypePlayer = getResultType(resultIntPlayer);
        labelPlayerResult.setText(getResultText(resultTypePlayer));
        strCurrentPlayerResult = getResultText(resultTypePlayer);
        if (resultTypePlayer == RESULT_TYPE.RESULT_5SAME) {
            ApiVariables.statsAmountPlayer5Same++;
        } else if (resultTypePlayer == RESULT_TYPE.RESULT_4SAME) {
            ApiVariables.statsAmountPlayer4Same++;
        } else if (resultTypePlayer == RESULT_TYPE.RESULT_FULLHOUSE) {
            ApiVariables.statsAmountPlayerFullHouse++;
        } else if (resultTypePlayer == RESULT_TYPE.RESULT_3SAME) {
            ApiVariables.statsAmountPlayer3Same++;
        } else if (resultTypePlayer == RESULT_TYPE.RESULT_2PAIRS) {
            ApiVariables.statsAmountPlayer2Pairs++;
        } else if (resultTypePlayer == RESULT_TYPE.RESULT_1PAIR) {
            ApiVariables.statsAmountPlayer1Pair++;
        } else if (resultTypePlayer == RESULT_TYPE.RESULT_NOTHING) {
            ApiVariables.statsAmountPlayerNothing++;
        }

        if (ApiVariables.currentBetValue == 1) {
            ApiVariables.statsAmountBet1Coin++;
        } else if (ApiVariables.currentBetValue == 2) {
            ApiVariables.statsAmountBet2Coins++;
        } else if (ApiVariables.currentBetValue == 3) {
            ApiVariables.statsAmountBet3Coins++;
        } else if (ApiVariables.currentBetValue == 4) {
            ApiVariables.statsAmountBet4Coins++;
        } else if (ApiVariables.currentBetValue == 5) {
            ApiVariables.statsAmountBet5Coins++;
        }

        if (resultIntEnemy > resultIntPlayer) {
            if (ApiVariables.coinAmount <= 0) {
                ApiVariables.statsAmountGameOver++;
                ApiVariables.statsAmountRoundsLost++;
                ApiVariables.statsAmountStarsLost++;
                ApiVariables.statsAmountCoinsLost += ApiVariables.currentBetValue;
                labelResult.setTextWithColor(ApiLanguage.getText("gameover"), CustomLabelType.DARK_RED);
                strCurrentResult = ApiLanguage.getText("gameover");
                strCurrentResultCoins = "";
                ApiVariables.coinAmount = 10;
                updateCoinAmount();
                ApiVariables.currentBetValue = 1;
                updateBetCoins();
                ApiVariables.starAmount = 0;
                updateStarAmount();
                closeCurrentRound();
            } else {
                ApiVariables.statsAmountRoundsLost++;
                ApiVariables.statsAmountStarsLost++;
                ApiVariables.statsAmountCoinsLost += ApiVariables.currentBetValue;
                labelResult.setTextWithColor(ApiLanguage.getText("lost") + " (-" + ApiVariables.currentBetValue + ")", CustomLabelType.RED);
                strCurrentResult = ApiLanguage.getText("lost");
                strCurrentResultCoins = " (-" + ApiVariables.currentBetValue + ")";
                if (ApiVariables.starAmount > 0) {
                    ApiVariables.starAmount--;
                    updateStarAmount();
                }
                closeCurrentRound();
            }
        } else if (resultIntEnemy < resultIntPlayer) {
            ApiVariables.statsAmountRoundsWon++;
            int wonCoinsAmount = getWonCoinsAmount(resultTypePlayer);
            labelResult.setTextWithColor(ApiLanguage.getText("won") + " (+" + wonCoinsAmount + ")", CustomLabelType.GREEN);
            strCurrentResult = ApiLanguage.getText("won");
            strCurrentResultCoins = " (+" + wonCoinsAmount + ")";

            ApiVariables.coinAmount += wonCoinsAmount;
            updateCoinAmount();
            if (ApiVariables.coinAmount > ApiVariables.statsAmountCoinsMax) {
                ApiVariables.statsAmountCoinsMax = ApiVariables.coinAmount;
            }
            ApiVariables.statsAmountCoinsWon += wonCoinsAmount;

            ApiVariables.starAmount++;
            updateStarAmount();
            if (ApiVariables.starAmount > ApiVariables.statsAmountStarsMax) {
                ApiVariables.statsAmountStarsMax = ApiVariables.starAmount;
            }
            ApiVariables.statsAmountStarsWon++;

            closeCurrentRound();
        } else if (resultIntEnemy == resultIntPlayer) {
            ApiVariables.statsAmountRoundsDraw++;
            labelResult.setTextWithColor(ApiLanguage.getText("draw"), CustomLabelType.BLUE);
            strCurrentResult = ApiLanguage.getText("draw");
            strCurrentResultCoins = "";
            ApiVariables.coinAmount += ApiVariables.currentBetValue;
            updateCoinAmount();
            closeCurrentRound();
        } else {
            ApiFunctions.crash("UNKNOWN ERROR");
        }
    }


    private int getWonCoinsAmount(RESULT_TYPE result) {
        switch (result) {
            case RESULT_5SAME:
                return 16 * ApiVariables.currentBetValue;
            case RESULT_4SAME:
                return 8 * ApiVariables.currentBetValue;
            case RESULT_FULLHOUSE:
                return 6 * ApiVariables.currentBetValue;
            case RESULT_3SAME:
                return 4 * ApiVariables.currentBetValue;
            case RESULT_2PAIRS:
                return 3 * ApiVariables.currentBetValue;
            case RESULT_1PAIR:
                return 2 * ApiVariables.currentBetValue;
            case RESULT_NOTHING:
                return 1 * ApiVariables.currentBetValue;
            default:
                ApiFunctions.crash("INVALID CARD VALUE (GETRESULTTEXT)");
                return -1;
        }
    }

    private String getResultText(RESULT_TYPE result) {
        switch (result) {
            case RESULT_5SAME:
                return ApiLanguage.getText("5same");
            case RESULT_4SAME:
                return ApiLanguage.getText("4same");
            case RESULT_FULLHOUSE:
                return ApiLanguage.getText("fullhouse");
            case RESULT_3SAME:
                return ApiLanguage.getText("3same");
            case RESULT_2PAIRS:
                return ApiLanguage.getText("2pairs");
            case RESULT_1PAIR:
                return ApiLanguage.getText("1pair");
            case RESULT_NOTHING:
                return ApiLanguage.getText("nothing");
            default:
                ApiFunctions.crash("INVALID CARD VALUE (GETRESULTTEXT)");
                return null;
        }
    }

    private RESULT_TYPE getResultType(int resultValue) {
        if (resultValue >= ApiVariables.multiplicator5x) {
            return RESULT_TYPE.RESULT_5SAME;
        } else if (resultValue >= ApiVariables.multiplicator4x) {
            return RESULT_TYPE.RESULT_4SAME;
        } else if (resultValue >= ApiVariables.multiplicatorFH) {
            return RESULT_TYPE.RESULT_FULLHOUSE;
        } else if (resultValue >= ApiVariables.multiplicator3x) {
            return RESULT_TYPE.RESULT_3SAME;
        } else if (resultValue >= ApiVariables.multiplicator2x2x) {
            return RESULT_TYPE.RESULT_2PAIRS;
        } else if (resultValue >= ApiVariables.multiplicator2x) {
            return RESULT_TYPE.RESULT_1PAIR;
        } else {
            return RESULT_TYPE.RESULT_NOTHING;
        }
    }

    private BufferedImage getTableOrInfoImage(CARD_VALUE cardValue) {
        switch (cardValue) {
            case DEFAULT:
                return bImgInterfaceItemDefault;
            case STAR:
                return bImgInterfaceItemStar;
            case MARIO:
                return bImgInterfaceItemMario;
            case LUIGI:
                return bImgInterfaceItemLuigi;
            case FIREFLOWER:
                return bImgInterfaceItemFireflower;
            case MUSHROOM:
                return bImgInterfaceItemMushroom;
            case CLOUD:
                return bImgInterfaceItemCloud;
            default:
                ApiFunctions.crash("Invalid Image!");
                return null;
        }
    }

    private ImageIcon getCardImage(CARD_VALUE cardValue) {
        switch (cardValue) {
            case DEFAULT:
                return new ImageIcon(bImgCardItemDefault);
            case STAR:
                return new ImageIcon(bImgCardItemStar);
            case MARIO:
                return new ImageIcon(bImgCardItemMario);
            case LUIGI:
                return new ImageIcon(bImgCardItemLuigi);
            case FIREFLOWER:
                return new ImageIcon(bImgCardItemFireflower);
            case MUSHROOM:
                return new ImageIcon(bImgCardItemMushroom);
            case CLOUD:
                return new ImageIcon(bImgCardItemCloud);
            default:
                ApiFunctions.crash("Invalid Card Value!");
                return null;
        }
    }

    private CARD_VALUE getCardValueFromInt(int value) {
        switch (value) {
            case 0:
                return CARD_VALUE.DEFAULT;
            case 1:
                return CARD_VALUE.STAR;
            case 2:
                return CARD_VALUE.MARIO;
            case 3:
                return CARD_VALUE.LUIGI;
            case 4:
                return CARD_VALUE.FIREFLOWER;
            case 5:
                return CARD_VALUE.MUSHROOM;
            case 6:
                return CARD_VALUE.CLOUD;
            default:
                ApiFunctions.crash("Invalid Card Value Int!");
                return null;
        }
    }
}
