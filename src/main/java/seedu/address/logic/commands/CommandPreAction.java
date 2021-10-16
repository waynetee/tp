package seedu.address.logic.commands;

/**
 * Represents the pre-action required before command execution
 */
public class CommandPreAction {
    private final boolean requiresFile;
    private final String fileDialogPrompt;
    private final boolean isFileSave;

    /**
     * Construct a CommandPreAction for a command that does not require a file.
     */
    public CommandPreAction() {
        this.requiresFile = false;
        this.fileDialogPrompt = "";
        this.isFileSave = false;
    }

    /**
     * Construct a CommandPreAction for a command that requires a file.
     */
    public CommandPreAction(String fileDialogPrompt, boolean isFileSave) {
        this.requiresFile = true;
        this.fileDialogPrompt = fileDialogPrompt;
        this.isFileSave = isFileSave;
    }

    public boolean requiresFile() {
        return requiresFile;
    }

    public String getFileDialogPrompt() {
        return fileDialogPrompt;
    }

    public boolean isFileSave() {
        return isFileSave;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CommandPreAction)) {
            return false;
        }
        CommandPreAction e = (CommandPreAction) other;
        return requiresFile == e.requiresFile && fileDialogPrompt == e.fileDialogPrompt && isFileSave == e.isFileSave;
    }
}
