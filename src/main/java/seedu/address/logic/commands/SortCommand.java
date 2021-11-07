package seedu.address.logic.commands;

import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;

/**
 * Sorts either the property or buyer list in the address book.
 */
public abstract class SortCommand extends SimpleCommand {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort properties or buyers by price or name. \n"
            + "Note that this replaces the list of visible properties or buyers.\n"
            + "Parameters: ( buyer | property ) ( name | price ) ( asc | desc )";

    private final SortType sortType;
    private final SortDirection sortDirection;

    /**
     * Creates a SortCommand with sort type and direction.
     *
     * @param sortType A {@code SortType} enum.
     * @param sortDirection A {@code SortDirection} enum.
     */
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
