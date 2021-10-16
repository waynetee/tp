package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_ACTOR;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.commands.buyer.MatchBuyerCommand;
import seedu.address.logic.commands.property.MatchPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MatchCommandParser {
    private static final int NUMBER_OF_PREAMBLE_ARGUMENTS = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the MatchCommand
     * and returns an MatchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        PreambleData preambleData = ParserUtil.parsePreamble(argMultimap.getPreamble(), NUMBER_OF_PREAMBLE_ARGUMENTS);
        PreambleData.Actor actor = preambleData.getActor();
        Index index = preambleData.getIndex();
        switch (actor) {
        case PROPERTY:
            return new MatchPropertyCommand(index);
        case BUYER:
            return new MatchBuyerCommand(index);
        default:
            throw new ParseException(MESSAGE_INVALID_ACTOR);
        }
    }
}
