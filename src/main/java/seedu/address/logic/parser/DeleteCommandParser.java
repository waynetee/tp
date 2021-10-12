package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.buyer.DeleteBuyerCommand;
import seedu.address.logic.commands.property.DeletePropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    private static final int NUMBER_OF_PREAMBLE_ARGUMENTS = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        PreambleData.Actor actor;
        Index index;

        try {
            PreambleData preambleData = ParserUtil.parsePreamble(args,
                    NUMBER_OF_PREAMBLE_ARGUMENTS);
            actor = preambleData.getActor();
            index = preambleData.getIndex();
            switch (actor) {
            case PROPERTY:
                return new DeletePropertyCommand(index);
            case BUYER:
                return new DeleteBuyerCommand(index);
            default:
                throw new ParseException(PreambleData.MESSAGE_INVALID_ACTOR);
            }

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
