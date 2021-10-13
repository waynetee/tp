package seedu.address.model.field;

public enum SortDirection {
    ASC, DESC;

    public static final String MESSAGE_CONSTRAINTS = "Invalid sort direction. Only asc and desc are allowed";

    @Override
    public String toString() {
        return name().toLowerCase() + "ending";
    }
}