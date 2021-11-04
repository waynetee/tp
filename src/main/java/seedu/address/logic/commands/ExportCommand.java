package seedu.address.logic.commands;

/**
 * Exports entity (property or buyer) from the address book to csv file.
 */
public abstract class ExportCommand extends CommandWithFile {
    public static final String COMMAND_WORD = "export";

    public static final String PROPERTIES = "properties";

    public static final String BUYERS = "buyers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports buyers or properties from csv file.\n\n"
            + "Parameters: ( property | buyer )\n"
            + "Example: " + COMMAND_WORD + " property";

    public static final String MESSAGE_CANCELLED = "Export %s was cancelled.";

    public static final String MESSAGE_FAILURE = "Export %s failed to save the file. "
            + "Try closing other apps that might have this file opened.";

    public static final String MESSAGE_SUCCESS = "Successfully exported %s";

}
