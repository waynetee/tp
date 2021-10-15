package seedu.address.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.UiCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.UiParser;
import seedu.address.logic.parser.exceptions.ParseException;

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
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane propertyListPanelPlaceholder;

    @FXML
    private StackPane buyerListPanelPlaceholder;

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


    /**
     * Opens the help window or focuses on it if it's already opened.
     *
     * @return commandResult for HelpCommand
     */
    @FXML
    public CommandResult handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
        CommandResult commandResult = HelpCommand.execute();
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
        return commandResult;
    }

    /**
     * Gets user to select a destination for saving csv.
     *
     * @param title Title of fileChooser dialog box
     * @return File object chosen by user
     */
    public File getSaveCsvFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Csv Files", "*.csv"));
        fileChooser.setInitialDirectory(Paths.get(".").toFile());
        return fileChooser.showSaveDialog(primaryStage);
    }

    /**
     * Exports Properties.
     */
    @FXML
    public CommandResult handleExportProperties() {
        File file = getSaveCsvFile("Save Properties to CSV");
        CommandResult commandResult;
        if (file == null) {
            commandResult =  ExportCommand.executePropertiesCancelled();
        }
        else {
            try {
                logic.exportProperties(file);
                commandResult = ExportCommand.executePropertiesSuccess();
            } catch (IOException ioe) {
                logger.warning("Problem while exporting Properties.");
                commandResult = ExportCommand.executePropertiesFailure();
            }
        }
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
        return ExportCommand.executePropertiesSuccess();
    }

    /**
     * Exports Buyers.
     */
    @FXML
    public CommandResult handleExportBuyers() {
        File file = getSaveCsvFile("Save Buyers to CSV");
        CommandResult commandResult;
        if (file == null) {
            commandResult = ExportCommand.executeBuyersCancelled();
        }
        else {
            try {
                logic.exportBuyers(file);
                commandResult = ExportCommand.executeBuyersSuccess();
            } catch (IOException ioe) {
                logger.warning("Problem while exporting Buyers.");
                commandResult = ExportCommand.executeBuyersFailure();
            }
        }
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
        return commandResult;
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     *
     * @return commandResult for ExitCommand
     */
    @FXML
    private CommandResult handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
        CommandResult commandResult = ExitCommand.execute();
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
        return commandResult;
    }

    public PropertyListPanel getPropertyListPanel() {
        return propertyListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            // Handle commands that require Ui handle function.
            UiCommand uiCommand = UiParser.parseCommand(commandText);
            switch(uiCommand) {
            case HELP:
                return handleHelp();
            case EXIT:
                return handleExit();
            case EXPORT_PROPERTIES:
                return handleExportProperties();
            case EXPORT_BUYERS:
                return handleExportBuyers();
            }

            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
