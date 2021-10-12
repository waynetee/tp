package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;

public class PreambleData {

    enum Actor {
        PROPERTY,
        BUYER,
    }

    private final Actor actor;
    private final Index index;

    /**
     * Constructs a PreambleData object using given {@code actor} and {@code index}.
     *
     * @param actor Actor object.
     * @param index Index of object in a list.
     */
    public PreambleData(Actor actor, Index index) {
        this.actor = actor;
        this.index = index;
    }

    public Index getIndex() {
        return index;
    }

    public Actor getActor() {
        return actor;
    }
}
