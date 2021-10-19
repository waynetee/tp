package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUYERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROPERTIES;

import seedu.address.model.Model;

/**
 * Returns to default view.
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_SUCCESS = "Returned to main screen.";
    public static final String MESSAGE_BACK_COMMAND = "Type the '" + COMMAND_WORD
            + "' command to return to the main screen.";


    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, UiAction.SHOW_DEFAULT);
    }
}
