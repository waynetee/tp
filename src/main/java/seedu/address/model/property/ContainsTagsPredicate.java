package seedu.address.model.property;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Property}'s {@code Tag}s is a superset of all the given tags.
 */
public class ContainsTagsPredicate implements Predicate<Property> {

    private final Set<Tag> tags;

    public ContainsTagsPredicate(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Property property) {
        Set<Tag> propertyTags = property.getTags();
        return propertyTags.containsAll(this.tags);
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this ||
            (other instanceof ContainsTagsPredicate) && 
            tags.equals(((ContainsTagsPredicate) other).tags);
    }
}
