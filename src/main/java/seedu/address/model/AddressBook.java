package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Match;
import seedu.address.model.property.Property;
import seedu.address.model.property.UniqueBuyerList;
import seedu.address.model.property.UniqueMatchList;
import seedu.address.model.property.UniquePropertyList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameProperty comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePropertyList properties;
    private final UniqueBuyerList buyers;
    private final UniqueMatchList matches;

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
        matches = new UniqueMatchList();
    }

    public AddressBook() {
    }

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


    //// match level operations

    /**
     * Replaces the list of matches with the new list.
     */
    public void setMatches(List<Match> newMatches) {
        matches.setListables(newMatches);
    }

    /**
     * Returns true if a match with the same identity as {@code match} exists in the address book.
     */
    public boolean hasMatch(Match match) {
        requireNonNull(match);
        return matches.contains(match);
    }

    /**
     * Adds a match to the address book.
     * The match must not already exist in the address book.
     */
    public void addMatch(Match m) {
        matches.add(m);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeMatch(Match key) {
        matches.remove(key);
    }

    /**
     * Sorts properties by the given {@code sortType} and {@code sortDirection}.
     */
    public void sortProperties(SortType sortType, SortDirection sortDirection) {
        properties.sort(sortType, sortDirection);
    }

    /**
     * Sorts buyers  by the given {@code sortType} and {@code sortDirection}.
     */
    public void sortBuyers(SortType sortType, SortDirection sortDirection) {
        buyers.sort(sortType, sortDirection);
    }

    //// util methods

    @Override
    public String toString() {
        return properties.asUnmodifiableObservableList().size() + " properties";
        // TODO: refine later
        // TODO: add representation of the other buyer and match lists
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
    public ObservableList<Match> getMatchList() {
        return matches.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && properties.equals(((AddressBook) other).properties)
                && buyers.equals(((AddressBook) other).buyers)
                && matches.equals(((AddressBook) other).matches));
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyers, properties);
    }
}
