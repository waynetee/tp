package seedu.address.model.property;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;

/**
 * Represents a matching between Property and Buyer in the address book.
 */
public class Match implements Listable {
    private final Property property;
    private final Buyer buyer;

    private Match(Property property, Buyer buyer) {
        CollectionUtil.requireAllNonNull(property, buyer);
        this.property = property;
        this.buyer = buyer;
    }

    /**
     * Constructs a Match object representing an association between a {@code property} and {@code buyer}.
     *
     * @param property Property that is to be matched with {@code buyer}.
     * @param buyer    Buyer that is to be matched with {@code property}.
     * @return Match object that is created.
     */
    public static Match createMatch(Property property, Buyer buyer) {
        if (!validateMatch(property, buyer)) {
            throw new IllegalStateException("Invalid matching attempted");
        }
        Match match = new Match(property, buyer);
        property.addMatch(match);
        buyer.addMatch(match);
        return match;
    }

    private static boolean validateMatch(Property property, Buyer buyer) {
        // TODO: ELiz's PR
        return property.getPrice().value <= buyer.getMaxPrice().value;
    }

    public Property getProperty() {
        return property;
    }

    public Buyer getBuyer() {
        return buyer;
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
}
