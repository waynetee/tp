package seedu.address.model.property;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.field.Name;
import seedu.address.model.field.Person;
import seedu.address.model.field.Price;
import seedu.address.model.tag.Tag;

/**
 * Represents a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Property implements Listable, Taggable, Nameable, Pricable {
    private final Name name;
    private final Address address;
    private final Person seller;
    private final Price price;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Property(Name name, Address address, Person seller, Price min, Set<Tag> tags) {
        requireAllNonNull(name, address, seller, min, tags);
        this.name = name;
        this.address = address;
        this.seller = seller;
        this.price = min;
        this.tags.addAll(tags);
    }

    @Override
    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Person getSeller() {
        return seller;
    }

    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Price getPrice() {
        return price;
    }

    /**
     * Returns true if the {@code Listable} item is a {@code Property} and has the same name.
     * Used by the UniqueList to identify unique properties as a Listable.
     */
    @Override
    public boolean isSameListable(Listable item) {
        if (!(item instanceof Property)) {
            return false;
        }
        return isSameProperty((Property) item);
    }

    /**
     * Returns true if both properties have the same name.
     * This defines a weaker notion of equality between two properties.
     */
    public boolean isSameProperty(Property otherProperty) {
        if (otherProperty == this) {
            return true;
        }

        return otherProperty != null
                && otherProperty.getAddress().equals(getAddress());
    }

    /**
     * Returns true if both properties have the same identity and data fields.
     * This defines a stronger notion of equality between two properties.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Property)) {
            return false;
        }

        Property otherProperty = (Property) other;
        return otherProperty.getName().equals(getName())
                && otherProperty.getAddress().equals(getAddress())
                && otherProperty.getSeller().equals(getSeller())
                && otherProperty.getPrice().equals(getPrice())
                && otherProperty.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, address, seller, price, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Address: ")
                .append(getAddress())
                .append("; Seller: ")
                .append(getSeller())
                .append("; Price: $")
                .append(getPrice());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

    public static Comparator<Property> getPriceComparator() {
        return Comparator.comparing(Property::getPrice);
    }

    public static Comparator<Property> getNameComparator() {
        return Comparator.comparing(Property::getName);
    }
}
