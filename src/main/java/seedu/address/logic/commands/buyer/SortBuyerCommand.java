package seedu.address.logic.commands.buyer;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.Model;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;

/**
 * Sorts the buyer list with the given sort type and direction.
 */
public class SortBuyerCommand extends SortCommand {
    public static final String MESSAGE_SUCCESS = "Sorted all buyers by %s in %s order.";

    public SortBuyerCommand(SortType sortType, SortDirection sortDirection) {
        super(sortType, sortDirection);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        SortType sortType = getSortType();
        SortDirection sortDirection = getSortDirection();

        model.sortBuyers(sortType, sortDirection);

        return new CommandResult(String.format(MESSAGE_SUCCESS, sortType, sortDirection));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortBuyerCommand)) {
            return false;
        }

        return super.equals(other);
    }
}
