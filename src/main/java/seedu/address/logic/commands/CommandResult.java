package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.ui.UiElement;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** UiAction to be completed after execution */
    private final UiAction uiAction;

    /** Optional UiElement, which display will be handled by MainWindow. */
    private final Optional<UiElement> uiElement;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, UiAction uiAction, UiElement uiElement) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.uiAction = uiAction;
        this.uiElement = uiElement == null ? Optional.empty() : Optional.of(uiElement);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields and empty UiElement.
     */
    public CommandResult(String feedbackToUser, UiAction uiAction) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.uiAction = uiAction;
        this.uiElement = Optional.empty();
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

    public Optional<UiElement> getUiElement() {
        return uiElement;
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
        return String.format("%s uiAction: %s", feedbackToUser, uiAction);
    }

}
