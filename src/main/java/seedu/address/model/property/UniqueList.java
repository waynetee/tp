package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.property.exceptions.DuplicateListableException;
import seedu.address.model.property.exceptions.ListableNotFoundException;

/**
 * A generic list that enforces uniqueness between its elements and does not allow nulls.
 * A {@code Listable} element is considered unique by comparing using {@code Listable#isSameListable(Listable)}.
 * As such, adding and updating of elements uses Listable#isSameListable(Listable) for equality
 * so as to ensure that the element being added or updated is unique in terms of identity in the
 * UniqueListableList. However, the removal of an element uses Listable#equals(Object) so
 * as to ensure that the element with exactly the same fields will be removed.
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
     * Adds an element to the list.
     * The element must not already exist in the list.
     */
    public void add(Item toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateListableException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds an element to front of the list.
     * The element must not already exist in the list.
     */
    public void addFront(Item toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateListableException();
        }
        internalList.add(0, toAdd);
    }

    /**
     * Adds a collection of elements to front of the list.
     */
    public void addAll(List<Item> toAdd) {
        requireNonNull(toAdd);
        if (!listablesAreUnique(toAdd)) {
            throw new DuplicateListableException();
        }
        for (Item item: toAdd) {
            if (contains(item)) {
                throw new DuplicateListableException();
            }
        }
        internalList.addAll(0, toAdd);
    }

    /**
     * Replaces the element {@code target} in the list with {@code editedListable}.
     * {@code target} must exist in the list.
     * The element identity of {@code editedListable} must not be the same as another existing element in the list.
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
     * Removes the equivalent element from the list.
     * The element must exist in the list.
     */
    public void remove(Item toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ListableNotFoundException();
        }
    }

    public void setListables(UniqueList<Item> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code listables}.
     * {@code listables} must not contain duplicate elements.
     */
    public void setListables(List<Item> listables) {
        requireAllNonNull(listables);
        if (!listablesAreUnique(listables)) {
            throw new DuplicateListableException();
        }

        internalList.setAll(listables);
    }

    /**
     * Sorts the contents of this list with the given {@code comparator}.
     */
    public void sortListables(Comparator<Item> comparator) {
        internalList.sort(comparator);
    }

    /**
     * Filters the contents of this list with the given {@code pred}.
     */
    public void filter(Predicate<Item> pred) {
        List<Item> newList = internalList.stream().filter(pred).collect(Collectors.toList());
        internalList.retainAll(newList);
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
     * Returns true if {@code listables} contains only unique elements.
     */
    private boolean listablesAreUnique(List<Item> listables) {
        for (int i = 0; i < listables.size() - 1; i++) {
            for (int j = i + 1; j < listables.size(); j++) {
                if (listables.get(i).isSameListable(listables.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
