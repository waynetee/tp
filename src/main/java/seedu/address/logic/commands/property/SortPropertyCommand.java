package seedu.address.logic.commands.property;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.Model;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;

import static java.util.Objects.requireNonNull;

public class SortPropertyCommand extends SortCommand {
    public static final String MESSAGE_SUCCESS = "Sorted all properties by %s in %s order.";

    public SortPropertyCommand(SortType sortType, SortDirection sortDirection) {
        super(sortType, sortDirection);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        SortType sortType = getSortType();
        SortDirection sortDirection = getSortDirection();

        if (sortType == SortType.PRICE && sortDirection == SortDirection.ASC) {
            model.sortPropertiesPrice();
        } else if (sortType == SortType.PRICE && sortDirection == SortDirection.DESC) {
            model.sortPropertiesPriceDesc();
        } else if (sortType == SortType.NAME && sortDirection == SortDirection.ASC) {
            model.sortPropertiesName();
        } else if (sortType == SortType.NAME && sortDirection == SortDirection.DESC) {
            model.sortPropertiesNameDesc();
        } else {
            assert false;
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortType, sortDirection));
    }
}
