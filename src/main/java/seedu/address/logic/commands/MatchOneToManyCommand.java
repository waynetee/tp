package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;

/**
 * Matches a single entity (property or buyer) to others in the address book.
 */
public abstract class MatchOneToManyCommand extends MatchCommand {

    protected final Index targetIndex;

    /**
     * Constructs a Match command with the entity at the given {@code index}.
     *
     * @param targetIndex Index the entity is displayed at.
     */
    public MatchOneToManyCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchOneToManyCommand // instanceof handles nulls
                && targetIndex.equals(((MatchOneToManyCommand) other).targetIndex)); // state check
    }
}
