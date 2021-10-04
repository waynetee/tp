package seedu.address.model.property.exceptions;

/**
 * Signals that the operation will result in duplicate Properties (Properties are considered duplicates
 * if they have the same identity).
 */
public class DuplicateListableException extends RuntimeException {
    public DuplicateListableException() {
        super("Operation would result in duplicate properties");
    }
}
