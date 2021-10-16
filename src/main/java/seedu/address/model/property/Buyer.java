package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Person;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.tag.Tag;

/**
 * Represents a Buyer in the address book.
 */
public class Buyer extends Person implements Listable, Taggable {

    private final Price maxPrice;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Match> matches = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Buyer(Person person, Price maxPrice, Set<Tag> tags) {
        super(person.getName(), person.getPhone(), person.getEmail());
        requireAllNonNull(maxPrice, tags);
        this.maxPrice = maxPrice;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Buyer(Name name, Phone phone, Email email, Price maxPrice, Set<Tag> tags) {
        super(name, phone, email);
        requireAllNonNull(maxPrice, tags);
        this.maxPrice = maxPrice;
        this.tags.addAll(tags);
    }

    public Price getMaxPrice() {
        return maxPrice;
    }

    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<Match> getMatches() {
        return Collections.unmodifiableSet(matches);
    }

    /**
     * Adds a match to the current buyer's matches.
     * @param match Match object to be added.
     */
    public void addMatch(Match match) {
        requireNonNull(match);
        if (this.matches.contains(match)) {
            throw new IllegalStateException("This buyer is already matched with the same property.");
        }
        this.matches.add(match);
    }

    /**
     * Matches the current buyer to a {@code property}.
     * @param property Property to be matched to this buyer.
     */
    public void matchToProperty(Property property) {
        requireNonNull(property);
        Match match = Match.createMatch(property, this);
        assert(this.matches.contains(match));
    }

    /**
     * Returns true if both buyers have the same name.
     * This defines a weaker notion of equality between two buyers.
     */
    public boolean isSameBuyer(Buyer otherBuyer) {
        if (otherBuyer == this) {
            return true;
        }

        return otherBuyer != null
                && super.isSamePerson(otherBuyer);
    }

    /**
     * Returns true if the {@code Listable} item is a {@code Buyer} and has the same name.
     * Used by the UniqueList to identify unique buyers as a Listable.
     */
    @Override
    public boolean isSameListable(Listable item) {
        if (!(item instanceof Buyer)) {
            return false;
        }
        return isSameBuyer((Buyer) item);
    }

    /**
     * Returns true if both buyers have the same identity, data fields, and
     * maximum price. This defines a stronger notion of equality between
     * two buyers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Buyer)) {
            return false;
        }

        Buyer otherBuyer = (Buyer) other;
        return super.equals(otherBuyer)
                && otherBuyer.getMaxPrice().equals(getMaxPrice())
                && otherBuyer.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhone(), getEmail(), getMaxPrice(), getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString())
                .append("; Maximum Price: ")
                .append(getMaxPrice());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
