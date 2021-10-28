package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.StringUtil;

public class TagTest {
    public static final String UPPERCASE_TAGNAME = "CONDO1";
    public static final String MIXEDCASE_TAGNAME = "coNDo1";
    public static final String LOWERCASE_TAGNAME = "condo1";
    public static final String VALID_TAGNAME_WITH_HYPHEN = "cOn-Do1";

    private static final String TAGNAME_OF_LENGTH_0 = "";
    // valid tagname lengths
    private static final String TAGNAME_OF_LENGTH_1 = StringUtil.repeat(1, "A");
    private static final String TAGNAME_OF_LENGTH_2 = StringUtil.repeat(2, "A");
    private static final String TAGNAME_OF_LENGTH_99 = StringUtil.repeat(99, "A");
    private static final String TAGNAME_OF_LENGTH_100 = StringUtil.repeat(100, "A");
    // end of valid
    private static final String TAGNAME_OF_LENGTH_101 = StringUtil.repeat(101, "A");
    private static final String TAGNAME_OF_LENGTH_102 = StringUtil.repeat(102, "A");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void constructor_invalidStartWithSpaceTagName_throwsIllegalArgumentException() {
        String invalidTagName = " " + LOWERCASE_TAGNAME;
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void constructor_invalidStartWithHyphenTagName_throwsIllegalArgumentException() {
        String invalidTagName = "-" + LOWERCASE_TAGNAME;
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tagnames
        assertFalse(Tag.isValidTagName(TAGNAME_OF_LENGTH_0)); // length boundary
        assertFalse(Tag.isValidTagName(TAGNAME_OF_LENGTH_101)); // length boundary
        assertFalse(Tag.isValidTagName(TAGNAME_OF_LENGTH_102));

        // valid tagnames
        assertTrue(Tag.isValidTagName(UPPERCASE_TAGNAME));
        assertTrue(Tag.isValidTagName(MIXEDCASE_TAGNAME));
        assertTrue(Tag.isValidTagName(LOWERCASE_TAGNAME));
        assertTrue(Tag.isValidTagName(VALID_TAGNAME_WITH_HYPHEN));
        assertTrue(Tag.isValidTagName(TAGNAME_OF_LENGTH_1)); // length boundary
        assertTrue(Tag.isValidTagName(TAGNAME_OF_LENGTH_2));
        assertTrue(Tag.isValidTagName(TAGNAME_OF_LENGTH_99));
        assertTrue(Tag.isValidTagName(TAGNAME_OF_LENGTH_100)); // length boundary
    }

    @Test
    public void equals() {
        Tag upperCaseTag = new Tag(UPPERCASE_TAGNAME);
        Tag mixedCaseTag = new Tag(MIXEDCASE_TAGNAME);
        Tag lowerCaseTag = new Tag(LOWERCASE_TAGNAME);
        Tag hyphenTag = new Tag(VALID_TAGNAME_WITH_HYPHEN);
        assertTrue(upperCaseTag.equals(mixedCaseTag));
        assertTrue(mixedCaseTag.equals(lowerCaseTag));
        assertFalse(hyphenTag.equals(upperCaseTag));
    }
}
