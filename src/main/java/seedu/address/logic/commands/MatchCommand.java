package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.model.tag.Tag;

/**
 * Match entities (properties and buyers) in the address book.
 */
public abstract class MatchCommand extends Command {
    public static final String COMMAND_WORD = "match";

    protected final Index targetIndex;

    /**
     * Constructs a Match command with the entity at the given {@code index}.
     *
     * @param targetIndex Index the entity is displayed at.
     */
    public MatchCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Calculates the size of the intersection between {@code otherTagSet} and {@code tagSet}.
     *
     * @param tagSet A set of tags to be intersected with {@code otherTagSet}.
     * @param otherTagSet A set of tags to be intersected with {@code tagSet}.
     * @return Number of tags in the intersection.
     */
    public Integer calculateTagIntersection(Set<Tag> tagSet, Set<Tag> otherTagSet) {
        requireAllNonNull(tagSet, otherTagSet);
        Set<Tag> copy = new HashSet<>(otherTagSet);
        copy.retainAll(tagSet);
        return copy.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchCommand // instanceof handles nulls
                && targetIndex.equals(((MatchCommand) other).targetIndex)); // state check
    }
}
