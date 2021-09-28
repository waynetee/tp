package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.property.Property;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withProperty("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Property} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withProperty(Property property) {
        addressBook.addProperty(property);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
