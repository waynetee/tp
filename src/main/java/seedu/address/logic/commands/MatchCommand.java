package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Match entities (properties and buyers) in the address book.
 */
public abstract class MatchCommand extends Command {
    public static final String COMMAND_WORD = "match";

    /**
     * Calculates the intersection between {@code otherTagSet} and {@code tagSet}.
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
}
