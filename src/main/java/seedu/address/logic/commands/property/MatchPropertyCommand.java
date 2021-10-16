package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.model.Model;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;

/**
 * Match property to buyers in the address book.
 */
public class MatchPropertyCommand extends MatchCommand {
    public static final String MESSAGE_SUCCESS = "Matched property to buyers.";

    public MatchPropertyCommand(Index index) {
        super(index);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Property> propertyList = model.getFilteredPropertyList();
        Property property = propertyList.get(index.getZeroBased());
        Predicate<Property> currentPropertyFilter = (p) -> p.equals(property);
        Set<Tag> propertyTags = property.getTags();
        Predicate<Buyer> buyerFilter = (buyer) -> {
            // TODO: Eliz's PR
            // return buyer.getMaxPrice().compareTo(property.getPrice());
            return buyer.getMaxPrice().value >= property.getPrice().value;
        };

        Comparator<Buyer> buyerComparator = Comparator.<Buyer, Integer>comparing(buyer ->
                calculateTagIntersection(propertyTags, buyer.getTags())
        ).thenComparingLong(buyer -> buyer.getMaxPrice().value).reversed();

        model.updateFilteredPropertyList(currentPropertyFilter);
        model.updateFilteredAndSortedBuyerList(buyerFilter, buyerComparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }


}
