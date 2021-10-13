package seedu.address.logic.commands;

import seedu.address.model.field.SortType;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_TYPE;


public abstract class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort properties or buyers"
            + "by the price. \n"
            + "Existing properties or buyers list will be overwritten by the sorted list.\n"
            + "Parameters: (buyer/property) ["
            + PREFIX_SORT_TYPE + "(name/price)]";

    private final SortType sortType;

    public SortCommand(SortType sortType) {
        this.sortType = sortType;
    }

    public SortType getSortType() {
        return sortType;
    }
}
