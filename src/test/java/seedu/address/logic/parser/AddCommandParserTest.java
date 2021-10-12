package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SELLER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SELLER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SELLER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SELLER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalProperties.P_AMY;
import static seedu.address.testutil.TypicalProperties.P_BOB;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
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
    public void parse_allFieldsPresent_success() {
        Property expectedProperty = new PropertyBuilder(P_BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, new AddPropertyCommand(expectedProperty));

        // multiple names - last name accepted
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, new AddPropertyCommand(expectedProperty));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, new AddPropertyCommand(expectedProperty));

        // multiple emails - last email accepted
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, new AddPropertyCommand(expectedProperty));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, new AddPropertyCommand(expectedProperty));

        // multiple sellers - last sellers accepted
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_AMY + SELLER_DESC_BOB + PRICE_DESC_BOB
                + TAG_DESC_FRIEND, new AddPropertyCommand(expectedProperty));

        // multiple price - last price accepted
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_AMY + PRICE_DESC_BOB
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
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Property expectedProperty = new PropertyBuilder(P_AMY).withTags().build();
        assertParseSuccess(parser, PREAMBLE_PROPERTY
                + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SELLER_DESC_AMY + PRICE_DESC_AMY, new AddPropertyCommand(expectedProperty));
    }

    @Disabled
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing seller prefix
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_SELLER_BOB + PRICE_DESC_BOB, expectedMessage);

        // missing price prefix
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + VALID_PRICE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_SELLER_BOB + VALID_PRICE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, PREAMBLE_PROPERTY
                + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SELLER_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

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
    }
}
