package seedu.address.model.field;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.property.Taggable;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Taggable}'s {@code Tag}s is a superset of all the given tags.
 */
public class ContainsTagsPredicate<T extends Taggable> implements Predicate<T> {

    private final Set<Tag> tags;

    public ContainsTagsPredicate() {
        this(Collections.emptySet());
    }

    public ContainsTagsPredicate(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(T taggable) {
        Set<Tag> taggableTags = taggable.getTags();
        return taggableTags.containsAll(this.tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || ((other instanceof ContainsTagsPredicate)
                && tags.equals(((ContainsTagsPredicate<?>) other).tags));
    }
}
