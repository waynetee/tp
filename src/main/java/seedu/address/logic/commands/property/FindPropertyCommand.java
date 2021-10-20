package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.field.ContainsTagsPredicate;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.model.property.Property;

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
                               ContainsTagsPredicate<Property> tagsPredicate) {
        super(namePredicate, tagsPredicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPropertyList(getComposedPredicate());
        return new CommandResult(
                String.format(Messages.MESSAGE_PROPERTIES_LISTED_OVERVIEW, model.getFilteredPropertyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof FindPropertyCommand) && super.equals(other);
    }
}
