package seedu.address.model.property;

public interface Listable {

    /**
     * For UniqueList to identify weakly equal items.
     */
    boolean isSameListable(Listable item);

}
