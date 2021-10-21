package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showBuyerAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPropertyAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalBuyers.B_CARL;
import static seedu.address.testutil.TypicalBuyers.B_DANIEL;
import static seedu.address.testutil.TypicalBuyers.B_ELLE;
import static seedu.address.testutil.TypicalBuyers.B_FIONA;
import static seedu.address.testutil.TypicalBuyers.B_GEORGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalProperties.P_ALICE;
import static seedu.address.testutil.TypicalProperties.P_BENSON;
import static seedu.address.testutil.TypicalProperties.P_CARL;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.buyer.MatchBuyerCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.property.MatchPropertyCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class MatchOneToManyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MatchPropertyCommand(null));
        assertThrows(NullPointerException.class, () -> new MatchBuyerCommand(null));
    }


    @Test
    public void execute_validPropertyIndex_sortsAndFiltersBuyersCorrectly() {
        MatchPropertyCommand command = new MatchPropertyCommand(Index.fromOneBased(3)); // P_CARL
        try {
            command.execute(model);
        } catch (CommandException e) {
            fail("This should not be reached. Index should be valid.");
        }

        assertEquals(List.of(P_CARL), model.getFilteredPropertyList());
        // ordered from the highest maxPrice to the lowest maxPrice
        // furthermore, B_ALICE and B_BENSON's maxPrices are strictly lower than P_CARL's selling price
        assertEquals(Arrays.asList(B_GEORGE, B_FIONA, B_ELLE, B_DANIEL, B_CARL),
                model.getFilteredBuyerList());
    }

    @Test
    public void execute_validBuyerIndex_sortsAndFiltersPropertiesCorrectly() {
        MatchBuyerCommand command = new MatchBuyerCommand(Index.fromOneBased(3)); // B_CARL
        try {
            command.execute(model);
        } catch (CommandException e) {
            fail("This should not be reached. Index should be valid.");
        }

        assertEquals(List.of(B_CARL), model.getFilteredBuyerList());
        // ordered from the lowest selling price to the highest price
        // furthermore, P_DANIEL to P_GEORGE's selling prices are all strictly higher than B_CARL's maxPrice
        assertEquals(Arrays.asList(P_ALICE, P_BENSON, P_CARL), model.getFilteredPropertyList());
    }

    @Test
    public void execute_invalidPropertyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPropertyList().size() + 1);
        MatchPropertyCommand matchPropertyCommand = new MatchPropertyCommand(outOfBoundIndex);

        assertCommandFailure(matchPropertyCommand, model, Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
    }

    /**
     * Call match command on filtered list where index is larger than size of filtered list,
     * but smaller than size of address book.
     */
    @Test
    public void execute_invalidPropertyIndexFilteredList_failure() {
        showPropertyAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPropertyList().size());

        MatchPropertyCommand matchPropertyCommand = new MatchPropertyCommand(outOfBoundIndex);

        assertCommandFailure(matchPropertyCommand, model, Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidBuyerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBuyerList().size() + 1);
        MatchBuyerCommand matchBuyerCommand = new MatchBuyerCommand(outOfBoundIndex);

        assertCommandFailure(matchBuyerCommand, model, Messages.MESSAGE_INVALID_BUYER_DISPLAYED_INDEX);
    }

    /**
     * Call match command on filtered list where index is larger than size of filtered list,
     * but smaller than size of address book.
     */
    @Test
    public void execute_invalidBuyerIndexFilteredList_failure() {
        showBuyerAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getBuyerList().size());

        MatchBuyerCommand matchBuyerCommand = new MatchBuyerCommand(outOfBoundIndex);

        assertCommandFailure(matchBuyerCommand, model, Messages.MESSAGE_INVALID_BUYER_DISPLAYED_INDEX);
    }

}
