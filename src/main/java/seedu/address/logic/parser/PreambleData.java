package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

public class PreambleData {
    public static final String MESSAGE_INVALID_ACTOR = "Only property or buyer can be specified as target.";

    enum Actor {
        PROPERTY,
        BUYER,
    }

    private final Actor actor;
    private final Index index;

    /**
     * Constructs a PreambleData object using given string representing {@code actor} and Index {@code index}.
     * @param actor String that represents an {@code actor}.
     * @param index Index of object in a list.
     * @throws ParseException
     */
    public PreambleData(String actor, Index index) throws ParseException {
        if (actor.equals("property")) {
            this.actor = Actor.PROPERTY;
        } else if (actor.equals("buyer")) {
            this.actor = Actor.BUYER;
        } else {
            throw new ParseException(MESSAGE_INVALID_ACTOR);
        }
        this.index = index;
    }

    /**
     * Constructs a PreambleData object using given {@code actor} and {@code index}.
     * @param actor Actor object.
     * @param index Index of object in a list.
     * @throws ParseException
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
