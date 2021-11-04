package seedu.address.model.field;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Property's phone number in the address book.
 * Guarantees: immutable; Is always valid.
 */
public class Phone {
    private static final Integer MIN_LENGTH = 3;
    private static final String VALIDATION_REGEX = "[\\p{Alnum}\\-()+ ]{" + MIN_LENGTH + ",}";
    public static final String MESSAGE_CONSTRAINTS =
            "Phone number should only contain alphanumeric characters, hyphens, parentheses, plus signs and spaces.\n"
                    + "Phone number should be at least " + MIN_LENGTH + " characters long.\n";

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
     * Returns true if a given string matches the validation regex.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
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
