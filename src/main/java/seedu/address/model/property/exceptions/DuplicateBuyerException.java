package seedu.address.model.property.exceptions;

/**
 * Signals that the operation will result in duplicate Buyers (Buyers are considered duplicates
 * if they have the same identity).
 */
public class DuplicateBuyerException extends DuplicateListableException {
    public DuplicateBuyerException() {
        super("buyers");
    }
}
