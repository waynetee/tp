package seedu.address.testutil;
import seedu.address.model.AddressBook;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

public class TypicalAddressBook {
    private TypicalAddressBook() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical properties and buyers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Property property : TypicalProperties.getTypicalProperties()) {
            ab.addProperty(property);
        }

        for (Buyer buyer : TypicalBuyers.getTypicalBuyers()) {
            ab.addBuyer(buyer);
        }

        return ab;
    }
}
