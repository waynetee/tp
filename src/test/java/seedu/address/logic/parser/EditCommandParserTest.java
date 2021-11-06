package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_ACTOR;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC_TOO_SHORT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SELLER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_BUYER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SELLER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SELLER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.ArgumentMultimap.MESSAGE_REPEATED_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_PREAMBLE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.buyer.EditBuyerCommand;
import seedu.address.logic.commands.property.EditPropertyCommand;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.property.Address;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPropertyDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;
    private static final String T_CONDO = "condo";
    private static final String T_NEAR_SCHOOL = "near school";
    private static final String TAG_ADD_CONDO = " " + PREFIX_ADD_TAG + T_CONDO;
    private static final String TAG_DELETE_CONDO = " " + PREFIX_DELETE_TAG + T_CONDO;
    private static final String TAG_DELETE_NEAR_SCHOOL = " " + PREFIX_DELETE_TAG + T_NEAR_SCHOOL;

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PREAMBLE_PROPERTY,
                String.format(MESSAGE_INVALID_PREAMBLE, EditCommand.EXPECTED_PREAMBLE, PREAMBLE_PROPERTY));

        // no actor specified
        assertParseFailure(parser, "1",
                String.format(MESSAGE_UNKNOWN_ACTOR, EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE));

        // no property field specified
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1", EditCommand.MESSAGE_NOT_EDITED
                + "\n" + EditPropertyCommand.MESSAGE_USAGE);

        // no buyer field specified
        assertParseFailure(parser, PREAMBLE_BUYER + " " + "1", EditCommand.MESSAGE_NOT_EDITED
                + "\n" + EditBuyerCommand.MESSAGE_USAGE);

        // empty command
        assertParseFailure(parser, "",
                String.format(MESSAGE_UNKNOWN_ACTOR, EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        String userPreamble = PREAMBLE_PROPERTY + " -5";
        assertParseFailure(parser, userPreamble + NAME_DESC_AMY, String.format(MESSAGE_INVALID_INDEX, -5));

        // zero index
        userPreamble = PREAMBLE_PROPERTY + " 0";
        assertParseFailure(parser, userPreamble + NAME_DESC_AMY, String.format(MESSAGE_INVALID_INDEX, 0));

        // invalid number of arguments being parsed as preamble
        String invalidPreamble = PREAMBLE_PROPERTY + " 1 some random string";
        assertParseFailure(parser, invalidPreamble,
                String.format(MESSAGE_INVALID_PREAMBLE, EditCommand.EXPECTED_PREAMBLE, invalidPreamble));

        // invalid actor in preamble
        assertParseFailure(parser, "buy",
                String.format(MESSAGE_UNKNOWN_ACTOR, EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + INVALID_PHONE_DESC_TOO_SHORT, Phone.MESSAGE_CONSTRAINTS); // invalid phone - too short
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + INVALID_SELLER_DESC, Name.MESSAGE_CONSTRAINTS); // invalid seller
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + INVALID_PRICE_DESC, Price.MESSAGE_CONSTRAINTS); // invalid price
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Property} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, PREAMBLE_PROPERTY + " " + "1"
                        + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parsePropertyCommand_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased()
                + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY
                + SELLER_DESC_AMY + PRICE_DESC_AMY + TAG_DESC_FRIEND;

        EditPropertyCommand.EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPrice(VALID_PRICE_AMY).withSeller(VALID_SELLER_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditPropertyCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased()
                + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPropertyCommand.EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditPropertyCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPropertyCommand.EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditPropertyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPropertyDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditPropertyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPropertyDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditPropertyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditPropertyDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditPropertyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // seller
        userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased() + SELLER_DESC_AMY;
        descriptor = new EditPropertyDescriptorBuilder().withSeller(VALID_SELLER_AMY).build();
        expectedCommand = new EditPropertyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased() + PRICE_DESC_AMY;
        descriptor = new EditPropertyDescriptorBuilder().withPrice(VALID_PRICE_AMY).build();
        expectedCommand = new EditPropertyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPropertyDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditPropertyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST;
        String userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased()
                + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput, String.format(MESSAGE_REPEATED_PREFIX, PREFIX_PHONE));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased() + TAG_EMPTY;

        EditPropertyCommand.EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder().withTags().build();
        EditPropertyCommand expectedCommand = new EditPropertyCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_addAndDeleteTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased()
                + TAG_ADD_CONDO + TAG_DELETE_NEAR_SCHOOL;

        EditPropertyCommand.EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder()
                .withTagsToAdd(T_CONDO).withTagsToDelete(T_NEAR_SCHOOL).build();
        EditPropertyCommand expectedCommand = new EditPropertyCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_addAndDeleteSameTag_failure() {
        Index targetIndex = INDEX_THIRD;
        String userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased()
                + TAG_ADD_CONDO + TAG_DELETE_CONDO;

        assertParseFailure(parser, userInput, EditCommand.MESSAGE_DUPLICATE_ADD_AND_DELETE_TAG);
    }

    @Test
    public void parse_resetAndModifyTagsSimultaneously_failure() {
        Index targetIndex = INDEX_THIRD;
        String userInput = PREAMBLE_PROPERTY + " " + targetIndex.getOneBased()
                + TAG_EMPTY + TAG_DELETE_CONDO;

        assertParseFailure(parser, userInput, EditCommand.MESSAGE_RESET_TAG_TOGETHER_WITH_MODIFY_TAG);
    }
}
