package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_DEFAULT_VIEW_INVALID_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_MATCH_AUTO_VIEW_INVALID_COMMAND;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandPreAction;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandWithFile;
import seedu.address.logic.commands.SimpleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Match;
import seedu.address.model.property.Property;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String COMMANDTEXT_INVALID_MESSAGE = "commandText is invalid";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }


    @Override
    public void validateCommand(String commandText, boolean showingMatchAutoView)
            throws ParseException, CommandException {
        Command command = addressBookParser.parseAnyCommand(commandText);
        if (showingMatchAutoView && !command.canRunInMatchAutoView()) {
            throw new CommandException(MESSAGE_MATCH_AUTO_VIEW_INVALID_COMMAND);
        }
        if (!showingMatchAutoView && !command.canRunInDefaultView()) {
            throw new CommandException(MESSAGE_DEFAULT_VIEW_INVALID_COMMAND);
        }
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        SimpleCommand command = addressBookParser.parseSimpleCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public CommandResult execute(String commandText, File file) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND WITH FILE][" + commandText + "]");

        CommandResult commandResult;
        Optional<CommandWithFile> command = addressBookParser.parseCommandWithFile(commandText);
        assert command.isPresent() : COMMANDTEXT_INVALID_MESSAGE;
        commandResult = command.get().execute(model, file);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public CommandPreAction getCommandPreAction(String commandText) throws ParseException {
        return addressBookParser.getCommandPreAction(commandText);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Property> getFilteredPropertyList() {
        return model.getFilteredPropertyList();
    }

    @Override
    public ObservableList<Buyer> getFilteredBuyerList() {
        return model.getFilteredBuyerList();
    }

    @Override
    public ObservableList<Match> getMatchList() {
        return model.getMatchList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
