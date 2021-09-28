package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SellerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Seller(null));
    }

    @Test
    public void constructor_invalidSeller_throwsIllegalArgumentException() {
        String invalidSeller = "";
        assertThrows(IllegalArgumentException.class, () -> new Seller(invalidSeller));
    }

    @Test
    public void isValidSeller() {
        // null seller
        assertThrows(NullPointerException.class, () -> Seller.isValidSeller(null));

        // invalid seller
        assertFalse(Seller.isValidSeller("")); // empty string
        assertFalse(Seller.isValidSeller(" ")); // spaces only
        assertFalse(Seller.isValidSeller("^")); // only non-alphanumeric characters
        assertFalse(Seller.isValidSeller("peter*")); // contains non-alphanumeric characters

        // valid seller
        assertTrue(Seller.isValidSeller("peter jack")); // alphabets only
        assertTrue(Seller.isValidSeller("12345")); // numbers only
        assertTrue(Seller.isValidSeller("peter the 2nd")); // alphanumeric characters
        assertTrue(Seller.isValidSeller("Capital Tan")); // with capital letters
        assertTrue(Seller.isValidSeller("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
