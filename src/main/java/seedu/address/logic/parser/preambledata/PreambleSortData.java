package seedu.address.logic.parser.preambledata;

import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;

public class PreambleSortData extends PreambleActorData {
    public static final String MESSAGE_PREAMBLE_FIELD = PreambleActorData.MESSAGE_PREAMBLE_FIELD
            + " price/name asc/desc";
    public static final int PREAMBLE_FIELD_COUNT = 3;
    private final SortType sortType;
    private final SortDirection sortDirection;

    /**
     * Constructs a PreambleIndexData object using given {@code actor},
     * {@code sortType} and {@code sortDirection}.
     *
     * @param actor Actor object.
     * @param sortType SortType object.
     * @param sortDirection SortDirection object.
     */
    public PreambleSortData(Actor actor, SortType sortType, SortDirection sortDirection) {
        super(actor);
        this.sortType = sortType;
        this.sortDirection = sortDirection;
    }

    /**
     * Returns the sortType.
     */
    public SortType getSortType() {
        return sortType;
    }

    /**
     * Returns the sortDirection.
     */
    public SortDirection getSortDirection() {
        return sortDirection;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PreambleSortData)) {
            return false;
        }

        PreambleSortData otherPreamble = (PreambleSortData) other;
        return getSortDirection().equals(otherPreamble.getSortDirection())
                && getSortType().equals(otherPreamble.getSortType())
                && super.equals(other);
    }
}
