package seedu.address.model.property;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Property {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Seller seller;

    // Data fields
    private final Address address;
    private final Price price;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Property(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Seller seller, Price min) {
        requireAllNonNull(name, phone, email, address, tags, seller);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.seller = seller;
        this.price = min;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Seller getSeller() {
        return seller;
    }

    public Price getPrice() {
        return price;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameProperty(Property otherProperty) {
        if (otherProperty == this) {
            return true;
        }

        return otherProperty != null
                && otherProperty.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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
                && otherProperty.getPhone().equals(getPhone())
                && otherProperty.getSeller().equals(getPhone())
                && otherProperty.getEmail().equals(getEmail())
                && otherProperty.getAddress().equals(getAddress())
                && otherProperty.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, seller);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Address: ")
                .append(getAddress())
                .append("; Seller: ")
                .append(getSeller())
                .append("; Price: ")
                .append(getPrice())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
