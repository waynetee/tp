package seedu.address.logic.commands.buyer;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Buyer;

public class AddBuyerCommand extends AddCommand {
    public static final String MESSAGE_SUCCESS = "New buyer added: %1$s";
    public static final String MESSAGE_DUPLICATE_BUYER = "This buyer already exists in the address book";

    private final Buyer buyerToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Buyer}
     */
    public AddBuyerCommand(Buyer buyer) {
        requireNonNull(buyer);
        buyerToAdd = buyer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasBuyer(buyerToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUYER);
        }

        model.addBuyer(buyerToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, buyerToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBuyerCommand // instanceof handles nulls
                && buyerToAdd.equals(((AddBuyerCommand) other).buyerToAdd));
    }
}
