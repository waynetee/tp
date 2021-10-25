package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.MatchOneToManyCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Match;
import seedu.address.model.property.Property;

/**
 * Match property to buyers in the address book.
 */
public class MatchPropertyCommand extends MatchOneToManyCommand {
    public static final String MESSAGE_SUCCESS = "Matched property to buyers.";

    public MatchPropertyCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Property> propertyList = model.getFilteredPropertyList();
        if (targetIndex.getZeroBased() >= propertyList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
        }

        Property property = propertyList.get(targetIndex.getZeroBased());
        Predicate<Property> currentPropertyFilter = (p) -> p.equals(property);
        Predicate<Buyer> buyerFilter = (buyer) -> buyer.getPrice().isGreaterThanOrEqualTo(property.getPrice());

        Comparator<Buyer> buyerComparator = Comparator.<Buyer, Integer>comparing(buyer ->
                Match.getNumCommonTags(buyer, property)
        ).thenComparingLong(buyer -> buyer.getPrice().value).reversed();

        model.updateFilteredPropertyList(currentPropertyFilter);
        model.updateFilteredAndSortedBuyerList(buyerFilter, buyerComparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MatchPropertyCommand)) {
            return false;
        }

        return super.equals(other);
    }
}
