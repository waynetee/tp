package seedu.address.logic.commands.buyer;

import org.junit.jupiter.api.Test;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBuyers.getTypicalBuyersSortedNameAsc;
import static seedu.address.testutil.TypicalBuyers.getTypicalBuyersSortedNameDesc;
import static seedu.address.testutil.TypicalBuyers.getTypicalBuyersSortedPriceAsc;
import static seedu.address.testutil.TypicalBuyers.getTypicalBuyersSortedPriceDesc;

public class SortBuyerCommandTest {
    private AddressBook addressBook = new AddressBook();
    private Model model = new ModelManager(addressBook, new UserPrefs());

    @Test
    public void equals() {
        SortBuyerCommand sortPriceAscCommand = new SortBuyerCommand(SortType.PRICE, SortDirection.ASC);
        SortBuyerCommand sortNameAscCommand = new SortBuyerCommand(SortType.NAME, SortDirection.ASC);
        SortBuyerCommand sortPriceDescCommand = new SortBuyerCommand(SortType.PRICE, SortDirection.DESC);
        SortBuyerCommand sortNameDescCommand = new SortBuyerCommand(SortType.NAME, SortDirection.DESC);

        // same object -> returns true
        assertTrue(sortPriceAscCommand.equals(sortPriceAscCommand));

        // same values -> returns true
        SortBuyerCommand sortPriceAscCommandCopy = new SortBuyerCommand(SortType.PRICE, SortDirection.ASC);
        assertTrue(sortPriceAscCommand.equals(sortPriceAscCommandCopy));

        // different types -> returns false
        assertFalse(sortPriceAscCommand.equals(1));

        // null -> returns false
        assertFalse(sortPriceAscCommand.equals(null));

        // different sort type -> returns false
        assertFalse(sortPriceAscCommand.equals(sortNameAscCommand));

        // different sort dir -> returns false
        assertFalse(sortPriceAscCommand.equals(sortPriceDescCommand));

        // different sort type and dir-> returns false
        assertFalse(sortPriceAscCommand.equals(sortNameDescCommand));
    }

    @Test
    public void execute_sortPriceAsc_success() {
        addressBook.setBuyers(getTypicalBuyersSortedPriceDesc());
        model.setAddressBook(addressBook);
        SortBuyerCommand sortCommand = new SortBuyerCommand(SortType.PRICE, SortDirection.ASC);

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.setBuyers(getTypicalBuyersSortedPriceAsc());
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());
        String expectedMessage = String.format(SortBuyerCommand.MESSAGE_SUCCESS, SortType.PRICE, SortDirection.ASC);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortPriceDesc_success() {
        addressBook.setBuyers(getTypicalBuyersSortedPriceAsc());
        model.setAddressBook(addressBook);
        SortBuyerCommand sortCommand = new SortBuyerCommand(SortType.PRICE, SortDirection.DESC);

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.setBuyers(getTypicalBuyersSortedPriceDesc());
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());
        String expectedMessage = String.format(SortBuyerCommand.MESSAGE_SUCCESS, SortType.PRICE, SortDirection.DESC);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortNameAsc_success() {
        addressBook.setBuyers(getTypicalBuyersSortedNameDesc());
        model.setAddressBook(addressBook);
        SortBuyerCommand sortCommand = new SortBuyerCommand(SortType.NAME, SortDirection.ASC);

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.setBuyers(getTypicalBuyersSortedNameAsc());
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());
        String expectedMessage = String.format(SortBuyerCommand.MESSAGE_SUCCESS, SortType.NAME, SortDirection.ASC);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortNameDesc_success() {
        addressBook.setBuyers(getTypicalBuyersSortedNameAsc());
        model.setAddressBook(addressBook);
        SortBuyerCommand sortCommand = new SortBuyerCommand(SortType.NAME, SortDirection.DESC);

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.setBuyers(getTypicalBuyersSortedNameDesc());
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());
        String expectedMessage = String.format(SortBuyerCommand.MESSAGE_SUCCESS, SortType.NAME, SortDirection.DESC);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }
}
