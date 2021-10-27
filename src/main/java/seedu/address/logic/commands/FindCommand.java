package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.address.model.field.ContainsPricePredicate;
import seedu.address.model.field.ContainsTagsPredicate;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.model.property.Nameable;
import seedu.address.model.property.Pricable;
import seedu.address.model.property.Taggable;

/**
 * Finds and lists all { properties or buyers } in address book whose name contains any of the
 * argument keywords and whose tags contain all the argument tags.
 * Keyword matching is case-insensitive.
 * Tag matching is case-insensitive.
 */
public abstract class FindCommand<T extends Nameable & Taggable & Pricable> extends SimpleCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all properties whose names contain any of "
            + "the specified keywords (case-insensitive) "
            + "and whose tags contain all of the specified tags "
            + "and whose prices is greater or equal to the min price (if specified) "
            + "and whose prices is smaller or equal to the max price (if specified).\n"
            + "The matching properties are displayed as a list with index numbers.\n"
            + "Parameters: (properties | buyers) KEYWORD [MORE_KEYWORDS]..."
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_MIN_PRICE + "PRICE] "
            + "[" + PREFIX_MAX_PRICE + "PRICE] "
            + "\n"
            + "Example: " + COMMAND_WORD + " properties alice bob charlie "
            + PREFIX_TAG + "Condo "
            + PREFIX_TAG + "4 rm "
            + PREFIX_MIN_PRICE + "100000 "
            + PREFIX_MAX_PRICE + "10000000";

    private final NameContainsKeywordsPredicate<T> namePredicate;
    private final ContainsTagsPredicate<T> tagsPredicate;
    private final ContainsPricePredicate<T> pricePredicate;
    private final Predicate<T> composedPredicate;

    /**
     * Creates a FindCommand with only a name predicate.
     *
     * @param namePredicate Nameable predicate checking for name match.
     */
    public FindCommand(NameContainsKeywordsPredicate<T> namePredicate) {
        this(namePredicate, new ContainsTagsPredicate<>(), new ContainsPricePredicate<>());
    }

    /**
     * Creates a FindCommand that composes the given predicates.
     *
     * @param namePredicate Nameable predicate checking for name match.
     * @param tagsPredicate Taggable predicate checking for containment of tags.
     * @param pricePredicate Pricable predicate checking for price match.
     */
    public FindCommand(NameContainsKeywordsPredicate<T> namePredicate,
                       ContainsTagsPredicate<T> tagsPredicate,
                       ContainsPricePredicate<T> pricePredicate) {
        this.namePredicate = namePredicate;
        this.tagsPredicate = tagsPredicate;
        this.pricePredicate = pricePredicate;
        this.composedPredicate = namePredicate.and(tagsPredicate).and(pricePredicate);
    }

    /**
     * Returns the composed predicate of all of
     * the {@code namePredicate}, {@code tagPredicate} and {@code pricePredicate}.
     */
    public Predicate<T> getComposedPredicate() {
        return composedPredicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && namePredicate.equals(((FindCommand<?>) other).namePredicate)
                && tagsPredicate.equals(((FindCommand<?>) other).tagsPredicate))
                && pricePredicate.equals(((FindCommand<?>) other).pricePredicate); // state check
    }
}
