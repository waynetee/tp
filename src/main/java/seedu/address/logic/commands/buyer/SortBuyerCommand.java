package seedu.address.logic.commands.buyer;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.Model;
import seedu.address.model.field.SortType;

import static java.util.Objects.requireNonNull;

public class SortBuyerCommand extends SortCommand {
    public static final String MESSAGE_SUCCESS = "Sorted all buyers by %s";

    public SortBuyerCommand(SortType sortType) {
        super(sortType);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        SortType sortType = getSortType();
        switch (getSortType()) {
        case PRICE:
            model.sortBuyersPrice();
            break;
        case NAME:
            model.sortBuyersName();
            break;
        default:
            assert false;
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortType));
    }
}
