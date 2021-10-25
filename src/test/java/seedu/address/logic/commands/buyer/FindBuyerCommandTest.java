package seedu.address.logic.commands.buyer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_BUYERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalBuyers.B_BENSON;
import static seedu.address.testutil.TypicalBuyers.B_CARL;
import static seedu.address.testutil.TypicalBuyers.B_ELLE;
import static seedu.address.testutil.TypicalBuyers.B_FIONA;

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
import seedu.address.model.property.Buyer;
import seedu.address.model.tag.Tag;

public class FindBuyerCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate<Buyer> firstPredicate =
                new NameContainsKeywordsPredicate<>(Collections.singletonList("first"));
        NameContainsKeywordsPredicate<Buyer> secondPredicate =
                new NameContainsKeywordsPredicate<>(Collections.singletonList("second"));

        FindBuyerCommand findFirstCommand = new FindBuyerCommand(firstPredicate);
        FindBuyerCommand findSecondCommand = new FindBuyerCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindBuyerCommand findFirstCommandCopy = new FindBuyerCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different Buyer -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_multipleKeywords_multiplePropertiesFound() {
        String expectedMessage = String.format(MESSAGE_BUYERS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate<Buyer> predicate = preparePredicate("Kurz Elle Kunz");
        FindBuyerCommand command = new FindBuyerCommand(predicate);
        expectedModel.updateFilteredBuyerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(B_CARL, B_ELLE, B_FIONA), model.getFilteredBuyerList());
    }

    @Test
    public void execute_tags_noPropertiesFound() {
        String expectedMessage = String.format(MESSAGE_BUYERS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate<Buyer> namePredicate = new NameContainsKeywordsPredicate<>();
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("condo"));
        tags.add(new Tag("hdb"));
        tags.add(new Tag("mrt"));
        ContainsTagsPredicate<Buyer> tagsPredicate = new ContainsTagsPredicate<>(tags);
        FindBuyerCommand command = new FindBuyerCommand(namePredicate, tagsPredicate);
        expectedModel.updateFilteredBuyerList(namePredicate.and(tagsPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBuyerList());
    }

    @Test
    public void execute_tags_multiplePropertiesFound() {
        String expectedMessage = String.format(MESSAGE_BUYERS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate<Buyer> namePredicate = new NameContainsKeywordsPredicate<>();
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("condo"));
        ContainsTagsPredicate<Buyer> tagsPredicate = new ContainsTagsPredicate<>(tags);
        FindBuyerCommand command = new FindBuyerCommand(namePredicate, tagsPredicate);
        expectedModel.updateFilteredBuyerList(namePredicate.and(tagsPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(B_BENSON), model.getFilteredBuyerList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate<Buyer> preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }
}