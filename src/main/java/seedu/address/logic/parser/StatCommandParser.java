package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_ACTOR;

import seedu.address.logic.commands.StatCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Actor;

public class StatCommandParser implements Parser<StatCommand> {
    private static final int ACTOR_POSITIONAL_INDEX = 0;
    private static final int NUM_OF_PREAMBLE_ARGS = 1;

    private static final boolean SHOW_BUYER = true;
    private static final boolean SHOW_PROPERTY = true;

    @Override
    public StatCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        Actor actor;
        userInput = userInput.trim();
        try {
            ParserUtil.assertPreambleArgsCount(userInput, NUM_OF_PREAMBLE_ARGS);
            actor = ParserUtil.parseActor(userInput, ACTOR_POSITIONAL_INDEX);
        } catch (ParseException pe) {
            if (userInput.isEmpty()) {
                return new StatCommand(SHOW_BUYER, SHOW_PROPERTY);
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatCommand.MESSAGE_USAGE), pe);
        }

        switch (actor) {
        case PROPERTY:
            return new StatCommand(!SHOW_BUYER, SHOW_PROPERTY);
        case BUYER:
            return new StatCommand(SHOW_BUYER, !SHOW_PROPERTY);
        default:
            throw new ParseException(MESSAGE_INVALID_ACTOR);
        }
    }
}
