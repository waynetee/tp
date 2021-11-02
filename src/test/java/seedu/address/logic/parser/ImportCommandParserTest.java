package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.buyer.ImportBuyersCommand;
import seedu.address.logic.commands.property.ImportPropertiesCommand;


public class ImportCommandParserTest {
    private static final String INVALID_COMMAND_INVALID_ACTOR = "buy";

    private static final String MESSAGE_INVALID_FORMAT = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidActor_throwsParseException() {
        assertParseFailure(parser, INVALID_COMMAND_INVALID_ACTOR, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsImportCommand() {
        assertParseSuccess(parser, ImportCommand.PROPERTIES, new ImportPropertiesCommand());
        assertParseSuccess(parser, ImportCommand.BUYERS, new ImportBuyersCommand());
    }
}
