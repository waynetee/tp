package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Actor;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;
import seedu.address.model.property.Address;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SELLER = "Br@yn";
    private static final String INVALID_PHONE = "911#";
    private static final String INVALID_PHONE_TOO_SHORT = "1";
    private static final String INVALID_PRICE = "100k";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ACTOR = "book";
    private static final String INVALID_SORT_TYPE = "phone";
    private static final String INVALID_SORT_DIR = "up";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_PHONE_LONG = "111111111111111111111111111111";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_SELLER = "Bryan Walker";
    private static final String VALID_PRICE = "100000";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_ACTOR = "property";
    private static final String VALID_SORT_TYPE = "price";
    private static final String VALID_SORT_DIR = "asc";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_INDEX, Integer.MAX_VALUE + 1), ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueLongPhone_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE_LONG);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE_LONG));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parsePhone_invalidValueTooShort_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE_TOO_SHORT));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parsePrice_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrice((String) null));
    }

    @Test
    public void parsePrice_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice(INVALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithoutWhitespace_returnsPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(VALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_PRICE + WHITESPACE;
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(priceWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseActor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseActor((String) null));
    }

    @Test
    public void parseActor_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseActor(INVALID_ACTOR));
    }

    @Test
    public void parseActor_validValueWithoutWhitespace_returnsActor() throws Exception {
        Actor expectedActor = Actor.PROPERTY;
        assertEquals(expectedActor, ParserUtil.parseActor(VALID_ACTOR));
    }

    @Test
    public void parseActor_validValueWithWhitespace_returnsTrimmedActor() throws Exception {
        String actorWithWhitespace = WHITESPACE + VALID_ACTOR + WHITESPACE;
        Actor expectedActor = Actor.PROPERTY;
        assertEquals(expectedActor, ParserUtil.parseActor(actorWithWhitespace));
    }

    @Test
    public void parseSortType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSortType((String) null));
    }

    @Test
    public void parseSortType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortType(INVALID_SORT_TYPE));
    }

    @Test
    public void parseSortType_validValueWithoutWhitespace_returnsSortType() throws Exception {
        SortType expectedSortType = SortType.PRICE;
        assertEquals(expectedSortType, ParserUtil.parseSortType(VALID_SORT_TYPE));
    }

    @Test
    public void parseSortType_validValueWithWhitespace_returnsTrimmedSortType() throws Exception {
        String sortTypeWithWhitespace = WHITESPACE + VALID_SORT_TYPE + WHITESPACE;
        SortType expectedSortType = SortType.PRICE;
        assertEquals(expectedSortType, ParserUtil.parseSortType(sortTypeWithWhitespace));
    }

    @Test
    public void parseSortDir_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSortDir((String) null));
    }

    @Test
    public void parseSortDir_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortDir(INVALID_SORT_DIR));
    }

    @Test
    public void parseSortDir_validValueWithoutWhitespace_returnsSortDir() throws Exception {
        SortDirection expectedSortDir = SortDirection.ASC;
        assertEquals(expectedSortDir, ParserUtil.parseSortDir(VALID_SORT_DIR));
    }

    @Test
    public void parseSortDir_validValueWithWhitespace_returnsTrimmedSortDir() throws Exception {
        String sortDirWithWhitespace = WHITESPACE + VALID_SORT_DIR + WHITESPACE;
        SortDirection expectedSortDir = SortDirection.ASC;
        assertEquals(expectedSortDir, ParserUtil.parseSortDir(sortDirWithWhitespace));
    }

    @Test
    public void parseActorWithIndex_null_throwsNullPointerExceptions() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseActor((String) null, 0));
    }

    @Test
    public void parseActorWithIndex_invalidValueInvalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseActor("1 " + VALID_ACTOR, 0));
    }

    @Test
    public void parseActorWithIndex_invalidValueEmptyActor_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseActor("", 0));
    }

    @Test
    public void parseActorWithIndex_validValue_returnActor() throws Exception {
        assertEquals(Actor.PROPERTY, ParserUtil.parseActor(VALID_ACTOR, 0));
    }

    @Test
    public void parseActorWithIndex_validValueWithWhitespace_returnActor() throws Exception {
        assertEquals(Actor.PROPERTY, ParserUtil.parseActor(WHITESPACE + VALID_ACTOR + WHITESPACE, 0));
    }

    @Test
    public void parseSortTypeWithIndex_null_throwsNullPointerExceptions() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSortType((String) null, 0));
    }

    @Test
    public void parseSortTypeWithIndex_invalidValueInvalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortType("1 " + VALID_SORT_TYPE, 0));
    }

    @Test
    public void parseSortTypeWithIndex_invalidValueEmptyActor_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortType("", 0));
    }

    @Test
    public void parseSortTypeWithIndex_validValue_returnSortType() throws Exception {
        assertEquals(SortType.PRICE, ParserUtil.parseSortType(VALID_ACTOR + " " + VALID_SORT_TYPE, 1));
    }

    @Test
    public void parseSortTypeWithIndex_validValueWithWhitespace_returnSortType() throws Exception {
        assertEquals(SortType.PRICE, ParserUtil.parseSortType(
                WHITESPACE + VALID_SORT_TYPE + "     1" + WHITESPACE, 0));
    }

    @Test
    public void parseSortDirWithIndex_null_throwsNullPointerExceptions() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSortDir((String) null, 0));
    }

    @Test
    public void parseSortDirWithIndex_invalidValueInvalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortDir("1 " + VALID_SORT_TYPE, 0));
    }

    @Test
    public void parseSortDirWithIndex_invalidValueEmptyActor_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortDir("", 0));
    }

    @Test
    public void parseSortDirWithIndex_validValue_returnSortDir() throws Exception {
        assertEquals(SortDirection.ASC, ParserUtil.parseSortDir(VALID_ACTOR + " " + VALID_SORT_DIR, 1));
    }

    @Test
    public void parseSortDirWithIndex_validValueWithWhitespace_returnSortDir() throws Exception {
        assertEquals(SortDirection.ASC, ParserUtil.parseSortDir(
                WHITESPACE + VALID_SORT_DIR + "     1" + WHITESPACE, 0));
    }
}
