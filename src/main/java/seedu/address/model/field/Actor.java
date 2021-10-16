package seedu.address.model.field;

public enum Actor {
    PROPERTY, BUYER;
    public static final String MESSAGE_CONSTRAINTS = "Invalid actor. Only property/buyer are allowed";
}