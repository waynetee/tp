package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Match;
import seedu.address.model.property.Property;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the properties list.
     * This list will not contain any duplicate properties.
     */
    ObservableList<Property> getPropertyList();

    /**
     * Returns an unmodifiable view of the current properties list.
     * This list will not contain any duplicate properties.
     */
    ObservableList<Property> getCurrPropertyList();

    /**
     * Returns an unmodifiable view of the buyers list.
     * This list will not contain any duplicate buyers.
     */
    ObservableList<Buyer> getBuyerList();

    /**
     * Returns an unmodifiable view of the current buyers list.
     * This list will not contain any duplicate buyers.
     */
    ObservableList<Buyer> getCurrBuyerList();

    /**
     * Returns an unmodifiable view of the match list.
     * This list will not contain any duplicate matches.
     */
    ObservableList<Match> getMatchList();
}
