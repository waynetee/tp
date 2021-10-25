package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandPreAction;
import seedu.address.logic.commands.CommandWithFile;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.commands.SimpleCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.StatCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseAnyCommand(String userInput) throws ParseException {
        Optional<CommandWithFile> fileCommand = parseCommandWithFile(userInput);
        if (fileCommand.isPresent()) {
            return fileCommand.get();
        }
        return parseSimpleCommand(userInput);
    }

    /**
     * Parses user input into simpleCommand for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public SimpleCommand parseSimpleCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case MatchCommand.COMMAND_WORD:
            return new MatchCommandParser().parse(arguments);

        case BackCommand.COMMAND_WORD:
            return new BackCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case StatCommand.COMMAND_WORD:
            return new StatCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into commandWithFile for execution.
     *
     * @param userInput full user input string
     * @return commandWithFile if a match is found, empty otherwise.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Optional<CommandWithFile> parseCommandWithFile(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case ExportCommand.COMMAND_WORD:
            return Optional.of(new ExportCommandParser().parse(arguments));
        case ImportCommand.COMMAND_WORD:
            return Optional.of(new ImportCommandParser().parse(arguments));
        default:
            return Optional.empty();
        }
    }

    public CommandPreAction getCommandPreAction(String userInput) throws ParseException {
        Optional<CommandWithFile> command = parseCommandWithFile(userInput);
        if (command.isEmpty()) {
            return new CommandPreAction();
        }
        return new CommandPreAction(command.get().toString(),
                command.get().toString().startsWith(ExportCommand.COMMAND_WORD));
    }
}
