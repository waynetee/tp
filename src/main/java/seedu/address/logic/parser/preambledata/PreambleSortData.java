package seedu.address.logic.parser.preambledata;

import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;

public class PreambleSortData extends PreambleActorData {
    public static final String MESSAGE_PREAMBLE_FIELD = PreambleActorData.MESSAGE_PREAMBLE_FIELD
            + " price/name asc/desc";
    public static final int PREAMBLE_FIELD_COUNT = 3;
    private final SortType sortType;
    private final SortDirection sortDirection;

    public PreambleSortData(Actor actor, SortType sortType, SortDirection sortDirection) {
        super(actor);
        this.sortType = sortType;
        this.sortDirection = sortDirection;
    }

    public SortType getSortType() {
        return sortType;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }
}
