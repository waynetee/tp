package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.StringUtil;

/**
 * Represents a Tag in the address book with lowercase tag name.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {
    public static final Integer MAX_LENGTH = 100;
    public static final String MESSAGE_CONSTRAINTS = "Tags names should only contain alphanumeric characters,"
            + "hyphens, and spaces and it should not be blank.\n"
            + "Tags should also be at most " + MAX_LENGTH + " characters long.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}\\- ]{0," + (MAX_LENGTH - 1) + "}";

    public final String tagName;

    /**
     * Converts name of tag to lowercase and constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = StringUtil.compressWhitespace(tagName.toLowerCase());
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
