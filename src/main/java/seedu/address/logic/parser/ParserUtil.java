package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.PreambleData.Actor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.property.Address;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_ACTOR = "Only property or buyer can be specified as target.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_PREAMBLE = "Incorrect number of arguments in preamble."
            + "The following fields are expected:"
            + "\n";
    public static final String[] MESSAGE_PREAMBLE_FIELDS = {"property/buyer", "INDEX"};

    public static final int ACTOR_POSITIONAL_INDEX = 0;
    public static final int INDEX_POSITIONAL_INDEX = 1;

    /**
     * Parses {@code preamble} into varying fields depending on the specified numFields.
     * The fields are, in order of index,
     * 0. Actor (property or buyer)
     * 1. Index (used by edit etc.)
     *
     * @param preamble  Positional arguments in a command.
     * @param numFields Number of positional arguments expected.
     * @throws ParseException if the number of positional arguments is different from {@code numFields}
     *                        or if subsequent parsing of each individual argument throws a ParseException.
     */
    public static PreambleData parsePreamble(String preamble, int numFields) throws ParseException {
        String[] preambleArray = preamble.trim().split(" ");
        if (preambleArray.length != numFields) {
            String expectedFields = "";
            for (int i = 0; i < numFields; i++) {
                expectedFields += MESSAGE_PREAMBLE_FIELDS[i] + " ";
            }
            throw new ParseException(MESSAGE_INVALID_PREAMBLE + expectedFields);
        }

        Actor actor = numFields >= ACTOR_POSITIONAL_INDEX + 1
                ? parseActor(preambleArray[ACTOR_POSITIONAL_INDEX])
                : null;
        Index index = numFields >= INDEX_POSITIONAL_INDEX + 1
                ? parseIndex(preambleArray[INDEX_POSITIONAL_INDEX])
                : null;

        return new PreambleData(actor, index);
    }

    /**
     * Parses {@code actor} into an {@code Preamble.Actor} using given string.
     *
     * @param actor String that represents an {@code actor}.
     * @throws ParseException if the given string does not match any of the actors.
     */
    public static Actor parseActor(String actor) throws ParseException {
        if (actor.equals("property")) {
            return Actor.PROPERTY;
        } else if (actor.equals("buyer")) {
            return Actor.BUYER;
        } else {
            throw new ParseException(MESSAGE_INVALID_ACTOR);
        }
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
