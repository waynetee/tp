package seedu.address.model.field;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.ComparerMixin;
import seedu.address.commons.util.StringUtil;

public class Price implements ComparerMixin<Price> {
    public static final Integer MIN_LENGTH = 4;
    public static final Integer MAX_LENGTH = 9;
    public static final String MESSAGE_CONSTRAINTS =
            "Price (in dollars) should only contain numbers, and it should be at least " + MIN_LENGTH + " digits and "
                    + "at most " + MAX_LENGTH + " digits long, not counting leading zeroes.";
    public static final String VALIDATION_REGEX = "\\d{" + MIN_LENGTH + "," + MAX_LENGTH + "}";
    public final Long value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price number.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        value = Long.parseLong(price);
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return StringUtil.stripLeadingZeroes(test).matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Price otherPrice) {
        return value.compareTo(otherPrice.value);
    }
}
