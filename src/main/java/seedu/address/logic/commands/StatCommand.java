package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StatView;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

public class StatCommand extends Command {

    public static final String COMMAND_WORD = "stat";
    public static final String PROPERTY_WORD = "property";
    public static final String BUYER_WORD = "buyer";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens a pop up with a price histogram of buyers, properties or both.\n"
            + "Parameters: [(property | buyer)]";

    public static final String MESSAGE_ARGUMENTS = "%s";

    private final String view;

    /**
     * Constructor for {@code StatCommand}.
     * @param view Specifying buyer, property, or both in view.
     */
    public StatCommand(String view) {
        requireAllNonNull(view);

        this.view = view;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Buyer> buyerList = model.getFilteredBuyerList();
        ObservableList<Property> propertyList = model.getFilteredPropertyList();
        StatView statWindow = new StatView(buyerList, propertyList);
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, this.view));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatCommand)) {
            return false;
        }

        // state check
        StatCommand e = (StatCommand) other;
        return view.equals(e.view);
    }

}
