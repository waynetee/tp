package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.field.ContainsTagsPredicate;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTagArgs_throwsParseException() {
        assertParseFailure(parser, " t/", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validKeywordArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);

    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("condo"));
        tags.add(new Tag("4rm"));
        FindCommand expectedFindCommand = new FindCommand(new NameContainsKeywordsPredicate(),
                new ContainsTagsPredicate(tags));
        assertParseSuccess(parser, " t/condo t/4rm", expectedFindCommand);
        assertParseSuccess(parser, " t/4rm t/condo", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n t/4rm \n \t t/condo \t", expectedFindCommand);
    }

    @Test
    public void parse_validKeywordAndTagArgs_returnsFindCommand() {
        List<String> keywords = Arrays.asList("Alice", "Bob");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("condo"));
        tags.add(new Tag("4rm"));
        FindCommand expectedFindCommand = new FindCommand(new NameContainsKeywordsPredicate(keywords),
                new ContainsTagsPredicate(tags));
        assertParseSuccess(parser, "Alice Bob t/condo t/4rm", expectedFindCommand);
    }


}
