package seedu.address.logic;

import java.io.File;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandPreAction;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Match;
import seedu.address.model.property.Property;

/**
 * API of the Logic component
 */
public interface Logic {


    /**
     * Validates the command input given the state of the ui.
     *
     * @param commandText The command as entered by the user.
     * @param showingMatchAutoView True if the UI is showing the results of the match auto command.
     * @throws ParseException If an error occurs during parsing.
     * @throws CommandException If command cannot be run because of the current UI state.
     */
    void validateCommand(String commandText, boolean showingMatchAutoView) throws ParseException, CommandException;

    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;


    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @param file File chosen by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText, File file) throws CommandException, ParseException;

    /**
     * @param commandText The command as entered by the user.
     * @return True if the command requires a file to be executed.
     * @throws ParseException If commandText does not fit expected format.
     */
    CommandPreAction getCommandPreAction(String commandText) throws ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of properties.
     */
    ObservableList<Property> getFilteredPropertyList();

    /**
     * Returns an unmodifiable view of the filtered buyer list.
     */
    ObservableList<Buyer> getFilteredBuyerList();

    /**
     * Returns an unmodifiable view of the match list.
     */
    ObservableList<Match> getMatchList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
