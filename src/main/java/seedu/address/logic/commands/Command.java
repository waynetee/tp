package seedu.address.logic.commands;

/**
 * Represents a command which can either be SimpleCommand or CommandWithFile.
 */
public abstract class Command {

    /**
     * Returns true if the command can be run while the default UI (buyer list + property list) is being shown.
     */
    public boolean canRunInDefaultView() {
        return true;
    }

    /**
     * Returns true if the command can be run while the match auto UI (match list) is being shown.
     */
    public boolean canRunInMatchAutoView() {
        return false;
    }

}
