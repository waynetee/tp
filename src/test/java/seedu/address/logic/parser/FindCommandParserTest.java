package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_PRICE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.buyer.FindBuyerCommand;
import seedu.address.model.field.ContainsPricePredicate;
import seedu.address.model.field.ContainsTagsPredicate;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.model.field.Price;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_BUYER + " "
                + "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBuyerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTagArgs_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_BUYER + " " + " t/", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyMinPrice_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_BUYER + " " + PREFIX_MIN_PRICE, Price.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyMaxPrice_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_BUYER + " " + PREFIX_MAX_PRICE, Price.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validKeywordArgs_returnsFindBuyerCommand() {
        // no leading and trailing whitespaces
        FindBuyerCommand expectedFindBuyerCommand =
                new FindBuyerCommand(new NameContainsKeywordsPredicate<>(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, PREAMBLE_BUYER + " " + "Alice Bob", expectedFindBuyerCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREAMBLE_BUYER + " " + " \n Alice \n \t Bob  \t", expectedFindBuyerCommand);

    }

    @Test
    public void parse_validTagArgs_returnsFindBuyerCommand() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("condo"));
        tags.add(new Tag("4rm"));
        FindBuyerCommand expectedFindBuyerCommand = new FindBuyerCommand(new NameContainsKeywordsPredicate<>(),
                new ContainsTagsPredicate<>(tags), new ContainsPricePredicate<>());
        assertParseSuccess(parser, PREAMBLE_BUYER + " " + " t/condo t/4rm", expectedFindBuyerCommand);
        assertParseSuccess(parser, PREAMBLE_BUYER + " " + " t/4rm t/condo", expectedFindBuyerCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREAMBLE_BUYER + " " + "\n t/4rm \n \t t/condo \t", expectedFindBuyerCommand);
    }

    @Test
    public void parse_validKeywordAndTagArgs_returnsFindBuyerCommand() {
        List<String> keywords = Arrays.asList("Alice", "Bob");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("condo"));
        tags.add(new Tag("4rm"));
        FindBuyerCommand expectedFindBuyerCommand = new FindBuyerCommand(new NameContainsKeywordsPredicate<>(keywords),
                new ContainsTagsPredicate<>(tags), new ContainsPricePredicate<>());
        assertParseSuccess(parser, PREAMBLE_BUYER + " " + "Alice Bob t/condo t/4rm", expectedFindBuyerCommand);
    }

    @Test
    public void parse_validMinPrice_returnsFindBuyerCommand() {
        Price minPrice = new Price("10000");
        FindBuyerCommand expectedFindBuyerCommand = new FindBuyerCommand(new NameContainsKeywordsPredicate<>(),
                new ContainsTagsPredicate<>(), new ContainsPricePredicate<>(minPrice, null));
        assertParseSuccess(parser, PREAMBLE_BUYER + " "
                + PREFIX_MIN_PRICE + "10000", expectedFindBuyerCommand);
    }

    @Test
    public void parse_validMaxPrice_returnsFindBuyerCommand() {
        Price maxPrice = new Price("10000");
        FindBuyerCommand expectedFindBuyerCommand = new FindBuyerCommand(new NameContainsKeywordsPredicate<>(),
                new ContainsTagsPredicate<>(), new ContainsPricePredicate<>(null, maxPrice));
        assertParseSuccess(parser, PREAMBLE_BUYER + " "
                + PREFIX_MAX_PRICE + "10000", expectedFindBuyerCommand);
    }

    @Test
    public void parse_validMinPriceMaxPrice_returnsFindBuyerCommand() {
        Price minPrice = new Price("10000");
        Price maxPrice = new Price("100000");
        FindBuyerCommand expectedFindBuyerCommand = new FindBuyerCommand(new NameContainsKeywordsPredicate<>(),
                new ContainsTagsPredicate<>(), new ContainsPricePredicate<>(minPrice, maxPrice));
        assertParseSuccess(parser, PREAMBLE_BUYER + " "
                + PREFIX_MIN_PRICE + "10000 " + PREFIX_MAX_PRICE + "100000 ", expectedFindBuyerCommand);
    }
}
