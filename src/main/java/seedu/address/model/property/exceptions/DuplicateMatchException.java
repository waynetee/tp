package seedu.address.model.property.exceptions;

/**
 * Signals that the operation will result in duplicate Matches (Matches are considered duplicates
 * if they have the same property and buyer).
 */
public class DuplicateMatchException extends DuplicateListableException {
    public DuplicateMatchException() {
        super("matches");
    }
}
