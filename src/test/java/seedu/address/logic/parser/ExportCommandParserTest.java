package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.buyer.ExportBuyersCommand;
import seedu.address.logic.commands.property.ExportPropertiesCommand;


public class ExportCommandParserTest {
    private static final String INVALID_COMMAND_INVALID_ACTOR = "buy";

    private static final String MESSAGE_INVALID_FORMAT = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidActor_throwsParseException() {
        assertParseFailure(parser, INVALID_COMMAND_INVALID_ACTOR, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsExportCommand() {
        assertParseSuccess(parser, ExportCommand.PROPERTIES, new ExportPropertiesCommand());
        assertParseSuccess(parser, ExportCommand.BUYERS, new ExportBuyersCommand());
    }
}
