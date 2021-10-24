package seedu.address.model.field;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.property.Nameable;

/**
 * Tests that a {@code Nameable}'s {@code Name} matches any of the keywords given.
 * If no keywords are given, defaults to match (i.e. predicate returns true).
 */
public class NameContainsKeywordsPredicate<T extends Nameable> implements Predicate<T> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate() {
        this.keywords = Collections.emptyList();
    }

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(T nameable) {
        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(nameable.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate<?>) other).keywords)); // state check
    }

}
