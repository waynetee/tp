package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.address.model.field.ContainsTagsPredicate;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.model.property.Nameable;
import seedu.address.model.property.Taggable;

/**
 * Finds and lists all { properties or buyers } in address book whose name contains any of the
 * argument keywords and whose tags contain all the argument tags.
 * Keyword matching is case-insensitive.
 * Tag matching is case-insensitive.
 */
public abstract class FindCommand<T extends Nameable & Taggable> extends SimpleCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all properties whose names contain any of "
            + "the specified keywords (case-insensitive) "
            + "and whose tags contain all of the specified tags.\n"
            + "The matching properties are displayed as a list with index numbers.\n\n"
            + "Parameters: (properties | buyers) KEYWORD [MORE_KEYWORDS]..."
            + "[" + PREFIX_TAG + "TAG]..."
            + "\n"
            + "Example: " + COMMAND_WORD + "properties alice bob charlie "
            + PREFIX_TAG + "Condo "
            + PREFIX_TAG + "4 rm ";

    private final NameContainsKeywordsPredicate<T> namePredicate;
    private final ContainsTagsPredicate<T> tagsPredicate;
    private final Predicate<T> composedPredicate;
    /**
     * Creates a FindCommand with only a name predicate.
     *
     * @param namePredicate Nameable predicate checking for name match.
     */
    public FindCommand(NameContainsKeywordsPredicate<T> namePredicate) {
        this(namePredicate, new ContainsTagsPredicate<>());
    }

    /**
     * Creates a FindCommand that composes the given predicates.
     *
     * @param namePredicate Nameable predicate checking for name match.
     * @param tagsPredicate Taggable predicate checking for containment of tags.
     */
    public FindCommand(NameContainsKeywordsPredicate<T> namePredicate, ContainsTagsPredicate<T> tagsPredicate) {
        this.namePredicate = namePredicate;
        this.tagsPredicate = tagsPredicate;
        this.composedPredicate = namePredicate.and(tagsPredicate);
    }

    /**
     * Returns the composed predicate of both the {@code namePredicate} and {@code tagPredicate}.
     */
    public Predicate<T> getComposedPredicate() {
        return composedPredicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && namePredicate.equals(((FindCommand<?>) other).namePredicate)
                && tagsPredicate.equals(((FindCommand<?>) other).tagsPredicate)); // state check
    }
}
