package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPropertyDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_SELLER, PREFIX_PRICE, PREFIX_ADD_TAG,
                PREFIX_DELETE_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditPropertyDescriptor editPropertyDescriptor = new EditPropertyDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPropertyDescriptor.setPropertyName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPropertyDescriptor.setSellerPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPropertyDescriptor.setSellerEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPropertyDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_SELLER).isPresent()) {
            editPropertyDescriptor.setSellerName(ParserUtil.parseName(argMultimap.getValue(PREFIX_SELLER).get()));
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editPropertyDescriptor.setPrice(ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG), true).ifPresent(editPropertyDescriptor::setTags);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_ADD_TAG), false).ifPresent(editPropertyDescriptor::setTagsToAdd);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_DELETE_TAG), false)
                .ifPresent(editPropertyDescriptor::setTagsToDelete);

        if (!editPropertyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        if (editPropertyDescriptor.isTagsBothResetAndModified()) {
            throw new ParseException(EditCommand.MESSAGE_RESET_TAG_TOGETHER_WITH_MODIFY_TAG);
        }

        if (editPropertyDescriptor.isAddAndDeleteTagsOverlapping()) {
            throw new ParseException(EditCommand.MESSAGE_DUPLICATE_ADD_AND_DELETE_TAG);
        }

        return new EditCommand(index, editPropertyDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code isEmptyStringDefaultsToEmptySet} is set to true and {@code tags} contain only one element
     * which is an empty string, it will be parsed into a {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags, boolean isEmptyStringDefaultsToEmptySet)
            throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet;
        if (tags.size() == 1 && tags.contains("") && isEmptyStringDefaultsToEmptySet) {
            tagSet = Collections.emptySet();
        } else {
            tagSet = tags;
        }
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
