package seedu.address.model.property;

import java.util.List;

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
public class UniqueBuyerList extends UniqueList<Buyer> {

    public void setBuyers(List<Buyer> buyers) {
        super.setListables(buyers);
    }

    public void setBuyers(UniqueBuyerList replacement) {
        super.setListables(replacement);
    }

    public void setBuyer(Buyer target, Buyer editedBuyer) {
        super.setListable(target, editedBuyer);
    }
}
