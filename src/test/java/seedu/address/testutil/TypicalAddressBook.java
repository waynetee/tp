package seedu.address.testutil;

import static seedu.address.testutil.TypicalProperties.getTypicalProperties;

import seedu.address.model.AddressBook;
import seedu.address.model.property.Property;


public class TypicalAddressBook {
    private TypicalAddressBook() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical properties and buyers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Property property : getTypicalProperties()) {
            ab.addProperty(property);
        }
        return ab;
    }
}
