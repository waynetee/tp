package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_ACTOR;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.buyer.ImportBuyersCommand;
import seedu.address.logic.commands.property.ImportPropertiesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Actor;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {
    private static final int ACTOR_POSITIONAL_INDEX = 0;
    private static final int NUM_OF_PREAMBLE_ARGUMENTS = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        Actor actor;

        try {
            ParserUtil.assertPreambleArgsCount(argMultimap.getPreamble(), NUM_OF_PREAMBLE_ARGUMENTS);
            actor = ParserUtil.parseActor(args, ACTOR_POSITIONAL_INDEX);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE), pe);
        }
        switch (actor) {
        case PROPERTY:
            return new ImportPropertiesCommand();
        case BUYER:
            return new ImportBuyersCommand();
        default:
            throw new ParseException(MESSAGE_INVALID_ACTOR);
        }
    }

}
