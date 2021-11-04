package seedu.address.model.field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.model.property.Property;
import seedu.address.testutil.PropertyBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate<Property> firstPredicate =
                new NameContainsKeywordsPredicate<>(firstPredicateKeywordList);
        NameContainsKeywordsPredicate<Property> secondPredicate =
                new NameContainsKeywordsPredicate<>(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate<Property> firstPredicateCopy =
                new NameContainsKeywordsPredicate<>(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different property -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate<Property> predicate =
                new NameContainsKeywordsPredicate<>(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PropertyBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate<>(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PropertyBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate<>(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PropertyBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate<>(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PropertyBuilder().withName("Alice Bob").build()));
    }

    // NameContainsKeywordsPredicate returns true if no keywords are specified.
    // This test may be no longer relevant.
    @Disabled
    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate<Property> predicate =
                new NameContainsKeywordsPredicate<>(Collections.emptyList());
        assertFalse(predicate.test(new PropertyBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate<>(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PropertyBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate<>(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PropertyBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
