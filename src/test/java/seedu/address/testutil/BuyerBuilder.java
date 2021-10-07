package seedu.address.testutil;

import seedu.address.model.field.Person;
import seedu.address.model.field.Price;
import seedu.address.model.property.Buyer;

public class BuyerBuilder extends PersonBuilder {

    public static final String DEFAULT_PRICE = "1230000";

    private Price maxPrice;
    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public BuyerBuilder() {
        super();
        maxPrice = new Price(DEFAULT_PRICE);
    }

    /**
     * Initializes the BuyerBuilder with the data of {@code personToCopy}.
     */
    public BuyerBuilder(Person personToCopy) {
        super(personToCopy);
        maxPrice = new Price(DEFAULT_PRICE);
    }

    /**
     * Initializes the BuyerBuilder with the data of {@code buyerToCopy}.
     */
    public BuyerBuilder(Buyer buyerToCopy) {
        super(buyerToCopy);
        maxPrice = buyerToCopy.getMaxPrice();
    }

    @Override
    public BuyerBuilder withName(String name) {
        super.withName(name);
        return this;
    }

    @Override
    public BuyerBuilder withPhone(String phone) {
        super.withPhone(phone);
        return this;
    }

    @Override
    public BuyerBuilder withEmail(String email) {
        super.withEmail(email);
        return this;
    }

    /**
     * Sets the maximum {@code Price} of the {@code Buyer} that we are building.
     */
    public BuyerBuilder withMaxPrice(String maxPrice) {
        this.maxPrice = new Price(maxPrice);
        return this;
    }

    @Override
    public Buyer build() {
        return new Buyer(super.build(), maxPrice);
    }

}
