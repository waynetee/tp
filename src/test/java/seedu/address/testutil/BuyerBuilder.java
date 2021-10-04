package seedu.address.testutil;

import seedu.address.model.field.Person;
import seedu.address.model.field.Price;
import seedu.address.model.property.Buyer;

public class BuyerBuilder {

    public static final Person DEFAULT_PERSON = TypicalPersons.ALICE;
    public static final String DEFAULT_PRICE = "1230000";

    private Person person;
    private Price max;
    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public BuyerBuilder() {
        person = DEFAULT_PERSON;
        max = new Price(DEFAULT_PRICE);
    }

    /**
     * Initializes the BuyerBuilder with the data of {@code buyerToCopy}.
     */
    public BuyerBuilder(Buyer buyerToCopy) {
        person = buyerToCopy.getPerson();
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
        this.person = person;
        return this;
    }

    public Buyer build() {
        return new Buyer(person, max);
    }

}
