package seedu.address.logic.parser.preambledata;

import seedu.address.commons.core.index.Index;

public class PreambleIndexData extends PreambleActorData {
    public static final String MESSAGE_PREAMBLE_FIELD = PreambleActorData.MESSAGE_PREAMBLE_FIELD + "INDEX";
    public static final int PREAMBLE_FIELD_COUNT = 2;
    private final Index index;

    /**
     * Constructs a PreambleIndexData object using given {@code actor} and {@code index}.
     *
     * @param actor Actor object.
     * @param index Index object.
     */
    public PreambleIndexData(Actor actor, Index index) {
        super(actor);
        this.index = index;
    }

    /**
     * Returns the {@code Index} of the PreambleIndexData object.
     */
    public Index getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PreambleIndexData)) {
            return false;
        }

        PreambleIndexData otherPreamble = (PreambleIndexData) other;
        return getIndex().equals(otherPreamble.getIndex()) && super.equals(other);
    }
}
