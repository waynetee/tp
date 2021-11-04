package seedu.address.model.property;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;
import seedu.address.model.property.exceptions.DuplicateListableException;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.ListableNotFoundException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;


/**
 * A list of properties that enforces uniqueness between its elements and does not allow nulls.
 * A property is considered unique by comparing using {@code Property#isSameProperty(Property)}.
 * As such, adding and updating of properties uses Property#isSameProperty(Property) for equality
 * so as to ensure that the property being added or updated is unique in terms of identity in the
 * UniquePropertyList. However, the removal of a property uses Property#equals(Object) so
 * as to ensure that the property with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Property#isSameProperty(Property)
 */
public class UniquePropertyList extends UniqueList<Property> {

    @Override
    public void add(Property toAdd) {
        try {
            super.add(toAdd);
        } catch (DuplicateListableException e) {
            throw new DuplicatePropertyException();
        }
    }

    @Override
    public void addFront(Property toAdd) {
        try {
            super.addFront(toAdd);
        } catch (DuplicateListableException e) {
            throw new DuplicatePropertyException();
        }
    }

    @Override
    public void addAll(List<Property> toAdd) {
        try {
            super.addAll(toAdd);
        } catch (DuplicateListableException e) {
            throw new DuplicatePropertyException();
        }
    }

    @Override
    public void remove(Property toRemove) {
        try {
            super.remove(toRemove);
        } catch (ListableNotFoundException e) {
            throw new PropertyNotFoundException();
        }
    }

    public void setProperties(List<Property> properties) {
        try {
            super.setListables(properties);
        } catch (DuplicateListableException e) {
            throw new DuplicatePropertyException();
        }
    }

    public void setProperties(UniquePropertyList replacement) {
        try {
            super.setListables(replacement);
        } catch (DuplicateListableException e) {
            throw new DuplicatePropertyException();
        }
    }

    public void setProperty(Property target, Property editedProperty) {
        try {
            super.setListable(target, editedProperty);
        } catch (DuplicateListableException e) {
            throw new DuplicatePropertyException();
        } catch (ListableNotFoundException e) {
            throw new PropertyNotFoundException();
        }
    }

    /**
     * Sorts the list by the given {@code sortType} and {@code sortDirection}.
     */
    public void sort(SortType sortType, SortDirection sortDirection) {
        Comparator<Property> comparator = null;

        switch (sortType) {
        case PRICE:
            comparator = Property.getPriceComparator();
            break;
        case NAME:
            comparator = Property.getNameComparator();
            break;
        default:
            assert false;
        }

        if (sortDirection == SortDirection.DESC) {
            comparator = comparator.reversed();
        }
        super.sortListables(comparator);
    }
}
