package seedu.address.logic.commands.buyer;

import java.io.File;
import java.io.IOException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Exports buyers to csv file.
 */
public class ExportBuyersCommand extends ExportCommand {
    @Override
    public CommandResult execute(Model model, File file) throws CommandException {
        if (file == null) {
            return new CommandResult(String.format(MESSAGE_CANCELLED, BUYERS));
        }
        try {
            Storage.exportBuyers(model.getAddressBook(), file);
            return new CommandResult(String.format(MESSAGE_SUCCESS, BUYERS));
        } catch (IOException ioe) {
            return new CommandResult(String.format(MESSAGE_FAILURE, BUYERS));
        }
    }

    public String toString() {
        return COMMAND_WORD + ' ' + BUYERS;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return other instanceof ExportBuyersCommand;
    }
}
