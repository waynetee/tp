package seedu.address.model.property;

import seedu.address.model.property.exceptions.DuplicateListableException;
import seedu.address.model.property.exceptions.DuplicateMatchException;
import seedu.address.model.property.exceptions.ListableNotFoundException;
import seedu.address.model.property.exceptions.MatchNotFoundException;

public class UniqueMatchList extends UniqueList<Match> {
    @Override
    public void add(Match toAdd) {
        try {
            super.add(toAdd);
        } catch (DuplicateListableException e) {
            throw new DuplicateMatchException();
        }
    }

    @Override
    public void remove(Match toRemove) {
        try {
            super.remove(toRemove);
        } catch (ListableNotFoundException e) {
            throw new MatchNotFoundException();
        }
    }
}
