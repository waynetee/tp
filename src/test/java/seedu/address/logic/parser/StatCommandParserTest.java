package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.StatCommand;


public class StatCommandParserTest {
    private static final String VALID_PROPERTY = "property";
    private static final String VALID_BUYER = "buyer";
    private static final String INVALID_ACTOR = "new";

    private static final String MESSAGE_INVALID_FORMAT = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, StatCommand.MESSAGE_USAGE);

    private final StatCommandParser parser = new StatCommandParser();

    @Test
    public void parse_emptyArg_success() {
        assertParseSuccess(parser, "     ",
                new StatCommand(true, true));
    }

    @Test
    public void parse_invalidArgs_returnsStatCommand() {
        assertParseFailure(parser, INVALID_ACTOR, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_buyer_returnsStatCommand() {
        assertParseSuccess(parser, VALID_BUYER,
                new StatCommand(true, false));
    }

    @Test
    public void parse_property_returnsStatCommand() {
        assertParseSuccess(parser, VALID_PROPERTY,
                new StatCommand(false, true));
    }
}
