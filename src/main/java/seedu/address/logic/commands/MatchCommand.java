package seedu.address.logic.commands;


/**
 * Matches entities (properties and buyers) in the address book.
 */
public abstract class MatchCommand extends SimpleCommand {

    public static final String COMMAND_WORD = "match";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Matches buyers and properties based on tags and price. "
                    + "The argument INDEX must be positive.\n\n"
                    + "Parameters: ( auto | property INDEX | buyer INDEX )\n"
                    + "Example: " + COMMAND_WORD + " auto";

}
