package seedu.address.model.field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.StringUtil;

public class NameTest {
    private static final String NAME_OF_LENGTH_0 = "";
    // valid name lengths
    private static final String NAME_OF_LENGTH_1 = StringUtil.repeat(1, "A");
    private static final String NAME_OF_LENGTH_2 = StringUtil.repeat(2, "A");
    private static final String NAME_OF_LENGTH_49 = StringUtil.repeat(49, "A");
    private static final String NAME_OF_LENGTH_50 = StringUtil.repeat(50, "A");
    // end of valid
    private static final String NAME_OF_LENGTH_51 = StringUtil.repeat(51, "A");
    private static final String NAME_OF_LENGTH_52 = StringUtil.repeat(52, "A");

    private static final String UPPERCASE_NAME = "JOHN CONNOR";
    private static final String MIXEDCASE_NAME = "JoHn coNNoR";
    private static final String LOWERCASE_NAME = "john connor";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void constructor_invalidStartWithSpaceName_throwsIllegalArgumentException() {
        String invalidName = " " + NAME_OF_LENGTH_2;
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void constructor_invalidStartWithHyphenName_throwsIllegalArgumentException() {
        String invalidName = "-" + NAME_OF_LENGTH_2;
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName(NAME_OF_LENGTH_0)); // length boundary
        assertFalse(Name.isValidName(NAME_OF_LENGTH_51)); // length boundary
        assertFalse(Name.isValidName(NAME_OF_LENGTH_52));

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName(NAME_OF_LENGTH_1)); // length boundary
        assertTrue(Name.isValidName(NAME_OF_LENGTH_2));
        assertTrue(Name.isValidName(NAME_OF_LENGTH_49));
        assertTrue(Name.isValidName(NAME_OF_LENGTH_50)); // length boundary

    }

    @Test
    public void equals() {
        Name upperCaseName = new Name(UPPERCASE_NAME);
        Name mixedCaseName = new Name(MIXEDCASE_NAME);
        Name lowerCaseName = new Name(LOWERCASE_NAME);
        assertTrue(upperCaseName.equals(mixedCaseName));
        assertTrue(mixedCaseName.equals(lowerCaseName));
    }
}
