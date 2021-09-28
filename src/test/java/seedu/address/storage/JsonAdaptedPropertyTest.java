package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedProperty.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.Address;
import seedu.address.model.property.Email;
import seedu.address.model.property.Name;
import seedu.address.model.property.Phone;
import seedu.address.model.property.Price;
import seedu.address.model.property.Seller;

public class JsonAdaptedPropertyTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SELLER = "Br@yn";
    private static final String INVALID_PRICE = "100k";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_SELLER = BENSON.getSeller().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_PRICE = BENSON.getPrice().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPropertyDetails_returnsProperty() throws Exception {
        JsonAdaptedProperty property = new JsonAdaptedProperty(BENSON);
        assertEquals(BENSON, property.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_SELLER, VALID_PRICE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_SELLER, VALID_PRICE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_SELLER, VALID_PRICE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_SELLER, VALID_PRICE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_SELLER, VALID_PRICE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TAGS, VALID_SELLER, VALID_PRICE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS,
                        VALID_SELLER, VALID_PRICE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, VALID_SELLER, VALID_PRICE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags,
                        VALID_SELLER, VALID_PRICE);
        assertThrows(IllegalValueException.class, property::toModelType);
    }

    @Test
    public void toModelType_invalidSeller_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        INVALID_SELLER, VALID_PRICE);
        String expectedMessage = Seller.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullSeller_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, null, VALID_PRICE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Seller.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_SELLER, INVALID_PRICE);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_SELLER, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

}
