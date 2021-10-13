package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.buyer.SortBuyerCommand;
import seedu.address.logic.commands.property.SortPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.SortType;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_TYPE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_ACTOR;


public class SortCommandParser implements Parser<SortCommand> {
    private static final int NUMBER_OF_PREAMBLE_ARGUMENTS = 1;

    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT_TYPE);

        PreambleData preambleData = ParserUtil.parsePreamble(argMultimap.getPreamble(), NUMBER_OF_PREAMBLE_ARGUMENTS);
        PreambleData.Actor actor = preambleData.getActor();

        SortType sortType = SortType.PRICE;
        if (argMultimap.getValue(PREFIX_SORT_TYPE).isPresent()) {
            sortType = ParserUtil.parseSortType(argMultimap.getValue(PREFIX_SORT_TYPE).get());
        }

        switch (actor) {
        case PROPERTY:
            return new SortPropertyCommand(sortType);
        case BUYER:
            return new SortBuyerCommand(sortType);
        default:
            throw new ParseException(MESSAGE_INVALID_ACTOR);
        }
    }
}