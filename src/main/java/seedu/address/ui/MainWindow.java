package seedu.address.ui;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandPreAction;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.stats.Stat;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PropertyListPanel propertyListPanel;
    private BuyerListPanel buyerListPanel;
    private MatchListPanel matchListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private StatWindow statWindow;

    private boolean showingMatchAutoView = false;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private HBox defaultView;

    @FXML
    private VBox matchAutoView;

    @FXML
    private StackPane propertyListPanelPlaceholder;

    @FXML
    private StackPane buyerListPanelPlaceholder;

    @FXML
    private StackPane matchListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        statWindow = new StatWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        propertyListPanel = new PropertyListPanel(logic.getFilteredPropertyList());
        propertyListPanelPlaceholder.getChildren().add(propertyListPanel.getRoot());

        buyerListPanel = new BuyerListPanel(logic.getFilteredBuyerList());
        buyerListPanelPlaceholder.getChildren().add(buyerListPanel.getRoot());

        matchListPanel = new MatchListPanel(logic.getMatchList());
        matchListPanelPlaceholder.getChildren().add(matchListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    void show() {
        primaryStage.show();
    }

    public PropertyListPanel getPropertyListPanel() {
        return propertyListPanel;
    }



    /**
     * Imports Properties.
     */
    @FXML
    public void handleImportProperties() {
        try {
            executeCommand(ImportCommand.COMMAND_WORD + " " + ImportCommand.PROPERTIES);
        } catch (CommandException | ParseException e) {
            logger.warning("handleImportProperties failed!");
        }
    }

    /**
     * Imports Buyers.
     */
    @FXML
    public void handleImportBuyers() {
        try {
            executeCommand(ImportCommand.COMMAND_WORD + " " + ImportCommand.BUYERS);
        } catch (CommandException | ParseException e) {
            logger.warning("handleImportBuyers failed!");
        }
    }

    /**
     * Exports Properties.
     */
    @FXML
    public void handleExportProperties() {
        try {
            executeCommand(ExportCommand.COMMAND_WORD + " " + ExportCommand.PROPERTIES);
        } catch (CommandException | ParseException e) {
            logger.warning("handleExportProperties failed!");
        }
    }

    /**
     * Exports Buyers.
     */
    @FXML
    public void handleExportBuyers() {
        try {
            executeCommand(ExportCommand.COMMAND_WORD + " " + ExportCommand.BUYERS);
        } catch (CommandException | ParseException e) {
            logger.warning("handleExportBuyers failed!");
        }
    }

    /**
     * Handles
     */
    @FXML
    public void handleStat(Stat stat) {
        statWindow.hide();
        statWindow.setStat(stat);
        statWindow.show();
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        statWindow.hide();
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            logic.validateCommand(commandText, showingMatchAutoView);
            CommandResult commandResult;
            CommandPreAction commandPreAction = logic.getCommandPreAction(commandText);
            if (commandPreAction.requiresFile()) {
                File file = getCsvFile(commandPreAction.getFileDialogPrompt(), commandPreAction.isFileSave());
                commandResult = logic.execute(commandText, file);
            } else {
                commandResult = logic.execute(commandText);
            }

            handleUiAction(commandResult);

            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Gets user to select a destination for saving csv.
     *
     * @param title Title of fileChooser dialog box
     * @return File object chosen by user
     */
    public File getCsvFile(String title, boolean isFileSave) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Csv Files", "*.csv"));
        fileChooser.setInitialDirectory(Paths.get(".").toFile());
        if (isFileSave) {
            return fileChooser.showSaveDialog(primaryStage);
        } else {
            return fileChooser.showOpenDialog(primaryStage);
        }
    }

    private void handleUiAction(CommandResult commandResult) {
        switch (commandResult.getUiAction()) {
        case STAT:
            Optional<UiElement> stat = commandResult.getUiElement();
            assert !stat.isEmpty() && stat.get() instanceof Stat;
            handleStat((Stat) stat.get());
            break;
        case HELP:
            handleHelp();
            break;
        case EXIT:
            handleExit();
            break;
        case SHOW_MATCHES:
            showMatchAutoView();
            break;
        case SHOW_DEFAULT:
            hideMatchAutoView();
            break;
        default:
            break;
        }
    }

    private void showMatchAutoView() {
        setView(true);
    }

    private void hideMatchAutoView() {
        setView(false);
    }

    /**
     * Sets the current UI being shown to the user (match auto view or default view)
     */
    private void setView(boolean showMatchAutoView) {
        showingMatchAutoView = showMatchAutoView;
        defaultView.setVisible(!showingMatchAutoView);
        defaultView.setManaged(!showingMatchAutoView);
        matchAutoView.setVisible(showingMatchAutoView);
        matchAutoView.setManaged(showingMatchAutoView);
    }

}
