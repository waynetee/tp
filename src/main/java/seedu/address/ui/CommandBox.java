package seedu.address.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final Queue<String> commandQueue;

    @FXML
    private StackPane stackPane;
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     * When multiple lines of commands are pasted into the box,
     * pressing enter will execute the first command and autofill the next one.
     * This continues until a different command is entered or all commands have been enetered.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandQueue = new LinkedList<>();
        initCommandBox();
    }

    private void initCommandBox() {
        commandTextField = new TextField() {
            // Anonymous class to intercept pastes
            @Override
            public void paste() {
                handlePaste();
            }
        };
        commandTextField.setId("commandTextField");
        commandTextField.setOnAction((e) -> handleCommandEntered());
        commandTextField.setPromptText("Enter command here...");
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        stackPane.getChildren().add(commandTextField);
    }

    private void handlePaste() {
        String contents = Clipboard.getSystemClipboard().getString();
        if (contents == null) {
            return;
        }
        commandQueue.addAll(List.of(contents.split("\\R"))); // Split by newlines
        commandQueue.removeIf(command -> command.equals("")); // Remove empty lines
        updateCommandFieldFromQueue();

    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }
        updateQueue(commandText);
        executeCommand(commandText);
        updateCommandFieldFromQueue();
    }

    private void updateQueue(String commandText) {
        if (commandText.equals(commandQueue.peek())) {
            // User entered pasted command
            commandQueue.poll();
        } else {
            // User entered different command
            commandQueue.clear();
        }
    }

    private void executeCommand(String commandText) {
        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    private void updateCommandFieldFromQueue() {
        if (!commandQueue.isEmpty()) {
            commandTextField.setText(commandQueue.peek());
            commandTextField.end(); // Moves caret to end
        }
    }


    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
