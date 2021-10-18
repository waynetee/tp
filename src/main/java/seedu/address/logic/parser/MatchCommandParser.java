package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_ACTOR;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MatchOneToManyCommand;
import seedu.address.logic.commands.buyer.MatchBuyerCommand;
import seedu.address.logic.commands.property.MatchPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Actor;

public class MatchCommandParser {
    private static final int ACTOR_POSITIONAL_INDEX = 0;
    private static final int INDEX_POSITIONAL_INDEX = 1;
    private static final int NUM_OF_PREAMBLE_ARGS = 2;


    /**
     * Parses the given {@code String} of arguments in the context of the MatchOneToManyCommand
     * and returns an MatchOneToManyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchOneToManyCommand parse(String args) throws ParseException {
        ParserUtil.assertPreambleArgsCount(args, NUM_OF_PREAMBLE_ARGS);
        Actor actor = ParserUtil.parseActor(args, ACTOR_POSITIONAL_INDEX);
        Index index = ParserUtil.parseIndex(args, INDEX_POSITIONAL_INDEX);
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
