package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PROPERTY = "Property list contains duplicate property(s).";
    public static final String MESSAGE_DUPLICATE_BUYER = "Buyer list contains duplicate buyer(s).";
    public static final String MESSAGE_DUPLICATE_MATCH = "Match list contains duplicate match(es).";


    private final List<JsonAdaptedProperty> properties = new ArrayList<>();
    private final List<JsonAdaptedBuyer> buyers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given properties.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("properties") List<JsonAdaptedProperty> properties,
                                       @JsonProperty("buyers") List<JsonAdaptedBuyer> buyers) {
        this.properties.addAll(properties);
        this.buyers.addAll(buyers);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        properties.addAll(source.getPropertyList().stream().map(JsonAdaptedProperty::new).collect(Collectors.toList()));
        buyers.addAll(source.getBuyerList().stream().map(JsonAdaptedBuyer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        Map<String, Property> propertyNameToPropertyMap = new HashMap<>();
        Map<String, Buyer> buyerNameToBuyerMap = new HashMap<>();
        for (JsonAdaptedProperty jsonAdaptedProperty : properties) {
            Property property = jsonAdaptedProperty.toModelType();
            propertyNameToPropertyMap.put(property.getName().toString(), property);
            if (addressBook.hasProperty(property)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROPERTY);
            }
            addressBook.addProperty(property);
        }

        for (JsonAdaptedBuyer jsonAdaptedBuyer : buyers) {
            Buyer buyer = jsonAdaptedBuyer.toModelType();
            buyerNameToBuyerMap.put(buyer.getName().toString(), buyer);
            if (addressBook.hasBuyer(buyer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BUYER);
            }
            addressBook.addBuyer(buyer);
        }

        return addressBook;
    }

}
