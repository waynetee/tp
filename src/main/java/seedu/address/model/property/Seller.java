package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Seller {
    public static final String MESSAGE_CONSTRAINTS =
            "Sellers' names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Seller}.
     *
     * @param seller A valid seller.
     */
    public Seller(String seller) {
        requireNonNull(seller);
        checkArgument(isValidSeller(seller), MESSAGE_CONSTRAINTS);
        fullName = seller;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidSeller(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Seller // instanceof handles nulls
                && fullName.equals(((Seller) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
