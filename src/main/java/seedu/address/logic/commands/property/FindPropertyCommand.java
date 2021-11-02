package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.field.ContainsPricePredicate;
import seedu.address.model.field.ContainsTagsPredicate;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.model.property.Property;

/**
 * Finds and lists all properties in address book whose name contains
 * any of the argument keywords and whose tags contain all the argument tags.
 */
public class FindPropertyCommand extends FindCommand<Property> {

    /**
     * Creates a FindPropertyCommand with only a name predicate.
     *
     * @param namePredicate Property predicate checking for name match.
     */
    public FindPropertyCommand(NameContainsKeywordsPredicate<Property> namePredicate) {
        super(namePredicate);
    }

    /**
     * Creates a FindCommand that composes the given predicates.
     *
     * @param namePredicate Property predicate checking for name match.
     * @param tagsPredicate Property predicate checking for containment of tags.
     */
    public FindPropertyCommand(NameContainsKeywordsPredicate<Property> namePredicate,
                               ContainsTagsPredicate<Property> tagsPredicate,
                               ContainsPricePredicate<Property> pricePredicate) {
        super(namePredicate, tagsPredicate, pricePredicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPropertyList(getComposedPredicate());
        if (model.getFilteredPropertyList().size() == 1) {
            return new CommandResult(Messages.MESSAGE_PROPERTY_LISTED_OVERVIEW);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PROPERTIES_LISTED_OVERVIEW, model.getFilteredPropertyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof FindPropertyCommand) && super.equals(other);
    }
}
