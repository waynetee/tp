package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.buyer.SortBuyerCommand;
import seedu.address.logic.commands.property.SortPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_DIR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_TYPE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_ACTOR;


public class SortCommandParser implements Parser<SortCommand> {
    private static final int NUMBER_OF_PREAMBLE_ARGUMENTS = 1;

    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT_TYPE, PREFIX_SORT_DIR);

        PreambleData preambleData = ParserUtil.parsePreamble(argMultimap.getPreamble(), NUMBER_OF_PREAMBLE_ARGUMENTS);
        PreambleData.Actor actor = preambleData.getActor();

        SortType sortType = SortType.PRICE;
        if (argMultimap.getValue(PREFIX_SORT_TYPE).isPresent()) {
            sortType = ParserUtil.parseSortType(argMultimap.getValue(PREFIX_SORT_TYPE).get());
        }

        SortDirection sortDirection = SortDirection.ASC;
        if (argMultimap.getValue(PREFIX_SORT_DIR).isPresent()) {
            sortDirection = ParserUtil.parseSortDir(argMultimap.getValue(PREFIX_SORT_DIR).get());
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