package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.StringUtil.compressWhitespace;
import static seedu.address.commons.util.StringUtil.startCaseSentence;

/**
 * Represents a Property's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final Integer MAX_LENGTH = 100;
    public static final String MESSAGE_CONSTRAINTS = "Addresses should only contain alphanumeric characters, "
            + "hashes (#), commas(,), semicolons (;), hyphens (-), and spaces, and it should not be blank.\n"
            + "Addresses should also be at most " + MAX_LENGTH + " characters long.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}\\-#,;][\\p{Alnum}\\-#,; ]{0," + (MAX_LENGTH - 1) + "}";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = startCaseSentence(compressWhitespace(address));
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
