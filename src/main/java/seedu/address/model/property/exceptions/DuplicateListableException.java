package seedu.address.model.property.exceptions;

/**
 * Signals that the operation will result in duplicate elements (Elements are considered duplicates
 * if they have the same identity).
 */
public class DuplicateListableException extends RuntimeException {
    public DuplicateListableException() {
        super("Operation would result in duplicate elements");
    }

    public DuplicateListableException(String type) {
        super(String.format("Operation would result in duplicate %s", type));
    }
}
