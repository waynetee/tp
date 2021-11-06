package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.field.Name;
import seedu.address.model.field.Price;
import seedu.address.model.property.Address;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Property objects.
 */
public class PropertyBuilder {

    public static final String DEFAULT_NAME = "Paradise Park";
    public static final String DEFAULT_ADDRESS = "432, Jurong West Ave 6, #19-987";
    public static final String DEFAULT_PRICE = "571000";

    private Name name;
    private Address address;
    private PersonBuilder sellerBuilder;
    private Price price;
    private Set<Tag> tags;

    /**
     * Creates a {@code PropertyBuilder} with the default details.
     */
    public PropertyBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        sellerBuilder = new PersonBuilder();
        price = new Price(DEFAULT_PRICE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PropertyBuilder with the data of {@code propertyToCopy}.
     */
    public PropertyBuilder(Property propertyToCopy) {
        name = propertyToCopy.getName();
        address = propertyToCopy.getAddress();
        sellerBuilder = new PersonBuilder(propertyToCopy.getSeller());
        price = propertyToCopy.getPrice();
        tags = new HashSet<>(propertyToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Property} that we are building.
     */
    public PropertyBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Property} that we are building.
     */
    public PropertyBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Property} that we are building.
     */
    public PropertyBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Property} that we are building.
     */
    public PropertyBuilder withPhone(String phone) {
        sellerBuilder.withPhone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Property} that we are building.
     */
    public PropertyBuilder withEmail(String email) {
        sellerBuilder.withEmail(email);
        return this;
    }

    /**
     * Sets the {@code Seller} of the {@code Property} that we are building.
     */
    public PropertyBuilder withSeller(String seller) {
        sellerBuilder.withName(seller);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Property} that we are building.
     */
    public PropertyBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    public Property build() {
        return new Property(name, address, sellerBuilder.build(), price, tags);
    }

}
