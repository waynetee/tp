package seedu.address.storage;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Match;
import seedu.address.model.property.Property;

/**
 * Jackson-friendly version of {@link Match}.
 */
class JsonAdaptedMatch {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Match's %s field is missing!";
    private final String propertyName;
    private final String buyerName;

    /**
     * Constructs a {@code JsonAdaptedMatch} with the given match details.
     */
    @JsonCreator
    public JsonAdaptedMatch(@JsonProperty("propertyName") String propertyName,
                            @JsonProperty("buyerName") String buyerName) {
        this.propertyName = propertyName;
        this.buyerName = buyerName;
    }

    /**
     * Converts a given {@code Match} into this class for Jackson use.
     */
    public JsonAdaptedMatch(Match source) {
        propertyName = source.getProperty().getName().toString();
        buyerName = source.getBuyer().getName().toString();
    }

    /**
     * Converts this Jackson-friendly adapted match object into the model's {@code Match} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted match.
     */
    public Match toModelType(Map<String, Property> propertyNameToPropertyMap, Map<String, Buyer> buyerNameToBuyerMap)
            throws IllegalValueException {
        if (propertyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Property.class.getSimpleName()));
        }
        final Property modelProperty = propertyNameToPropertyMap.get(propertyName);

        if (modelProperty == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Property.class.getSimpleName()));
        }

        if (buyerName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Buyer.class.getSimpleName()));
        }
        final Buyer modelBuyer = buyerNameToBuyerMap.get(buyerName);

        if (modelBuyer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Buyer.class.getSimpleName()));
        }

        final Match match = new Match(modelProperty, modelBuyer);
        return match;
    }

}
