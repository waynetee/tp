package seedu.address.logic.commands;

/**
 * Imports entity (property or buyer) from csv file to address book.
 */
public abstract class ImportCommand extends CommandWithFile {
    public static final String COMMAND_WORD = "import";

    public static final String PROPERTIES = "properties";

    public static final String BUYERS = "buyers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports buyers or properties from csv file.\n\n"
            + "Parameters: ( property | buyer )\n"
            + "Example: " + COMMAND_WORD + " property";

    public static final String MESSAGE_CANCELLED = "Import %s was cancelled.";

    public static final String MESSAGE_IO_FAILURE = "Import %s failed to open the file. "
            + "PropertyWhiz does not have permissions to open the file.";

    public static final String MESSAGE_FORMAT_FAILURE = "Import %s failed to recognize the file. "
            + "The csv file provided is incorrectly formatted.";

    public static final String MESSAGE_DUPLICATE = "Import %s failed. Duplicate %s detected.";

    public static final String MESSAGE_SUCCESS = "Successfully imported %s.";

}
