package seedu.address.logic.parser.preambledata;

public class PreambleActorData {

    public enum Actor {
        PROPERTY,
        BUYER,
    }
    public static final String MESSAGE_PREAMBLE_FIELD = "property/buyer";
    public static final int PREAMBLE_FIELD_COUNT = 1;

    private final Actor actor;

    /**
     * Constructs a PreambleData object using given {@code actor}.
     *
     * @param actor Actor object.
     */
    public PreambleActorData(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }
}
