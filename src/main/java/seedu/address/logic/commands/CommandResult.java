package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** UiAction to be completed after execution */
    private final UiAction uiAction;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, UiAction uiAction) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.uiAction = uiAction;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, UiAction.NONE);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public UiAction getUiAction() {
        return uiAction;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && uiAction == otherCommandResult.uiAction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, uiAction);
    }

    @Override
    public String toString() {
        return String.format("%s showHelp:%b exit:%b", feedbackToUser, uiAction);
    }

}
