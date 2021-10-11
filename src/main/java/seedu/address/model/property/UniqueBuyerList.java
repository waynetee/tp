package seedu.address.model.property;

import java.util.List;

import seedu.address.model.property.exceptions.BuyerNotFoundException;
import seedu.address.model.property.exceptions.DuplicateBuyerException;
import seedu.address.model.property.exceptions.DuplicateListableException;
import seedu.address.model.property.exceptions.ListableNotFoundException;


/**
 * A list of buyers that enforces uniqueness between its elements and does not allow nulls.
 * A buyer is considered unique by comparing using {@code Buyer#isSameBuyer(Buyer)}.
 * As such, adding and updating of buyers uses Buyer#isSameBuyer(Buyer) for equality
 * so as to ensure that the buyer being added or updated is unique in terms of identity in the
 * UniqueBuyerList. However, the removal of a buyer uses Buyer#equals(Object) so
 * as to ensure that the buyer with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Buyer#isSameBuyer(Buyer)
 */
public class UniqueBuyerList extends UniqueList<Buyer> {

    @Override
    public void add(Buyer toAdd) {
        try {
            super.add(toAdd);
        } catch (DuplicateListableException e) {
            throw new DuplicateBuyerException();
        }
    }

    @Override
    public void remove(Buyer toRemove) {
        try {
            super.remove(toRemove);
        } catch (ListableNotFoundException e) {
            throw new BuyerNotFoundException();
        }
    }

    public void setBuyers(List<Buyer> properties) {
        try {
            super.setListables(properties);
        } catch (DuplicateListableException e) {
            throw new DuplicateBuyerException();
        }
    }

    public void setBuyers(UniqueBuyerList replacement) {
        try {
            super.setListables(replacement);
        } catch (DuplicateListableException e) {
            throw new DuplicateBuyerException();
        }
    }

    public void setBuyer(Buyer target, Buyer editedBuyer) {
        try {
            super.setListable(target, editedBuyer);
        } catch (DuplicateListableException e) {
            throw new DuplicateBuyerException();
        } catch (ListableNotFoundException e) {
            throw new BuyerNotFoundException();
        }
    }
}
