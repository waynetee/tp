package seedu.address.model.property;

import java.util.Set;

import seedu.address.model.tag.Tag;

public interface Taggable {

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    Set<Tag> getTags();
}
