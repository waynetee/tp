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
     * Calculates the similarity between otherTagSet and tagSet.
     * @param tagSet
     * @param otherTagSet
     * @return
     */
    public Integer calculateTagDistance(Set<Tag> tagSet, Set<Tag> otherTagSet) {
        requireAllNonNull(tagSet, otherTagSet);
        Set<Tag> copy = new HashSet<>(otherTagSet);
        copy.retainAll(tagSet);
        return copy.size();
    }
}
