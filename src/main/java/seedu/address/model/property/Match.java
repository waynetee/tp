package seedu.address.model.property;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;

/**
 * Represents a matching between Property and Buyer in the address book.
 */
public class Match implements Listable {
    /** Bonus to match score if price is within budget. */
    public static final int WITHIN_BUDGET_BONUS = 2;

    private final Property property;
    private final Buyer buyer;

    /**
     * Creates a match between a property and a buyer.
     */
    public Match(Property property, Buyer buyer) {
        CollectionUtil.requireAllNonNull(property, buyer);
        this.property = property;
        this.buyer = buyer;
    }

    public Property getProperty() {
        return property;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    /**
     * Returns number of tags that a buyer and property have in common.
     */
    public static int getNumCommonTags(Buyer buyer, Property property) {
        Set<Tag> intersection = new HashSet<>(buyer.getTags());
        intersection.retainAll(property.getTags());
        return intersection.size();
    }


    /**
     * Returns a score representing how compatible a buyer is with a property.
     */
    public int getMatchScore() {
        int numCommonTags = getNumCommonTags(buyer, property);
        int priceScore = buyer.getPrice().isGreaterThanOrEqualTo(property.getPrice()) ? WITHIN_BUDGET_BONUS : 0;
        return numCommonTags + priceScore;
    }

    /**
     * Returns the absolute difference between the buyer's budget and property price.
     */
    public long getPriceGap() {
        return Math.abs(getPriceDifference());
    }

    /**
     * Returns the difference between the buyer's budget and property price.
     *
     * @return Buyer's budget - property price.
     */
    public long getPriceDifference() {
        long budget = buyer.getPrice().value;
        long price = property.getPrice().value;
        return budget - price;
    }

    /**
     * Returns true if the {@code Listable} item is a {@code Match} and has the same property and buyer.
     * Used by the UniqueList to identify unique matches as a Listable.
     */
    @Override
    public boolean isSameListable(Listable item) {
        if (!(item instanceof Match)) {
            return false;
        }
        return isSameMatch((Match) item);
    }

    /**
     * Returns true if both matches have the same name.
     * This defines a weaker notion of equality between two matches.
     */
    public boolean isSameMatch(Match match) {
        if (match == this) {
            return true;
        }

        if (match == null) {
            return false;
        }

        return this.property.isSameProperty(match.getProperty())
                && this.buyer.isSameBuyer(match.getBuyer());
    }

    /**
     * Returns true if both matches have the same identity and data fields.
     * This defines a stronger notion of equality between two matches.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Match)) {
            return false;
        }

        Match otherMatch = (Match) other;
        return otherMatch.getProperty().equals(getProperty())
                && otherMatch.getBuyer().equals(getBuyer());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(property, buyer);
    }

    @Override
    public String toString() {
        return property.toString() + " <--> " + buyer.toString();
    }

}
