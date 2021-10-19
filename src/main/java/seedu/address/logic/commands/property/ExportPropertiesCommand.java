package seedu.address.logic.commands.property;

import java.io.File;
import java.io.IOException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Exports properties to csv file.
 */
public class ExportPropertiesCommand extends ExportCommand {
    @Override
    public CommandResult execute(Model model, File file) throws CommandException {
        if (file == null) {
            return new CommandResult(String.format(MESSAGE_CANCELLED, PROPERTIES));
        }
        try {
            Storage.exportProperties(model.getAddressBook(), file);
            return new CommandResult(String.format(MESSAGE_SUCCESS, PROPERTIES));
        } catch (IOException ioe) {
            return new CommandResult(String.format(MESSAGE_FAILURE, PROPERTIES));
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

        return other instanceof ExportPropertiesCommand;
    }
}
