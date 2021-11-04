package seedu.address.logic.commands;

import java.io.File;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed with a file.
 */
public abstract class CommandWithFile extends Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param file {@code File} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, File file) throws CommandException;

}
