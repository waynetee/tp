package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_BUYER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.buyer.FindBuyerCommand;
import seedu.address.model.field.ContainsTagsPredicate;
import seedu.address.model.field.NameContainsKeywordsPredicate;
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
                new ContainsTagsPredicate<>(tags));
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
                new ContainsTagsPredicate<>(tags));
        assertParseSuccess(parser, PREAMBLE_BUYER + " " + "Alice Bob t/condo t/4rm", expectedFindBuyerCommand);
    }


}
