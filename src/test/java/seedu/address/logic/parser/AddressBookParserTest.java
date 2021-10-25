package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_PROPERTY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandPreAction;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.commands.buyer.ExportBuyersCommand;
import seedu.address.logic.commands.buyer.ImportBuyersCommand;
import seedu.address.logic.commands.property.AddPropertyCommand;
import seedu.address.logic.commands.property.DeletePropertyCommand;
import seedu.address.logic.commands.property.EditPropertyCommand;
import seedu.address.logic.commands.property.EditPropertyCommand.EditPropertyDescriptor;
import seedu.address.logic.commands.property.ExportPropertiesCommand;
import seedu.address.logic.commands.property.FindPropertyCommand;
import seedu.address.logic.commands.property.ImportPropertiesCommand;
import seedu.address.logic.commands.property.MatchPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.model.property.Property;
import seedu.address.testutil.EditPropertyDescriptorBuilder;
import seedu.address.testutil.PropertyBuilder;
import seedu.address.testutil.PropertyUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Property property = new PropertyBuilder().build();
        AddPropertyCommand command =
                (AddPropertyCommand) parser.parseSimpleCommand(PropertyUtil.getAddCommand(property));
        assertEquals(new AddPropertyCommand(property), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseSimpleCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseSimpleCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePropertyCommand command = (DeletePropertyCommand) parser.parseSimpleCommand(
                DeleteCommand.COMMAND_WORD + " " + PREAMBLE_PROPERTY + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeletePropertyCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Property property = new PropertyBuilder().build();
        EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder(property).build();
        EditPropertyCommand command = (EditPropertyCommand) parser.parseSimpleCommand(EditCommand.COMMAND_WORD + " "
                + PREAMBLE_PROPERTY + " " + INDEX_FIRST.getOneBased() + " "
                + PropertyUtil.getEditPropertyDescriptorDetails(descriptor));
        assertEquals(new EditPropertyCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_match() throws Exception {
        MatchPropertyCommand command = (MatchPropertyCommand) parser.parseSimpleCommand(
                MatchCommand.COMMAND_WORD + " " + PREAMBLE_PROPERTY + " " + INDEX_FIRST.getOneBased());
        MatchPropertyCommand expected = new MatchPropertyCommand(INDEX_FIRST);
        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseSimpleCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseSimpleCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseSimpleCommand(
                FindCommand.COMMAND_WORD + " " + PREAMBLE_PROPERTY + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPropertyCommand(new NameContainsKeywordsPredicate<>(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseSimpleCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseSimpleCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseSimpleCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseSimpleCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        HelpCommand.MESSAGE_USAGE), () -> parser.parseSimpleCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseSimpleCommand("unknownCommand"));
    }

    @Test
    public void parseCommandWithFile_unrecognisedInput_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);
        assertThrows(ParseException.class, expectedMessage, () -> parser.parseCommandWithFile(""));
    }

    @Test
    public void parseCommandWithFile_help() throws Exception {
        assertTrue(parser.parseCommandWithFile(HelpCommand.COMMAND_WORD).isEmpty());
        assertTrue(parser.parseCommandWithFile(HelpCommand.COMMAND_WORD + " 3").isEmpty());
    }

    @Test
    public void parseCommandWithFile_export() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertThrows(ParseException.class, expectedMessage, () ->
                parser.parseCommandWithFile(ExportCommand.COMMAND_WORD));
        assertThrows(ParseException.class, expectedMessage, () ->
                parser.parseCommandWithFile(ExportCommand.COMMAND_WORD + " 1"));
        assertTrue(parser.parseCommandWithFile(ExportCommand.COMMAND_WORD + " " + ExportCommand.PROPERTIES)
                .get() instanceof ExportPropertiesCommand);
        assertTrue(parser.parseCommandWithFile(ExportCommand.COMMAND_WORD + " " + ExportCommand.BUYERS)
                .get() instanceof ExportBuyersCommand);
    }

    @Test
    public void parseCommandWithFile_import() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertThrows(ParseException.class, expectedMessage, () ->
                parser.parseCommandWithFile(ImportCommand.COMMAND_WORD));
        assertThrows(ParseException.class, expectedMessage, () ->
                parser.parseCommandWithFile(ImportCommand.COMMAND_WORD + " 1"));
        assertTrue(parser.parseCommandWithFile(ImportCommand.COMMAND_WORD + " " + ImportCommand.PROPERTIES)
                .get() instanceof ImportPropertiesCommand);
        assertTrue(parser.parseCommandWithFile(ImportCommand.COMMAND_WORD + " " + ImportCommand.BUYERS)
                .get() instanceof ImportBuyersCommand);
    }

    @Test
    public void getCommandPreAction_help() throws Exception {
        assertFalse(parser.getCommandPreAction(HelpCommand.COMMAND_WORD).requiresFile());
        assertFalse(parser.getCommandPreAction(HelpCommand.COMMAND_WORD + " 3").requiresFile());
    }

    @Test
    public void getCommandPreAction_export() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertThrows(ParseException.class, expectedMessage, () ->
                parser.getCommandPreAction(ExportCommand.COMMAND_WORD));
        assertThrows(ParseException.class, expectedMessage, () ->
                parser.getCommandPreAction(ExportCommand.COMMAND_WORD + " 1"));
        assertTrue(parser.getCommandPreAction(ExportCommand.COMMAND_WORD + " " + ExportCommand.PROPERTIES)
                .equals(new CommandPreAction(ExportCommand.COMMAND_WORD + " " + ExportCommand.PROPERTIES, true)));
        assertTrue(parser.getCommandPreAction(ExportCommand.COMMAND_WORD + " " + ExportCommand.BUYERS)
                .equals(new CommandPreAction(ExportCommand.COMMAND_WORD + " " + ExportCommand.BUYERS, true)));
    }

    @Test
    public void getCommandPreAction_import() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertThrows(ParseException.class, expectedMessage, () ->
                parser.getCommandPreAction(ImportCommand.COMMAND_WORD));
        assertThrows(ParseException.class, expectedMessage, () ->
                parser.getCommandPreAction(ImportCommand.COMMAND_WORD + " 1"));
        assertTrue(parser.getCommandPreAction(ImportCommand.COMMAND_WORD + " " + ImportCommand.PROPERTIES)
                .equals(new CommandPreAction(ImportCommand.COMMAND_WORD + " " + ImportCommand.PROPERTIES, false)));
        assertTrue(parser.getCommandPreAction(ImportCommand.COMMAND_WORD + " " + ImportCommand.BUYERS)
                .equals(new CommandPreAction(ImportCommand.COMMAND_WORD + " " + ImportCommand.BUYERS, false)));
    }
}
