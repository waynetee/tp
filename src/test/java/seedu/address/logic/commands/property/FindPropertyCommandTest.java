package seedu.address.logic.commands.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PROPERTIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperties.P_ALICE;
import static seedu.address.testutil.TypicalProperties.P_BENSON;
import static seedu.address.testutil.TypicalProperties.P_CARL;
import static seedu.address.testutil.TypicalProperties.P_ELLE;
import static seedu.address.testutil.TypicalProperties.P_FIONA;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.field.ContainsTagsPredicate;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;


public class FindPropertyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate<Property> firstPredicate =
                new NameContainsKeywordsPredicate<>(Collections.singletonList("first"));
        NameContainsKeywordsPredicate<Property> secondPredicate =
                new NameContainsKeywordsPredicate<>(Collections.singletonList("second"));

        FindPropertyCommand findFirstCommand = new FindPropertyCommand(firstPredicate);
        FindPropertyCommand findSecondCommand = new FindPropertyCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPropertyCommand findFirstCommandCopy = new FindPropertyCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different property -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_multipleKeywords_multiplePropertiesFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate<Property> predicate = preparePredicate("Kurz Elle Kunz");
        FindPropertyCommand command = new FindPropertyCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(P_CARL, P_ELLE, P_FIONA), model.getFilteredPropertyList());
    }

    @Test
    public void execute_tags_noPropertiesFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate<Property> namePredicate = new NameContainsKeywordsPredicate<>();
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("condo"));
        tags.add(new Tag("hdb"));
        tags.add(new Tag("mrt"));
        ContainsTagsPredicate<Property> tagsPredicate = new ContainsTagsPredicate<>(tags);
        FindPropertyCommand command = new FindPropertyCommand(namePredicate, tagsPredicate);
        expectedModel.updateFilteredPropertyList(namePredicate.and(tagsPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPropertyList());
    }

    @Test
    public void execute_tags_multiplePropertiesFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate<Property> namePredicate = new NameContainsKeywordsPredicate<>();
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("condo"));
        ContainsTagsPredicate<Property> tagsPredicate = new ContainsTagsPredicate<>(tags);
        FindPropertyCommand command = new FindPropertyCommand(namePredicate, tagsPredicate);
        expectedModel.updateFilteredPropertyList(namePredicate.and(tagsPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(P_ALICE, P_BENSON), model.getFilteredPropertyList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate<Property> preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }
}
