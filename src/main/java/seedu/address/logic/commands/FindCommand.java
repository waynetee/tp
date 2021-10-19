package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.field.ContainsTagsPredicate;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.model.property.Property;

/**
 * Finds and lists all properties in address book whose name contains any of the argument keywords and whose tags
 * contain all the argument tags.
 * Keyword matching is case-insensitive.
 * Tag matching is case-insensitive.
 */
public class FindCommand extends SimpleCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all properties whose names contain any of "
            + "the specified keywords (case-insensitive)\n"
            + "and whose tags contain all of the specified tags.\n"
            + "The matching properties are displayed as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]..."
            + "[" + PREFIX_TAG + "TAG]..."
            + "\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie"
            + PREFIX_TAG + "Condo "
            + PREFIX_TAG + "4 rm ";

    private final Predicate<Property> propertyPredicate;
    private final NameContainsKeywordsPredicate namePredicate;
    private final ContainsTagsPredicate tagsPredicate;

    /**
     * Creates a FindCommand with only a name predicate.
     *
     * @param namePredicate Property predicate checking for name match.
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate) {
        this(namePredicate, new ContainsTagsPredicate());
    }

    /**
     * Creates a FindCommand that composes the given predicates.
     *
     * @param namePredicate Property predicate checking for name match.
     * @param tagsPredicate Property predicate checking for containment of tags.
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate, ContainsTagsPredicate tagsPredicate) {
        this.namePredicate = namePredicate;
        this.tagsPredicate = tagsPredicate;
        this.propertyPredicate = namePredicate.and(tagsPredicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPropertyList(propertyPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PROPERTIES_LISTED_OVERVIEW, model.getFilteredPropertyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && namePredicate.equals(((FindCommand) other).namePredicate)
                && tagsPredicate.equals(((FindCommand) other).tagsPredicate)); // state check
    }
}
