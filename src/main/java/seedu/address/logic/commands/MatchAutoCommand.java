package seedu.address.logic.commands;


import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Matches as many properties and buyers together as possible.
 */
public class MatchAutoCommand extends MatchCommand {

    public static final String ARGUMENT_WORD = "auto";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
