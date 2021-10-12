package seedu.address.logic.commands.buyer;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Buyer;

/**
 * Deletes a buyer identified using it's displayed index from the address book.
 */
public class DeleteBuyerCommand extends DeleteCommand {
    public static final String MESSAGE_DELETE_BUYER_SUCCESS = "Deleted Buyer: %1$s";

    public DeleteBuyerCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Buyer> lastShownList = model.getFilteredBuyerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
        }

        Buyer buyerToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBuyer(buyerToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_BUYER_SUCCESS, buyerToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBuyerCommand // instanceof handles nulls
                && super.equals(other));
    }
}
