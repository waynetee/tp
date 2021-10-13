package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Property> filteredProperties;
    private final FilteredList<Buyer> filteredBuyers;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredProperties = new FilteredList<>(this.addressBook.getPropertyList());
        filteredBuyers = new FilteredList<>(this.addressBook.getBuyerList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasProperty(Property property) {
        requireNonNull(property);
        return addressBook.hasProperty(property);
    }

    @Override
    public void deleteProperty(Property target) {
        addressBook.removeProperty(target);
    }

    @Override
    public void addProperty(Property property) {
        addressBook.addProperty(property);
        updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
    }

    @Override
    public void setProperty(Property target, Property editedProperty) {
        requireAllNonNull(target, editedProperty);

        addressBook.addProperty(target, editedProperty);
    }

    @Override
    public boolean hasBuyer(Buyer buyer) {
        requireNonNull(buyer);
        return addressBook.hasBuyer(buyer);
    }

    @Override
    public void deleteBuyer(Buyer target) {
        addressBook.removeBuyer(target);
    }

    @Override
    public void addBuyer(Buyer buyer) {
        addressBook.addBuyer(buyer);
        updateFilteredBuyerList(PREDICATE_SHOW_ALL_BUYERS);
    }

    @Override
    public void setBuyer(Buyer target, Buyer editedBuyer) {
        requireAllNonNull(target, editedBuyer);

        addressBook.addBuyer(target, editedBuyer);
    }

    @Override
    public void sortPropertiesPrice() {
        addressBook.sortPropertiesPrice();
        updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
    }

    @Override
    public void sortPropertiesPriceDesc() {
        addressBook.sortPropertiesPriceDesc();
        updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
    }

    @Override
    public void sortBuyersPrice() {
        addressBook.sortBuyersPrice();
        updateFilteredBuyerList(PREDICATE_SHOW_ALL_BUYERS);
    }

    @Override
    public void sortBuyersPriceDesc() {
        addressBook.sortBuyersPriceDesc();
        updateFilteredBuyerList(PREDICATE_SHOW_ALL_BUYERS);
    }

    @Override
    public void sortPropertiesName() {
        addressBook.sortPropertiesName();
        updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
    }

    @Override
    public void sortPropertiesNameDesc() {
        addressBook.sortPropertiesNameDesc();
        updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
    }

    @Override
    public void sortBuyersName() {
        addressBook.sortBuyersName();
        updateFilteredBuyerList(PREDICATE_SHOW_ALL_BUYERS);
    }

    @Override
    public void sortBuyersNameDesc() {
        addressBook.sortBuyersNameDesc();
        updateFilteredBuyerList(PREDICATE_SHOW_ALL_BUYERS);
    }

    //=========== Filtered Property List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Property} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Property> getFilteredPropertyList() {
        return filteredProperties;
    }

    @Override
    public void updateFilteredPropertyList(Predicate<Property> predicate) {
        requireNonNull(predicate);
        filteredProperties.setPredicate(predicate);
    }

    //=========== Filtered Buyer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Buyer} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Buyer> getFilteredBuyerList() {
        return filteredBuyers;
    }

    @Override
    public void updateFilteredBuyerList(Predicate<Buyer> predicate) {
        requireNonNull(predicate);
        filteredBuyers.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredProperties.equals(other.filteredProperties);
    }

}
