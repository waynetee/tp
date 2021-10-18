package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_ACTOR;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.buyer.SortBuyerCommand;
import seedu.address.logic.commands.property.SortPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Actor;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;


/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static final int ACTOR_POSITIONAL_INDEX = 0;
    private static final int SORT_TYPE_POSITIONAL_INDEX = 1;
    private static final int SORT_DIR_POSITIONAL_INDEX = 2;
    private static final int NUM_OF_PREAMBLE_ARGS = 3;
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Actor actor;
        SortType sortType;
        SortDirection sortDirection;

        try {
            ParserUtil.assertPreambleArgsCount(args, NUM_OF_PREAMBLE_ARGS);
            actor = ParserUtil.parseActor(args, ACTOR_POSITIONAL_INDEX);
            sortType = ParserUtil.parseSortType(args, SORT_TYPE_POSITIONAL_INDEX);
            sortDirection = ParserUtil.parseSortDir(args, SORT_DIR_POSITIONAL_INDEX);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
        }

        switch (actor) {
        case PROPERTY:
            return new SortPropertyCommand(sortType, sortDirection);
        case BUYER:
            return new SortBuyerCommand(sortType, sortDirection);
        default:
            throw new ParseException(MESSAGE_INVALID_ACTOR);
        }
    }
}
