package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_BUYER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SELLER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SELLER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.ArgumentMultimap.MESSAGE_REPEATED_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_PREAMBLE;
import static seedu.address.testutil.TypicalProperties.P_AMY;
import static seedu.address.testutil.TypicalProperties.P_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.buyer.AddBuyerCommand;
import seedu.address.logic.commands.property.AddPropertyCommand;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.property.Address;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PropertyBuilder;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_missingParts_failure() {
        String expected = String.format(MESSAGE_UNKNOWN_ACTOR, AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE);

        // no actor specified
        assertParseFailure(parser, NAME_DESC_AMY, expected);

        // empty command
        assertParseFailure(parser, "", expected);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid number of arguments being parsed as preamble
        String invalidPreamble = PREAMBLE_PROPERTY + " some random string";
        assertParseFailure(parser, invalidPreamble,
                String.format(MESSAGE_INVALID_PREAMBLE,
                AddCommand.EXPECTED_PREAMBLE,
                PREAMBLE_PROPERTY + " some random string"));

        // invalid actor in preamble
        assertParseFailure(parser, "buy",
                String.format(MESSAGE_UNKNOWN_ACTOR, AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Property expectedProperty = new PropertyBuilder(P_BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, new AddPropertyCommand(expectedProperty));

        // multiple tags - all accepted
        Property expectedPropertyMultipleTags = new PropertyBuilder(P_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddPropertyCommand(expectedPropertyMultipleTags));
    }

    @Test
    public void parse_repeatedFields_failure() {
        // multiple names - failure
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, String.format(MESSAGE_REPEATED_PREFIX, PREFIX_NAME));

        // multiple phones - failure
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, String.format(MESSAGE_REPEATED_PREFIX, PREFIX_PHONE));

        // multiple emails - failure
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, String.format(MESSAGE_REPEATED_PREFIX, PREFIX_EMAIL));

        // multiple addresses - failure
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, String.format(MESSAGE_REPEATED_PREFIX, PREFIX_ADDRESS));

        // multiple sellers - failure
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_AMY + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, String.format(MESSAGE_REPEATED_PREFIX, PREFIX_SELLER));

        // multiple price - failure
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_AMY + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, String.format(MESSAGE_REPEATED_PREFIX, PREFIX_PRICE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Property expectedProperty = new PropertyBuilder(P_AMY).withTags().build();
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SELLER_DESC_AMY + PRICE_DESC_AMY, new AddPropertyCommand(expectedProperty));
    }

    @Test
    public void parsePropertyCommand_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing phone
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing email
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing address
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing seller
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + PRICE_DESC_BOB, expectedMessage);

        // missing price
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB, expectedMessage);

        // missing all
        assertParseFailure(parser, PREAMBLE_PROPERTY, expectedMessage);
    }

    @Test
    public void parseBuyerCommand_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBuyerCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser, PREAMBLE_BUYER
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing phone
        assertParseFailure(parser, PREAMBLE_BUYER + NAME_DESC_BOB
                + EMAIL_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing email
        assertParseFailure(parser, PREAMBLE_BUYER + NAME_DESC_BOB
                + PHONE_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing price
        assertParseFailure(parser, PREAMBLE_BUYER + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB, expectedMessage);

        // missing all
        assertParseFailure(parser, PREAMBLE_BUYER, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + SELLER_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid seller
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_SELLER_DESC + PRICE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + INVALID_PRICE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Price.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + SELLER_DESC_BOB + PRICE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone - shorter than minimum length
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + INVALID_PHONE_DESC_TOO_SHORT + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid phone - contain non-numeric characters
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);
    }
}
