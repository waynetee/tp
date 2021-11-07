package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.field.Actor;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;
import seedu.address.model.property.Address;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_ACTOR = "Not 'property' or 'buyer'.";
    public static final String MESSAGE_INVALID_ACTOR_ARGUMENT = "Argument %d is neither 'property' nor 'buyer'.";
    public static final String MESSAGE_INVALID_INDEX = "Index %s is not a positive number.";
    public static final String MESSAGE_INVALID_INDEX_ARGUMENT = "Argument %d is not in range.";
    public static final String MESSAGE_INVALID_SORT_TYPE_ARGUMENT = "Argument %d is not a sort type.";
    public static final String MESSAGE_INVALID_SORT_DIR_ARGUMENT = "Argument %d is not a sort direction.";
    public static final String MESSAGE_INVALID_NUM_ARGUMENTS = "Expected %d arguments, provided %d arguments.";
    public static final String MESSAGE_INVALID_PREAMBLE = "Invalid target.\n"
            + "Expected: %s\n"
            + "Received: %s\n";
    public static final String MESSAGE_INVALID_FIND_PRICE_PREFIX = "Invalid command. Only one %s is allowed.";

    public static final List<String> PROPERTY_PATTERN = Arrays.asList("property", "properties");

    public static final List<String> BUYER_PATTERN = Arrays.asList("buyer", "buyers");

    /**
     * Parses {@code actor} into an {@code Preamble.Actor} using given string.
     *
     * @param actor String that represents an {@code actor}.
     * @throws ParseException if the given string does not match any of the actors.
     */
    public static Actor parseActor(String actor) throws ParseException {
        String trimmedActor = actor.trim();

        if (PROPERTY_PATTERN.contains(trimmedActor)) {
            return Actor.PROPERTY;
        } else if (BUYER_PATTERN.contains(trimmedActor)) {
            return Actor.BUYER;
        } else {
            throw new ParseException(MESSAGE_INVALID_ACTOR);
        }
    }

    /**
     * Parses {@code actor} into an {@code Preamble.Actor} using given string and index.
     *
     * @param actor String that represents an {@code actor}.
     * @param index Integer that represents the position of the actor in the string separated by spaces.
     *
     * @throws ParseException if the given string does not match any of the actors.
     */
    public static Actor parseActor(String actor, int index) throws ParseException {
        String[] splitInputs = actor.trim().split("\\s+");
        if (index >= splitInputs.length) {
            throw new ParseException(String.format(MESSAGE_INVALID_ACTOR_ARGUMENT, index));
        }
        return parseActor(splitInputs[index]);
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
            throw new ParseException(String.format(MESSAGE_INVALID_INDEX, trimmedIndex));
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses string {@code oneBasedIndex} at the given index into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex, int index) throws ParseException {
        String[] splitInputs = oneBasedIndex.trim().split("\\s+");
        if (index >= splitInputs.length) {
            throw new ParseException(String.format(MESSAGE_INVALID_INDEX_ARGUMENT, index));
        }
        return parseIndex(splitInputs[index]);
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

    /**
     * Parses a {@code String sortType} into a {@code SortType}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sortType} is invalid.
     */
    public static SortType parseSortType(String sortType) throws ParseException {
        requireNonNull(sortType);
        String trimmedSortType = sortType.trim();
        if (trimmedSortType.equals("price")) {
            return SortType.PRICE;
        } else if (trimmedSortType.equals("name")) {
            return SortType.NAME;
        } else {
            throw new ParseException(SortType.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String sortType} at the given index into a {@code SortType}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sortType} is invalid.
     */
    public static SortType parseSortType(String sortType, int index) throws ParseException {
        String[] splitInputs = sortType.trim().split("\\s+");
        if (index >= splitInputs.length) {
            throw new ParseException(String.format(MESSAGE_INVALID_SORT_TYPE_ARGUMENT, index));
        }
        return parseSortType(splitInputs[index]);
    }

    /**
     * Parses a {@code String sortDir} into a {@code SortDir}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sortDir} is invalid.
     */
    public static SortDirection parseSortDir(String sortDir) throws ParseException {
        requireNonNull(sortDir);
        String trimmedSortType = sortDir.trim();
        if (trimmedSortType.equals("asc")) {
            return SortDirection.ASC;
        } else if (trimmedSortType.equals("desc")) {
            return SortDirection.DESC;
        } else {
            throw new ParseException(SortDirection.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String sortDir} at the given index into a {@code SortDir}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sortDir} is invalid.
     */
    public static SortDirection parseSortDir(String sortDir, int index) throws ParseException {
        String[] splitInputs = sortDir.trim().split("\\s+");
        if (index >= splitInputs.length) {
            throw new ParseException(String.format(MESSAGE_INVALID_SORT_DIR_ARGUMENT, index));
        }
        return parseSortDir(splitInputs[index]);
    }

    /**
     * Asserts that the correct number of args separated by spaces are present in {@code args}.
     *
     * @throws ParseException if the given number of args separated by spaces is not equal to {@code numOfPreamble}.
     */
    public static void assertPreambleArgsCount(String args, int numOfPreamble) throws ParseException {
        String[] splitInputs = args.trim().split("\\s+");
        if (numOfPreamble != splitInputs.length) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_NUM_ARGUMENTS, numOfPreamble, splitInputs.length));
        }
    }

    /**
     * Parses the only {@code String} price in {@code Collection<String> prices} into a {@code Price}.
     *
     * @throws ParseException if more than one price is found in {@code prices}
     *                        or the given input is an invalid {@code Price}.
     */
    public static Price parseFindPrice(Collection<String> prices, Prefix prefix) throws ParseException {
        if (prices.size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_FIND_PRICE_PREFIX, prefix));
        }

        try {
            return prices.size() == 0 ? null : new Price(prices.iterator().next());
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
