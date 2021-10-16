package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalBuyers.B_CARL;
import static seedu.address.testutil.TypicalBuyers.B_DANIEL;
import static seedu.address.testutil.TypicalBuyers.B_ELLE;
import static seedu.address.testutil.TypicalBuyers.B_FIONA;
import static seedu.address.testutil.TypicalBuyers.B_GEORGE;
import static seedu.address.testutil.TypicalProperties.P_ALICE;
import static seedu.address.testutil.TypicalProperties.P_BENSON;
import static seedu.address.testutil.TypicalProperties.P_CARL;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.buyer.MatchBuyerCommand;
import seedu.address.logic.commands.property.MatchPropertyCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class MatchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MatchPropertyCommand(null));
        assertThrows(NullPointerException.class, () -> new MatchBuyerCommand(null));
    }


    @Test
    public void execute_validPropertyIndex_sortsAndFiltersBuyersCorrectly() {
        MatchPropertyCommand command = new MatchPropertyCommand(Index.fromOneBased(3)); // P_CARL
        command.execute(model);
        assertEquals(List.of(P_CARL), model.getFilteredPropertyList());
        // ordered from the highest maxPrice to the lowest maxPrice
        // furthermore, B_ALICE and B_BENSON's maxPrices are strictly lower than P_CARL's selling price
        assertEquals(Arrays.asList(B_GEORGE, B_FIONA, B_ELLE, B_DANIEL, B_CARL) ,model.getFilteredBuyerList());
    }

    @Test
    public void execute_validBuyerIndex_sortsAndFiltersPropertiesCorrectly() {
        MatchBuyerCommand command = new MatchBuyerCommand(Index.fromOneBased(3)); // B_CARL
        command.execute(model);
        assertEquals(List.of(B_CARL), model.getFilteredBuyerList());
        // ordered from the lowest selling price to the highest price
        // furthermore, P_DANIEL to P_GEORGE's selling prices are all strictly higher than B_CARL's maxPrice
        assertEquals(Arrays.asList(P_ALICE, P_BENSON, P_CARL) ,model.getFilteredPropertyList());
    }

}
