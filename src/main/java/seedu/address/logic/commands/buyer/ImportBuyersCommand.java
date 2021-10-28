package seedu.address.logic.commands.buyer;

import java.io.File;
import java.io.IOException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.property.exceptions.DuplicateBuyerException;
import seedu.address.storage.Storage;

/**
 * Imports buyers from csv file.
 */
public class ImportBuyersCommand extends ImportCommand {
    @Override
    public CommandResult execute(Model model, File file) throws CommandException {
        if (file == null) {
            return new CommandResult(String.format(MESSAGE_CANCELLED, BUYERS));
        }
        try {
            model.setAddressBook(Storage.importBuyers(model.getAddressBook(), file));
            return new CommandResult(String.format(MESSAGE_SUCCESS, BUYERS));
        } catch (IOException ioe) {
            return new CommandResult(String.format(MESSAGE_IO_FAILURE, BUYERS));
        } catch (ParseException pe) {
            return new CommandResult(String.format(MESSAGE_FORMAT_FAILURE, BUYERS) + "\n" + pe.getMessage());
        } catch (DuplicateBuyerException dbe) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE, BUYERS, BUYERS));
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

        return other instanceof ImportBuyersCommand;
    }
}
