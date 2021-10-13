package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_PROPERTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_PREAMBLE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROPERTY;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.property.DeletePropertyCommand;
import seedu.address.logic.commands.property.SortPropertyCommand;
import seedu.address.logic.parser.preambledata.PreambleSortData;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;


public class SortCommandParserTest {
    private static final String VALID_ACTOR = "property ";
    private static final String VALID_SORT_TYPE = "price ";
    private static final String VALID_SORT_DIR = "asc ";
    private static final String VALID_COMMAND = VALID_ACTOR + VALID_SORT_TYPE + VALID_SORT_DIR;

    private static final String INVALID_COMMAND_MISSING_ACTOR = VALID_SORT_TYPE + VALID_SORT_DIR;
    private static final String INVALID_COMMAND_MISSING_SORT_TYPE = VALID_ACTOR + VALID_SORT_DIR;
    private static final String INVALID_COMMAND_MISSING_SORT_DIR = VALID_ACTOR + VALID_SORT_TYPE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PREAMBLE
                    + PreambleSortData.MESSAGE_PREAMBLE_FIELD + "\n" + SortCommand.MESSAGE_USAGE);

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingActor_throwsParseException() {
        assertParseFailure(parser, INVALID_COMMAND_MISSING_ACTOR, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingSortType_throwsParseException() {
        assertParseFailure(parser, INVALID_COMMAND_MISSING_SORT_TYPE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingSortDir_throwsParseException() {
        assertParseFailure(parser, INVALID_COMMAND_MISSING_SORT_DIR, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, VALID_COMMAND,
                new SortPropertyCommand(SortType.PRICE, SortDirection.ASC));
    }
}
