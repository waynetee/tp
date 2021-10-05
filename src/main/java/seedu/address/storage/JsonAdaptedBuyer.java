package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.field.Person;
import seedu.address.model.field.Price;

/**
 * Jackson-friendly version of {@link Buyer}.
 */
class JsonAdaptedBuyer extends JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Buyer's %s field is missing!";

    private final String maxPrice;

    /**
     * Constructs a {@code JsonAdaptedBuyer} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedBuyer(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("maxPrice") String maxPrice) {
        super(name, phone, email);
        this.maxPrice = maxPrice;
    }

    /**
     * Converts a given {@code Buyer} into this class for Jackson use.
     */
    public JsonAdaptedBuyer(Buyer source) {
        super(source);
        this.maxPrice = source.getMaxPrice().value;
    }

    /**
     * TODO
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Buyer toModelType() throws IllegalValueException {
        Person modelPerson = super.toModelType();

        if (maxPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(maxPrice)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(maxPrice);

        return new Buyer(modelPerson, modelPrice);
    }

}
