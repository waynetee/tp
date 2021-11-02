package seedu.address.logic.commands.buyer;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.field.ContainsPricePredicate;
import seedu.address.model.field.ContainsTagsPredicate;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.model.property.Buyer;

/**
 * Finds and lists all buyers in address book whose name contains
 * any of the argument keywords and whose tags contain all the argument tags.
 */
public class FindBuyerCommand extends FindCommand<Buyer> {
    /**
     * Creates a FindBuyerCommand with only a name predicate.
     *
     * @param namePredicate Buyer predicate checking for name match.
     */
    public FindBuyerCommand(NameContainsKeywordsPredicate<Buyer> namePredicate) {
        super(namePredicate);
    }

    /**
     * Creates a FindBuyerCommand that composes the given predicates.
     *
     * @param namePredicate Buyer predicate checking for name match.
     * @param tagsPredicate Buyer predicate checking for containment of tags.
     */
    public FindBuyerCommand(NameContainsKeywordsPredicate<Buyer> namePredicate,
                            ContainsTagsPredicate<Buyer> tagsPredicate,
                            ContainsPricePredicate<Buyer> pricePredicate) {
        super(namePredicate, tagsPredicate, pricePredicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBuyerList(getComposedPredicate());
        if (model.getFilteredBuyerList().size() == 1) {
            return new CommandResult(Messages.MESSAGE_BUYER_LISTED_OVERVIEW);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_BUYERS_LISTED_OVERVIEW, model.getFilteredBuyerList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof FindBuyerCommand) && super.equals(other);
    }
}
