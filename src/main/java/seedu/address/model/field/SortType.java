package seedu.address.model.field;

public enum SortType {
    NAME, PRICE;

    public static final String MESSAGE_CONSTRAINTS = "Invalid sort type. Only name and price can be sorted";

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
