package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_ACTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_PREAMBLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.buyer.EditBuyerCommand;
import seedu.address.logic.commands.property.EditPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Actor;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    private static final int ACTOR_POSITIONAL_INDEX = 0;
    private static final int INDEX_POSITIONAL_INDEX = 1;
    private static final int NUM_OF_PREAMBLE_ARGS = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Actor actor;
        try {
            actor = ParserUtil.parseActor(args, ACTOR_POSITIONAL_INDEX);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_ACTOR,
                    EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE));
        }

        switch (actor) {
        case PROPERTY:
            return getEditPropertyCommand(args);
        case BUYER:
            return getEditBuyerCommand(args);
        default:
            assert false;
            return null;
        }
    }

    private EditPropertyCommand getEditPropertyCommand(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_SELLER, PREFIX_PRICE, PREFIX_TAG, PREFIX_ADD_TAG, PREFIX_DELETE_TAG);

        assertPreambleSize(argMultimap.getPreamble());
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble(), INDEX_POSITIONAL_INDEX);

        EditPropertyCommand.EditPropertyDescriptor editPropertyDescriptor = getEditPropertyDescriptor(argMultimap);
        return new EditPropertyCommand(index, editPropertyDescriptor);
    }

    private EditBuyerCommand getEditBuyerCommand(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_PRICE, PREFIX_TAG, PREFIX_ADD_TAG, PREFIX_DELETE_TAG);

        assertPreambleSize(argMultimap.getPreamble());
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble(), INDEX_POSITIONAL_INDEX);

        EditBuyerCommand.EditBuyerDescriptor editBuyerDescriptor = getEditBuyerDescriptor(argMultimap);
        return new EditBuyerCommand(index, editBuyerDescriptor);
    }

    private void assertPreambleSize(String preamble) throws ParseException {
        try {
            ParserUtil.assertPreambleArgsCount(preamble, NUM_OF_PREAMBLE_ARGS);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_PREAMBLE, EditCommand.EXPECTED_PREAMBLE, preamble));
        }
    }

    private EditPropertyCommand.EditPropertyDescriptor getEditPropertyDescriptor(ArgumentMultimap argMultimap)
            throws ParseException {
        EditPropertyCommand.EditPropertyDescriptor editPropertyDescriptor = new EditPropertyCommand
                .EditPropertyDescriptor();
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
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG), true)
                .ifPresent(editPropertyDescriptor::setTags);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_ADD_TAG), false)
                .ifPresent(editPropertyDescriptor::setTagsToAdd);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_DELETE_TAG), false)
                .ifPresent(editPropertyDescriptor::setTagsToDelete);

        if (!editPropertyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPropertyCommand.MESSAGE_NOT_EDITED + "\n" + EditPropertyCommand.MESSAGE_USAGE);
        }

        if (editPropertyDescriptor.isTagsBothResetAndModified()) {
            throw new ParseException(EditCommand.MESSAGE_RESET_TAG_TOGETHER_WITH_MODIFY_TAG);
        }

        if (editPropertyDescriptor.isAddAndDeleteTagsOverlapping()) {
            throw new ParseException(EditCommand.MESSAGE_DUPLICATE_ADD_AND_DELETE_TAG);
        }
        return editPropertyDescriptor;
    }

    private EditBuyerCommand.EditBuyerDescriptor getEditBuyerDescriptor(ArgumentMultimap argMultimap)
            throws ParseException {
        EditBuyerCommand.EditBuyerDescriptor editBuyerDescriptor = new EditBuyerCommand.EditBuyerDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editBuyerDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editBuyerDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editBuyerDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editBuyerDescriptor.setMaxPrice(ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG), true)
                .ifPresent(editBuyerDescriptor::setTags);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_ADD_TAG), false)
                .ifPresent(editBuyerDescriptor::setTagsToAdd);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_DELETE_TAG), false)
                .ifPresent(editBuyerDescriptor::setTagsToDelete);

        if (!editBuyerDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBuyerCommand.MESSAGE_NOT_EDITED + "\n" + EditBuyerCommand.MESSAGE_USAGE);
        }

        if (editBuyerDescriptor.isTagsBothResetAndModified()) {
            throw new ParseException(EditCommand.MESSAGE_RESET_TAG_TOGETHER_WITH_MODIFY_TAG);
        }

        if (editBuyerDescriptor.isAddAndDeleteTagsOverlapping()) {
            throw new ParseException(EditCommand.MESSAGE_DUPLICATE_ADD_AND_DELETE_TAG);
        }

        return editBuyerDescriptor;
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
