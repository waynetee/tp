package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Person;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;

public class Buyer extends Person {

    private final Price max;
    // TODO: add Set<Tags> tags

    /**
     * Every field must be present and not null.
     */
    public Buyer(Person person, Price max) {
        super(person.getName(), person.getPhone(), person.getEmail());
        requireNonNull(max);
        this.max = max;
    }

    /**
     * Every field must be present and not null.
     */
    public Buyer(Name name, Phone phone, Email email, Price max) {
        super(name, phone, email);
        requireAllNonNull(max);
        this.max = max;
    }

    public Price getPrice() {
        return max;
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
                && otherBuyer.isSamePerson(this);
    }

    /**
     * Returns true if both buyers have the same identity, data fields, and
     * maximum price. This defines a stronger notion of equality between
     * two properties.
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
                && otherBuyer.getPrice().equals(getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhone(), getEmail(), max);
    }

    @Override
    public String toString() {
        return String.format("%s; Maximum Price: %s", super.toString(), max);
    }
}
