package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperties.P_ALICE;
import static seedu.address.testutil.TypicalProperties.P_BOB;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Match;
import seedu.address.model.property.Property;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.testutil.PropertyBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPropertyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateProperties_throwsDuplicatePropertyException() {
        // Two properties with the same identity fields
        Property editedAlice = new PropertyBuilder(P_ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Property> newProperties = Arrays.asList(P_BOB, editedAlice);
        AddressBookStub newData = new AddressBookStub(newProperties);

        assertThrows(DuplicatePropertyException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasProperty_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasProperty(null));
    }

    @Test
    public void hasProperty_propertyNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasProperty(P_ALICE));
    }

    @Test
    public void hasProperty_propertyInAddressBook_returnsTrue() {
        addressBook.addProperty(P_ALICE);
        assertTrue(addressBook.hasProperty(P_ALICE));
    }

    @Test
    public void hasProperty_propertyWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addProperty(P_BOB);
        Property editedAlice = new PropertyBuilder(P_ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasProperty(editedAlice));
    }

    @Test
    public void getPropertyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPropertyList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose properties list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Property> properties = FXCollections.observableArrayList();
        private final ObservableList<Property> currProperties = FXCollections.observableArrayList();
        private final ObservableList<Buyer> buyers = FXCollections.observableArrayList();
        private final ObservableList<Buyer> currBuyers = FXCollections.observableArrayList();
        private final ObservableList<Match> matches = FXCollections.observableArrayList();

        AddressBookStub(Collection<Property> properties) {
            this.properties.setAll(properties);
        }

        @Override
        public ObservableList<Property> getPropertyList() {
            return properties;
        }

        @Override
        public ObservableList<Buyer> getBuyerList() {
            return buyers;
        }

        @Override
        public ObservableList<Property> getCurrPropertyList() {
            return currProperties;
        }

        @Override
        public ObservableList<Buyer> getCurrBuyerList() {
            return currBuyers;
        }

        @Override
        public ObservableList<Match> getMatchList() {
            return matches;
        }
    }

}
