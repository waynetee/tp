package seedu.address.model.field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.StringUtil;

public class PriceTest {
    private static final String PRICE_OF_LENGTH_0 = "";
    private static final String PRICE_OF_LENGTH_2 = StringUtil.repeat(2, "1");
    private static final String PRICE_OF_LENGTH_3 = StringUtil.repeat(3, "1");
    // valid price lengths
    private static final String PRICE_OF_LENGTH_4 = StringUtil.repeat(4, "1");
    private static final String PRICE_OF_LENGTH_5 = StringUtil.repeat(5, "1");
    private static final String PRICE_OF_LENGTH_8 = StringUtil.repeat(8, "1");
    private static final String PRICE_OF_LENGTH_9 = StringUtil.repeat(9, "1");
    // end of valid
    private static final String PRICE_OF_LENGTH_10 = StringUtil.repeat(10, "1");
    private static final String PRICE_OF_LENGTH_11 = StringUtil.repeat(11, "1");

    private static final String LEADING_ZEROES = StringUtil.repeat(10, "0");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Price(PRICE_OF_LENGTH_0));
    }

    @Test
    public void isValidPrice() {
        // null price
        assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid price
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("anc")); // only non-numeric characters
        assertFalse(Price.isValidPrice("100k")); // contains non-numeric characters
        assertFalse(Price.isValidPrice(PRICE_OF_LENGTH_2));
        assertFalse(Price.isValidPrice(LEADING_ZEROES + PRICE_OF_LENGTH_2));
        assertFalse(Price.isValidPrice(PRICE_OF_LENGTH_3)); // length boundary
        assertFalse(Price.isValidPrice(LEADING_ZEROES + PRICE_OF_LENGTH_3));
        assertFalse(Price.isValidPrice(PRICE_OF_LENGTH_10)); // length boundary
        assertFalse(Price.isValidPrice(LEADING_ZEROES + PRICE_OF_LENGTH_10));
        assertFalse(Price.isValidPrice(PRICE_OF_LENGTH_11));
        assertFalse(Price.isValidPrice(LEADING_ZEROES + PRICE_OF_LENGTH_11));


        // valid price
        assertTrue(Price.isValidPrice(PRICE_OF_LENGTH_4)); // length boundary
        assertTrue(Price.isValidPrice(LEADING_ZEROES + PRICE_OF_LENGTH_4));
        assertTrue(Price.isValidPrice(PRICE_OF_LENGTH_5));
        assertTrue(Price.isValidPrice(LEADING_ZEROES + PRICE_OF_LENGTH_5));
        assertTrue(Price.isValidPrice(PRICE_OF_LENGTH_8));
        assertTrue(Price.isValidPrice(LEADING_ZEROES + PRICE_OF_LENGTH_8));
        assertTrue(Price.isValidPrice(PRICE_OF_LENGTH_9)); // boundary
        assertTrue(Price.isValidPrice(LEADING_ZEROES + PRICE_OF_LENGTH_9));
    }
}
