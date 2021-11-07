package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

/**
 * Deletes an entity (property or buyer) identified using it's displayed index from the address book.
 */
public abstract class DeleteCommand extends SimpleCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the property/buyer identified by the index (must be a positive integer) "
            + "used in the displayed property/buyer list.\n"
            + "Parameters: ( property | buyer ) INDEX \n"
            + "Example: " + COMMAND_WORD + " property 1";

    protected final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
