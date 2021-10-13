package seedu.address.logic.parser.preambledata;

import seedu.address.commons.core.index.Index;

public class PreambleIndexData extends PreambleActorData {
    public static final String MESSAGE_PREAMBLE_FIELD = PreambleActorData.MESSAGE_PREAMBLE_FIELD + "INDEX";
    public static final int PREAMBLE_FIELD_COUNT = 2;
    private final Index index;

    /**
     * Constructs a PreambleData object using given {@code actor}.
     *
     * @param actor Actor object.
     */
    public PreambleIndexData(Actor actor, Index index) {
        super(actor);
        this.index = index;
    }

    public Index getIndex(){
        return index;
    }
}
