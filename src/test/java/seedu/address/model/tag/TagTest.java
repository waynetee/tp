package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {
    public static final String UPPERCASE_TAGNAME = "CONDO1";
    public static final String MIXEDCASE_TAGNAME = "coNDo1";
    public static final String LOWERCASE_TAGNAME = "condo1";

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
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals() {
        Tag upperCaseTag = new Tag(UPPERCASE_TAGNAME);
        Tag mixedCaseTag = new Tag(MIXEDCASE_TAGNAME);
        Tag lowerCaseTag = new Tag(LOWERCASE_TAGNAME);
        assertTrue(upperCaseTag.equals(mixedCaseTag));
        assertTrue(mixedCaseTag.equals(lowerCaseTag));
    }

}
