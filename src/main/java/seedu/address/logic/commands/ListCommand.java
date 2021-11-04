package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all properties in the address book to the user.
 */
public class ListCommand extends SimpleCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all properties and buyers.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.showAllProperties();
        model.showAllBuyers();
        return new CommandResult(MESSAGE_SUCCESS, UiAction.SHOW_DEFAULT);
    }

    @Override
    public boolean canRunInMatchAutoView() {
        return true;
    }

}
