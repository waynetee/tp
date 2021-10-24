package seedu.address.model.property;

import seedu.address.model.field.Name;

/**
 * An interface that represent any objects composed with {@code Name}.
 */
public interface Nameable {
    /** Returns the name of the object */
    Name getName();
}
