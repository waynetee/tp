package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Person;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;

public class Buyer extends Person implements Listable {

    private final Price maxPrice;
    // TODO: add Set<Tags> tags

    /**
     * Every field must be present and not null.
     */
    public Buyer(Person person, Price maxPrice) {
        super(person.getName(), person.getPhone(), person.getEmail());
        requireNonNull(maxPrice);
        this.maxPrice = maxPrice;
    }

    /**
     * Every field must be present and not null.
     */
    public Buyer(Name name, Phone phone, Email email, Price maxPrice) {
        super(name, phone, email);
        requireAllNonNull(maxPrice);
        this.maxPrice = maxPrice;
    }

    public Price getMaxPrice() {
        return maxPrice;
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
                && otherBuyer.getMaxPrice().equals(getMaxPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhone(), getEmail(), maxPrice);
    }

    @Override
    public String toString() {
        return String.format("%s; Maximum Price: %s", super.toString(), maxPrice);
    }
}
