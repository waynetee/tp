package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Adds an entity (property or buyer) to the address book.
 */
public abstract class AddCommand extends SimpleCommand {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property or buyer to the address book.\n\n"
            + "Parameters (property): "
            + "property "
            + PREFIX_NAME + "NAME "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_SELLER + "SELLER "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_TAG + "TAG]..."
            + "\n"
            + "Parameters (buyer): "
            + "buyer "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_PRICE + "BUDGET "
            + "[" + PREFIX_TAG + "TAG]..."
            + "\n"
            + "Example: " + COMMAND_WORD + " property "
            + PREFIX_NAME + "Hasta La Vista "
            + PREFIX_ADDRESS + "20 Clementi Ave 2, #02-25 "
            + PREFIX_PRICE + "1652000 "
            + PREFIX_SELLER + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_TAG + "Condo "
            + PREFIX_TAG + "4 rm "
            + PREFIX_TAG + "621 sqft "
            + PREFIX_TAG + "EW23 Clementi";

    public static final String EXPECTED_PREAMBLE = "( property | buyer )";
}
