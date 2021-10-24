package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Returns to default view (showing property list and buyer list).
 */
public class BackCommand extends SimpleCommand {

    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_SUCCESS = "Returned to main screen.";
    public static final String MESSAGE_BACK_COMMAND = "Key in the '" + COMMAND_WORD
            + "' command to return to the main screen.";


    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, UiAction.SHOW_DEFAULT);
    }

    @Override
    public boolean canRunInDefaultView() {
        return false;
    }

    @Override
    public boolean canRunInMatchAutoView() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BackCommand;
    }

}
