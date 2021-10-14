package seedu.address.model.property;

import java.util.Comparator;
import java.util.List;

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
     * Sorts the list by price in ascending order. If two {@code Property} have the same price,
     * the {@code Property} which has a lexically smaller name will have a lower priority.
     */
    public void sortPrice() {
        Comparator<Property> priceComparator = (b1, b2) -> {
            if (b1.getPrice().compareTo(b2.getPrice()) == 0) {
                return b1.getName().compareTo(b2.getName());
            }
            return b1.getPrice().compareTo(b2.getPrice());
        };
        super.sortListables(priceComparator);
    }

    /**
     * Sorts the list by price in descending order. If two {@code Property} have the same price,
     * the {@code Property} which has a lexically smaller name will have a lower priority.
     */
    public void sortPriceDesc() {
        Comparator<Property> priceComparator = (b1, b2) -> {
            if (b1.getPrice().compareTo(b2.getPrice()) == 0) {
                return b1.getName().compareTo(b2.getName());
            }
            return -b1.getPrice().compareTo(b2.getPrice());
        };
        super.sortListables(priceComparator);
    }

    /**
     * Sorts the list by name in ascending order.
     */
    public void sortName() {
        Comparator<Property> nameComparator = Comparator.comparing(Property::getName);
        super.sortListables(nameComparator);
    }

    /**
     * Sorts the list by name in descending order.
     */
    public void sortNameDesc() {
        Comparator<Property> nameComparator = Comparator.comparing(Property::getName).reversed();
        super.sortListables(nameComparator);
    }
}
