package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Match;
import seedu.address.model.property.Property;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Property> PREDICATE_SHOW_ALL_PROPERTIES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Buyer> PREDICATE_SHOW_ALL_BUYERS = unused -> true;


    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a property with the same identity as {@code property} exists in the address book.
     */
    boolean hasProperty(Property property);

    /**
     * Deletes the given property.
     * The property must exist in the address book.
     */
    void deleteProperty(Property target);

    /**
     * Adds the given property.
     * {@code property} must not already exist in the address book.
     */
    void addProperty(Property property);

    /**
     * Adds the given property to the front of the list.
     * {@code property} must not already exist in the address book.
     */
    void addNewProperty(Property property);

    /**
     * Replaces the given property {@code target} with {@code editedProperty}.
     * {@code target} must exist in the address book.
     * The property identity of {@code editedProperty} must not be the same as another existing property
     * in the address book.
     */
    void setProperty(Property target, Property editedProperty);

    /**
     * Returns an unmodifiable view of the filtered property list
     */
    ObservableList<Property> getFilteredPropertyList();

    /**
     * Shows all the properties in the property list.
     */
    void showAllProperties();

    /**
     * Updates the filter of the filtered property list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPropertyList(Predicate<Property> predicate);

    /**
     * Updates and sorts the property list using the given {@code predicate} and {@code comparator}.
     */
    void updateFilteredAndSortedPropertyList(Predicate<Property> predicate, Comparator<Property> comparator);

    /**
     * Sorts the property list by the given {@code sortType} and {@code sortDirection}.
     */
    void sortProperties(SortType sortType, SortDirection sortDirection);

    /**
     * Returns true if a buyer with the same identity as {@code buyer} exists in the address book.
     */
    boolean hasBuyer(Buyer buyer);

    /**
     * Deletes the given buyer.
     * The buyer must exist in the address book.
     */
    void deleteBuyer(Buyer buyer);

    /**
     * Adds the given buyer.
     * {@code buyer} must not already exist in the address book.
     */
    void addBuyer(Buyer buyer);

    /**
     * Adds the given buyer to the front of the list.
     * {@code buyer} must not already exist in the address book.
     */
    void addNewBuyer(Buyer buyer);

    /**
     * Replaces the given buyer {@code target} with {@code editedBuyer}.
     * {@code target} must exist in the address book.
     * The buyer identity of {@code editedBuyer} must not be the same as another existing buyer
     * in the address book.
     */
    void setBuyer(Buyer target, Buyer editedBuyer);

    /**
     * Returns an unmodifiable view of the filtered buyer list
     */
    ObservableList<Buyer> getFilteredBuyerList();

    /**
     * Shows all the buyers in the property list.
     */
    void showAllBuyers();

    /**
     * Updates the filter of the filtered buyer list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBuyerList(Predicate<Buyer> predicate);

    /**
     * Updates and sorts the buyer list using the given {@code predicate} and {@code comparator}.
     */
    void updateFilteredAndSortedBuyerList(Predicate<Buyer> predicate, Comparator<Buyer> comparator);

    /**
     * Sorts the buyer list by the given buyer {@code sortType} and {@code sortDirection}.
     */
    void sortBuyers(SortType sortType, SortDirection sortDirection);

    /**
     * Returns an unmodifiable view of the match list.
     */
    ObservableList<Match> getMatchList();

    /**
     * Updates the list of Matches.
     */
    void setMatchList(List<Match> matches);

}
