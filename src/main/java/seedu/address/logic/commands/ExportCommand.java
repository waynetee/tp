package seedu.address.logic.commands;

public abstract class ExportCommand extends CommandWithFile {
    public static final String COMMAND_WORD = "export";

    public static final String PROPERTIES = "properties";

    public static final String BUYERS = "buyers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports buyers or properties from csv file.\n"
            + "Examples: " + COMMAND_WORD  + " " + BUYERS + "\n"
            + COMMAND_WORD  + " " + PROPERTIES + "\n";

    public static final String MESSAGE_CANCELLED = "Export %s was cancelled.";

    public static final String MESSAGE_FAILURE = "Export %s failed to save the file.";

    public static final String MESSAGE_SUCCESS = "Successfully exported %s";

    public static CommandResult executePropertiesCancelled() {
        return new CommandResult(String.format(MESSAGE_CANCELLED, PROPERTIES));
    }

    public static CommandResult executeBuyersCancelled() {
        return new CommandResult(String.format(MESSAGE_CANCELLED, BUYERS));
    }

    public static CommandResult executePropertiesFailure() {
        return new CommandResult(String.format(MESSAGE_FAILURE, PROPERTIES));
    }

    public static CommandResult executeBuyersFailure() {
        return new CommandResult(String.format(MESSAGE_FAILURE, BUYERS));
    }

    public static CommandResult executePropertiesSuccess() {
        return new CommandResult(String.format(MESSAGE_SUCCESS, PROPERTIES));
    }

    public static CommandResult executeBuyersSuccess() {
        return new CommandResult(String.format(MESSAGE_SUCCESS, BUYERS));
    }
}
