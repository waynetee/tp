package seedu.address.testutil;

import seedu.address.model.field.Person;
import seedu.address.model.field.Price;
import seedu.address.model.property.Buyer;

public class BuyerBuilder extends PersonBuilder {

    public static final String DEFAULT_PRICE = "1230000";

    private Price max;
    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public BuyerBuilder() {
        super();
        max = new Price(DEFAULT_PRICE);
    }

    /**
     * Initializes the BuyerBuilder with the data of {@code personToCopy}.
     */
    public BuyerBuilder(Person personToCopy) {
        super(personToCopy);
        max = new Price(DEFAULT_PRICE);
    }

    /**
     * Initializes the BuyerBuilder with the data of {@code buyerToCopy}.
     */
    public BuyerBuilder(Buyer buyerToCopy) {
        super(buyerToCopy);
        max = buyerToCopy.getPrice();
    }

    /**
     * Sets the maximum {@code Price} of the {@code Buyer} that we are building.
     */
    public BuyerBuilder withPrice(String max) {
        this.max = new Price(max);
        return this;
    }

    /**
     * Sets the {@code Person} of the {@code Buyer} that we are building.
     */
    public BuyerBuilder withPerson(Person person) {
        super.withName(person.getName().toString());
        return this;
    }

    @Override
    public Buyer build() {
        return new Buyer(getName(), getPhone(), getEmail(), max);
    }

}
