package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_ACTOR;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.buyer.ExportBuyersCommand;
import seedu.address.logic.commands.property.ExportPropertiesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Actor;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {
    private static final int ACTOR_POSITIONAL_INDEX = 0;
    private static final int NUM_OF_PREAMBLE_ARGUMENTS = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        Actor actor;

        try {
            ParserUtil.assertPreambleArgsCount(argMultimap.getPreamble(), NUM_OF_PREAMBLE_ARGUMENTS);
            actor = ParserUtil.parseActor(args, ACTOR_POSITIONAL_INDEX);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE), pe);
        }
        switch (actor) {
        case PROPERTY:
            return new ExportPropertiesCommand();
        case BUYER:
            return new ExportBuyersCommand();
        default:
            throw new ParseException(MESSAGE_INVALID_ACTOR);
        }
    }

}
