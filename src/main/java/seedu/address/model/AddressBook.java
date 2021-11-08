package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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
    private final UniquePropertyList currProperties;
    private final UniqueBuyerList buyers;
    private final UniqueBuyerList currBuyers;
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
        currProperties = new UniquePropertyList();
        buyers = new UniqueBuyerList();
        currBuyers = new UniqueBuyerList();
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
     * Replaces the contents of the current property list with {@code properties}.
     * {@code properties} must not contain duplicate properties.
     */
    public void setCurrProperties(List<Property> properties) {
        this.currProperties.setProperties(properties);
    }

    /**
     * Replaces the contents of both the current and actual property list with {@code properties}.
     * {@code properties} must not contain duplicate properties.
     */
    public void setAllProperties(List<Property> properties) {
        setProperties(properties);
        setCurrProperties(properties);
    }

    /**
     * Replaces the contents of the buyer list with {@code buyers}.
     * {@code buyers} must not contain duplicate buyers.
     */
    public void setBuyers(List<Buyer> buyers) {
        this.buyers.setBuyers(buyers);
    }

    /**
     * Replaces the contents of the current buyer list with {@code buyers}.
     * {@code buyers} must not contain duplicate buyers.
     */
    public void setCurrBuyers(List<Buyer> buyers) {
        this.currBuyers.setBuyers(buyers);
    }

    /**
     * Replaces the contents of both the current and actual buyer list with {@code buyers}.
     * {@code buyers} must not contain duplicate buyers.
     */
    public void setAllBuyers(List<Buyer> buyers) {
        setBuyers(buyers);
        setCurrBuyers(buyers);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setBuyers(newData.getBuyerList());
        setCurrBuyers(newData.getCurrBuyerList());
        setProperties(newData.getPropertyList());
        setCurrProperties(newData.getCurrPropertyList());
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
        currProperties.add(p);
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
        currProperties.setProperty(target, editedProperty);
    }

    /**
     * Adds a property to the top of the address book.
     * The property must not already exist in the address book.
     */
    public void addNewProperty(Property p) {
        properties.addFront(p);
        currProperties.addFront(p);
    }

    /**
     * Adds a list of properties to the address book.
     * The properties must not already exist in the address book.
     */
    public void addAllProperties(List<Property> p) {
        properties.addAll(p);
        currProperties.addAll(p);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeProperty(Property key) {
        properties.remove(key);
        currProperties.remove(key);
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
        currBuyers.add(b);
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
        currBuyers.setBuyer(target, editedBuyer);
    }

    /**
     * Adds a buyer to the top of the address book.
     * The buyer must not already exist in the address book.
     */
    public void addNewBuyer(Buyer b) {
        buyers.addFront(b);
        currBuyers.addFront(b);
    }

    /**
     * Adds a list of buyers to the address book.
     * The buyers must not already exist in the address book.
     */
    public void addAllBuyers(List<Buyer> b) {
        buyers.addAll(b);
        currBuyers.addAll(b);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeBuyer(Buyer key) {
        buyers.remove(key);
        currBuyers.remove(key);
    }

    /**
     * Resets the {@code currProperties} to the actual {@code properties} list.
     */
    public void resetProperties() {
        currProperties.setProperties(properties);
    }

    /**
     * Filters the {@code currProperties} based on the given property {@code Predicate}.
     *
     * @param predicate {@code Property} predicate
     */
    public void filterProperties(Predicate<Property> predicate) {
        currProperties.filter(predicate);
    }

    /**
     * Resets the {@code currBuyers} to the actual {@code buyers} list.
     */
    public void resetBuyers() {
        currBuyers.setBuyers(buyers);
    }

    /**
     * Filters the {@code currBuyer} based on the given property {@code Predicate}.
     *
     * @param predicate {@code Buyer} predicate
     */
    public void filterBuyers(Predicate<Buyer> predicate) {
        currBuyers.filter(predicate);
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
     * Sorts currently displayed properties by the given {@code comparator}.
     */
    public void sortProperties(Comparator<Property> comparator) {
        currProperties.sortListables(comparator);
    }

    /**
     * Sorts currently displayed properties by the given {@code sortType} and {@code sortDirection}.
     */
    public void sortProperties(SortType sortType, SortDirection sortDirection) {
        currProperties.sort(sortType, sortDirection);
    }

    /**
     * Sorts currently displayed buyers by the given {@code comparator}.
     */
    public void sortBuyers(Comparator<Buyer> comparator) {
        currBuyers.sortListables(comparator);
    }

    /**
     * Sorts currently displayed properties by the given {@code sortType} and {@code sortDirection}.
     */
    public void sortBuyers(SortType sortType, SortDirection sortDirection) {
        currBuyers.sort(sortType, sortDirection);
    }
    //// util methods

    @Override
    public String toString() {
        return properties.asUnmodifiableObservableList().size() + " properties"
                + buyers.asUnmodifiableObservableList().size() + " buyers";
    }

    @Override
    public ObservableList<Property> getPropertyList() {
        return properties.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Property> getCurrPropertyList() {
        return currProperties.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Buyer> getBuyerList() {
        return buyers.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Buyer> getCurrBuyerList() {
        return currBuyers.asUnmodifiableObservableList();
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
                && currProperties.equals(((AddressBook) other).currProperties)
                && buyers.equals(((AddressBook) other).buyers)
                && currBuyers.equals(((AddressBook) other).currBuyers)
                && matches.equals(((AddressBook) other).matches));
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyers, properties);
    }
}
