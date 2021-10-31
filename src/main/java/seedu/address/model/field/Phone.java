package seedu.address.model.field;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Property's phone number in the address book.
 * Guarantees: immutable; Is always valid.
 */
public class Phone {
    public static final Integer MIN_LENGTH = 3;
    public static final Integer MAX_LENGTH = 30;
    public static final String VALIDATION_DIGIT_REGEX = "\\d{" + MIN_LENGTH + "," + MAX_LENGTH + "}";
    public static final String VALIDATION_LENGTH_REGEX = ".{" + MIN_LENGTH + "," + MAX_LENGTH + "}";
    public static final String MESSAGE_LENGTH_CONSTRAINTS =
            "The minimum and maximum length of a phone number is "
                    + MIN_LENGTH + " and " + MAX_LENGTH + " respectively.";
    public static final String MESSAGE_CONSTRAINTS =
            "Phone number should only contain numbers, and it should be at least " + MIN_LENGTH
                    + " digits and at most " + MAX_LENGTH + " digits long. \n"
                    + "If you are sure that you want to add this phone number, "
                    + "add \" --nvp\" to the end of the command to proceed.";

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        value = phone;
    }

    /**
     * Returns true if a given string is within the min and max length of 3 and 30 respectively.
     */
    public static boolean isValidPhoneLength(String test) {
        return test.matches(VALIDATION_LENGTH_REGEX);
    }

    /**
     * Returns true if a given string only contain digits 0 - 9.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_DIGIT_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
