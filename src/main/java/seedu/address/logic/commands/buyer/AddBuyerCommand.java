package seedu.address.logic.commands.buyer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Buyer;

/**
 * Adds a buyer to the address book.
 */
public class AddBuyerCommand extends AddCommand {
    public static final String MESSAGE_SUCCESS = "New buyer added: %1$s";
    public static final String MESSAGE_DUPLICATE_BUYER = "This buyer already exists in the address book";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " buyer: Adds a buyer to the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_PRICE + "BUDGET "
            + "[" + PREFIX_TAG + "TAG]..."
            + ""
            + "\n"
            + "Example: " + COMMAND_WORD + " buyer "
            + PREFIX_NAME + "Lydia "
            + PREFIX_PHONE + "9482427 "
            + PREFIX_EMAIL + "lydia@example.com "
            + PREFIX_PRICE + "1000000 "
            + PREFIX_TAG + "Condo "
            + PREFIX_TAG + "4 rm ";

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

        model.addNewBuyer(buyerToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, buyerToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBuyerCommand // instanceof handles nulls
                && buyerToAdd.equals(((AddBuyerCommand) other).buyerToAdd));
    }
}
