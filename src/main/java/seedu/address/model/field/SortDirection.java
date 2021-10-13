package seedu.address.model.field;

/**
 * Enum class to represent the sorting direction of a {@code SortCommand}.
 */
public enum SortDirection {
    ASC, DESC;

    public static final String MESSAGE_CONSTRAINTS = "Invalid sort direction. Only asc and desc are allowed";

    @Override
    public String toString() {
        return name().toLowerCase() + "ending";
    }
}
