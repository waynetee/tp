package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;
import seedu.address.model.property.UniqueBuyerList;
import seedu.address.model.property.UniquePropertyList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameProperty comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePropertyList properties;
    private final UniqueBuyerList buyers;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        properties = new UniquePropertyList();
        buyers = new UniqueBuyerList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Properties in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the property list with {@code properties}.
     * {@code properties} must not contain duplicate properties.
     */
    public void setProperties(List<Property> properties) {
        this.properties.setProperties(properties);
    }

    /**
     * Replaces the contents of the buyer list with {@code buyers}.
     * {@code buyers} must not contain duplicate buyers.
     */
    public void setBuyers(List<Buyer> buyers) {
        this.buyers.setBuyers(buyers);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setBuyers(newData.getBuyerList());
        setProperties(newData.getPropertyList());
    }

    //// property-level operations

    /**
     * Returns true if a property with the same identity as {@code property} exists in the address book.
     */
    public boolean hasProperty(Property property) {
        requireNonNull(property);
        return properties.contains(property);
    }

    /**
     * Adds a property to the address book.
     * The property must not already exist in the address book.
     */
    public void addProperty(Property p) {
        properties.add(p);
    }

    /**
     * Replaces the given property {@code target} in the list with {@code editedProperty}.
     * {@code target} must exist in the address book.
     * The property identity of {@code editedProperty} must not be the same as another existing property
     * in the address book.
     */
    public void addProperty(Property target, Property editedProperty) {
        requireNonNull(editedProperty);

        properties.setProperty(target, editedProperty);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeProperty(Property key) {
        properties.remove(key);
    }

    //// buyer level operations

    /**
     * Returns true if a buyer with the same identity as {@code buyer} exists in the address book.
     */
    public boolean hasBuyer(Buyer buyer) {
        requireNonNull(buyer);
        return buyers.contains(buyer);
    }

    /**
     * Adds a buyer to the address book.
     * The buyer must not already exist in the address book.
     */
    public void addBuyer(Buyer b) {
        buyers.add(b);
    }

    /**
     * Replaces the given buyer {@code target} in the list with {@code editedBuyer}.
     * {@code target} must exist in the address book.
     * The buyer identity of {@code editedBuyer} must not be the same as another existing buyer
     * in the address book.
     */
    public void addBuyer(Buyer target, Buyer editedBuyer) {
        requireNonNull(editedBuyer);

        buyers.setBuyer(target, editedBuyer);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeBuyer(Buyer key) {
        buyers.remove(key);
    }

    /**
     * Sorts properties by price in ascending order.
     */
    public void sortPropertiesPrice() {
        properties.sortPrice();
    }

    /**
     * Sort properties by price in descending order.
     */
    public void sortPropertiesPriceDesc() {
        properties.sortPriceDesc();
    }

    /**
     * Sorts buyers by price in ascending order.
     */
    public void sortBuyersPrice() {
        buyers.sortPrice();
    }

    /**
     * Sorts buyers by price in descending order.
     */
    public void sortBuyersPriceDesc() {
        buyers.sortPriceDesc();
    }

    /**
     * Sorts properties by name in ascending order.
     */
    public void sortPropertiesName() {
        properties.sortName();
    }

    /**
     * Sorts properties by name in descending order.
     */
    public void sortPropertiesNameDesc() {
        properties.sortNameDesc();
    }

    /**
     * Sorts buyers by name in ascending order.
     */
    public void sortBuyersName() {
        buyers.sortName();
    }

    /**
     * Sorts buyers by name in descending order.
     */
    public void sortBuyersNameDesc() {
        buyers.sortNameDesc();
    }

    //// util methods

    @Override
    public String toString() {
        return properties.asUnmodifiableObservableList().size() + " properties";
        // TODO: refine later
    }

    @Override
    public ObservableList<Property> getPropertyList() {
        return properties.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Buyer> getBuyerList() {
        return buyers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && properties.equals(((AddressBook) other).properties)
                && buyers.equals(((AddressBook) other).buyers));
    }

    @Override
    public int hashCode() {
        return properties.hashCode();
    }
}
