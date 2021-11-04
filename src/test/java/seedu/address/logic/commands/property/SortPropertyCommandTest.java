package seedu.address.logic.commands.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertiesSortedNameAsc;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertiesSortedNameDesc;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertiesSortedPriceAsc;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertiesSortedPriceDesc;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;

public class SortPropertyCommandTest {
    private AddressBook addressBook = new AddressBook();
    private Model model = new ModelManager(addressBook, new UserPrefs());

    @Test
    public void equals() {
        SortPropertyCommand sortPriceAscCommand = new SortPropertyCommand(SortType.PRICE, SortDirection.ASC);
        SortPropertyCommand sortNameAscCommand = new SortPropertyCommand(SortType.NAME, SortDirection.ASC);
        SortPropertyCommand sortPriceDescCommand = new SortPropertyCommand(SortType.PRICE, SortDirection.DESC);
        SortPropertyCommand sortNameDescCommand = new SortPropertyCommand(SortType.NAME, SortDirection.DESC);

        // same object -> returns true
        assertTrue(sortPriceAscCommand.equals(sortPriceAscCommand));

        // same values -> returns true
        SortPropertyCommand sortPriceAscCommandCopy = new SortPropertyCommand(SortType.PRICE, SortDirection.ASC);
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
        addressBook.setAllProperties(getTypicalPropertiesSortedPriceDesc());
        model.setAddressBook(addressBook);
        SortPropertyCommand sortCommand = new SortPropertyCommand(SortType.PRICE, SortDirection.ASC);

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.setProperties(getTypicalPropertiesSortedPriceDesc());
        expectedAddressBook.setCurrProperties(getTypicalPropertiesSortedPriceAsc());
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());
        String expectedMessage = String.format(SortPropertyCommand.MESSAGE_SUCCESS, SortType.PRICE, SortDirection.ASC);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortPriceDesc_success() {
        addressBook.setAllProperties(getTypicalPropertiesSortedPriceAsc());
        model.setAddressBook(addressBook);
        SortPropertyCommand sortCommand = new SortPropertyCommand(SortType.PRICE, SortDirection.DESC);

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.setProperties(getTypicalPropertiesSortedPriceAsc());
        expectedAddressBook.setCurrProperties(getTypicalPropertiesSortedPriceDesc());
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());
        String expectedMessage = String.format(SortPropertyCommand.MESSAGE_SUCCESS, SortType.PRICE, SortDirection.DESC);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortNameAsc_success() {
        addressBook.setAllProperties(getTypicalPropertiesSortedNameDesc());
        model.setAddressBook(addressBook);
        SortPropertyCommand sortCommand = new SortPropertyCommand(SortType.NAME, SortDirection.ASC);

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.setProperties(getTypicalPropertiesSortedNameDesc());
        expectedAddressBook.setCurrProperties(getTypicalPropertiesSortedNameAsc());
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());
        String expectedMessage = String.format(SortPropertyCommand.MESSAGE_SUCCESS, SortType.NAME, SortDirection.ASC);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortNameDesc_success() {
        addressBook.setAllProperties(getTypicalPropertiesSortedNameAsc());
        model.setAddressBook(addressBook);
        SortPropertyCommand sortCommand = new SortPropertyCommand(SortType.NAME, SortDirection.DESC);

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.setProperties(getTypicalPropertiesSortedNameAsc());
        expectedAddressBook.setCurrProperties(getTypicalPropertiesSortedNameDesc());
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());
        String expectedMessage = String.format(SortPropertyCommand.MESSAGE_SUCCESS, SortType.NAME, SortDirection.DESC);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }
}
