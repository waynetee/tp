package seedu.address.model.field;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.property.Nameable;

/**
 * Represents a Person in propertywhiz.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Nameable {
    private final Name name;
    private final Phone phone;
    private final Email email;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email) {
        requireAllNonNull(name, phone, email);
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email);
    }

    @Override
    public String toString() {
        return String.format("%s; Phone: %s; Email: %s", name, phone, email);
    }


}
