package seedu.address.logic.commands.property;

import java.io.File;
import java.io.IOException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.storage.Storage;

/**
 * Imports properties from csv file.
 */
public class ImportPropertiesCommand extends ImportCommand {
    @Override
    public CommandResult execute(Model model, File file) throws CommandException {
        if (file == null) {
            return new CommandResult(String.format(MESSAGE_CANCELLED, PROPERTIES));
        }
        try {
            model.setAddressBook(Storage.importProperties(model.getAddressBook(), file));
            return new CommandResult(String.format(MESSAGE_SUCCESS, PROPERTIES));
        } catch (IOException ioe) {
            return new CommandResult(String.format(MESSAGE_IO_FAILURE, PROPERTIES));
        } catch (ParseException pe) {
            return new CommandResult(String.format(MESSAGE_FORMAT_FAILURE, PROPERTIES) + "\n" + pe.getMessage());
        } catch (DuplicatePropertyException dpe) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE, PROPERTIES, PROPERTIES));
        }
    }

    public String toString() {
        return COMMAND_WORD + ' ' + PROPERTIES;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return other instanceof ImportPropertiesCommand;
    }
}
