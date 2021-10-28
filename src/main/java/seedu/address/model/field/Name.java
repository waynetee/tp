package seedu.address.model.field;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.StringUtil.compressWhitespace;
import static seedu.address.commons.util.StringUtil.startCaseSentence;

import seedu.address.commons.util.ComparerMixin;

/**
 * Represents a start-cased (i.e. the first letter of each word is capitalized) name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name implements ComparerMixin<Name> {
    public static final Integer MAX_LENGTH = 50;
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, hyphens, and spaces, and it should not be blank.\n"
                    + "Names should also be at most " + MAX_LENGTH + " characters long.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}\\- ]{0," + (MAX_LENGTH - 1) + "}";

    public final String fullName;

    /**
     * Converts name to start-case and constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = startCaseSentence(compressWhitespace(name));
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

    @Override
    public int compareTo(Name otherName) {
        return fullName.toLowerCase().compareTo(otherName.fullName.toLowerCase());
    }
}
