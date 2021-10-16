package seedu.address.logic.commands.buyer;

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
 * Match buyer to properties in the address book.
 */
public class MatchBuyerCommand extends MatchCommand  {

    public static final String MESSAGE_SUCCESS = "Matched buyer to properties.";

    private final Index index;

    public MatchBuyerCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Buyer> buyerList = model.getFilteredBuyerList();
        Buyer buyer = buyerList.get(index.getZeroBased());
        Set<Tag> buyerTags = buyer.getTags();
        Predicate<Property> propertyFilter = (property) -> {
            // TODO: Eliz's PR
            // return property.getPrice().compareTo(buyer.getMaxPrice());
            return property.getPrice().value > buyer.getMaxPrice().value;
        };

        Comparator<Property> propertyComparator = Comparator.comparing(property ->
                calculateTagDistance(buyerTags, property.getTags()));

        model.updateFilteredAndSortedPropertyList(propertyFilter, propertyComparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
