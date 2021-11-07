package seedu.address.commons.core;

import static seedu.address.logic.commands.BackCommand.MESSAGE_BACK_COMMAND;

import seedu.address.logic.commands.MatchAutoCommand;
import seedu.address.logic.commands.MatchCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format. \n%1$s ";
    public static final String MESSAGE_UNKNOWN_ACTOR = "Invalid command format: "
            + "%1$s command should start with '%1$s property' or '%1$s buyer'.\n\n%2$s ";
    public static final String MESSAGE_PROPERTY_LISTED_OVERVIEW = "1 property listed!";
    public static final String MESSAGE_BUYER_LISTED_OVERVIEW = "1 buyer listed!";
    public static final String MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX = "The property index provided is invalid.";
    public static final String MESSAGE_PROPERTIES_LISTED_OVERVIEW = "%1$d properties listed!";
    public static final String MESSAGE_INVALID_BUYER_DISPLAYED_INDEX = "The buyer index provided is invalid.";
    public static final String MESSAGE_BUYERS_LISTED_OVERVIEW = "%1$d buyers listed!";
    public static final String MESSAGE_MATCH_AUTO_VIEW_INVALID_COMMAND =
            "Command is not available in this screen.\n" + MESSAGE_BACK_COMMAND;
    public static final String MESSAGE_DEFAULT_VIEW_INVALID_COMMAND =
            "This command can only be used after entering the '"
            + MatchCommand.COMMAND_WORD + " " + MatchAutoCommand.ARGUMENT_WORD + "' command.";
}
