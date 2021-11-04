package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MatchAutoCommand;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.commands.MatchOneToManyCommand;
import seedu.address.logic.commands.buyer.MatchBuyerCommand;
import seedu.address.logic.commands.property.MatchPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Actor;

public class MatchCommandParser {
    private static final int ACTOR_POSITIONAL_INDEX = 0;
    private static final int INDEX_POSITIONAL_INDEX = 1;
    private static final int NUM_OF_PREAMBLE_ARGS_IN_ONE_TO_MANY = 2;


    /**
     * Parses the given {@code String} of arguments in the context of the MatchCommand
     * and returns an MatchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        if (isMatchAuto(args)) {
            return new MatchAutoCommand();
        }
        try {
            return parseOneToManyMatch(args);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }
    }

    private boolean isMatchAuto(String args) {
        return args.trim().equals(MatchAutoCommand.ARGUMENT_WORD);
    }

    private MatchOneToManyCommand parseOneToManyMatch(String args) throws ParseException {
        ParserUtil.assertPreambleArgsCount(args, NUM_OF_PREAMBLE_ARGS_IN_ONE_TO_MANY);
        Actor actor = ParserUtil.parseActor(args, ACTOR_POSITIONAL_INDEX);
        Index index = ParserUtil.parseIndex(args, INDEX_POSITIONAL_INDEX);
        switch (actor) {
        case PROPERTY:
            return new MatchPropertyCommand(index);
        case BUYER:
            return new MatchBuyerCommand(index);
        default:
            assert false;
            return null;
        }
    }
}
