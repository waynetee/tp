package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.field.Person;
import seedu.address.model.field.Price;
import seedu.address.model.property.Buyer;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Buyer}.
 */
class JsonAdaptedBuyer extends JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Buyer's %s field is missing!";

    private final String maxPrice;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBuyer} with the given buyer details.
     */
    @JsonCreator
    public JsonAdaptedBuyer(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email, @JsonProperty("maxPrice") String maxPrice,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        super(name, phone, email);
        this.maxPrice = maxPrice;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Buyer} into this class for Jackson use.
     */
    public JsonAdaptedBuyer(Buyer source) {
        super(source);
        this.maxPrice = source.getPrice().value.toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted buyer object into the model's {@code Buyer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted buyer.
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

        final Set<Tag> modelTags = new HashSet<>();
        for (JsonAdaptedTag tag : tagged) {
            modelTags.add(tag.toModelType());
        }
        return new Buyer(modelPerson, modelPrice, modelTags);
    }

}
