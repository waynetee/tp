package seedu.address.model.property;

import seedu.address.model.tag.Tag;

import java.util.Set;

public interface Taggable {

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    Set<Tag> getTags();
}
