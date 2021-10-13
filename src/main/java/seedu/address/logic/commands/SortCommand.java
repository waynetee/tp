package seedu.address.logic.commands;

import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;

public abstract class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort properties or buyers by price or name. \n"
            + "Existing properties or buyers list will be overwritten by the sorted list.\n"
            + "Parameters: (buyer/property) (name/price) (asc/desc)";

    private final SortType sortType;
    private final SortDirection sortDirection;

    public SortCommand(SortType sortType, SortDirection sortDirection) {
        this.sortType = sortType;
        this.sortDirection = sortDirection;
    }

    public SortType getSortType() {
        return sortType;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return sortType == otherCommand.sortType
                && sortDirection == otherCommand.sortDirection;
    }
}
