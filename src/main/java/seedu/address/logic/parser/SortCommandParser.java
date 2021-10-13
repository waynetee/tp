package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.buyer.SortBuyerCommand;
import seedu.address.logic.commands.property.SortPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.preambledata.PreambleActorData.Actor;
import seedu.address.logic.parser.preambledata.PreambleSortData;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_ACTOR;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        PreambleSortData preambleData = ParserUtil.parseSortPreamble(argMultimap.getPreamble());
        Actor actor = preambleData.getActor();
        SortType sortType = preambleData.getSortType();
        SortDirection sortDirection = preambleData.getSortDirection();

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