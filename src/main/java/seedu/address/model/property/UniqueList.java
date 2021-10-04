package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.property.exceptions.DuplicateListableException;
import seedu.address.model.property.exceptions.ListableNotFoundException;

/**
 * A abstract list that enforces uniqueness between its elements and does not allow nulls.
 * A {@code Listable} item is considered unique by comparing using {@code Listable#isSameListable(Listable)}.
 * As such, adding and updating of properties uses Listable#isSameListable(Listable) for equality
 * so as to ensure that the property being added or updated is unique in terms of identity in the
 * UniqueListableList. However, the removal of a property uses Listable#equals(Object) so
 * as to ensure that the property with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Listable#isSameListable(Listable)
 */
public class UniqueList<Item extends Listable> implements Iterable<Item> {

    private final ObservableList<Item> internalList = FXCollections.observableArrayList();
    private final ObservableList<Item> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent property as the given argument.
     */
    public boolean contains(Item toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameListable);
    }

    /**
     * Adds a property to the list.
     * The property must not already exist in the list.
     */
    public void add(Item toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateListableException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the property {@code target} in the list with {@code editedListable}.
     * {@code target} must exist in the list.
     * The property identity of {@code editedListable} must not be the same as another existing property in the list.
     */
    public void setListable(Item target, Item editedListable) {
        requireAllNonNull(target, editedListable);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ListableNotFoundException();
        }

        if (!target.isSameListable(editedListable) && contains(editedListable)) {
            throw new DuplicateListableException();
        }

        internalList.set(index, editedListable);
    }

    /**
     * Removes the equivalent property from the list.
     * The property must exist in the list.
     */
    public void remove(Listable toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ListableNotFoundException();
        }
    }

    public void setProperties(UniqueList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code properties}.
     * {@code properties} must not contain duplicate properties.
     */
    public void setProperties(List<Item> properties) {
        requireAllNonNull(properties);
        if (!propertiesAreUnique(properties)) {
            throw new DuplicateListableException();
        }

        internalList.setAll(properties);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Item> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Item> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueList // instanceof handles nulls
                        && internalList.equals(((UniqueList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code properties} contains only unique properties.
     */
    private boolean propertiesAreUnique(List<Item> properties) {
        for (int i = 0; i < properties.size() - 1; i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                if (properties.get(i).isSameListable(properties.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
