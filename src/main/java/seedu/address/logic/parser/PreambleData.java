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
