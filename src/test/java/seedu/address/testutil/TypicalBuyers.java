package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import seedu.address.model.property.Buyer;

/**
 * A utility class containing a list of {@code Buyer} objects to be used in tests.
 */
public class TypicalBuyers {

    // Uses TypicalPersons to build TypicalBuyers
    public static final Buyer ALICE = new BuyerBuilder().withPerson(seedu.address.testutil.TypicalPersons.ALICE)
            .withPrice("123000").build();
    public static final Buyer BENSON = new BuyerBuilder().withPerson(seedu.address.testutil.TypicalPersons.BENSON)
            .withPrice("1231000").build();
    public static final Buyer CARL = new BuyerBuilder().withPerson(seedu.address.testutil.TypicalPersons.CARL)
            .withPrice("678000").build();
    public static final Buyer DANIEL = new BuyerBuilder().withPerson(seedu.address.testutil.TypicalPersons.DANIEL)
            .withPrice("102200").build();
    public static final Buyer ELLE = new BuyerBuilder().withPerson(seedu.address.testutil.TypicalPersons.ELLE)
            .withPrice("999000").build();
    public static final Buyer FIONA = new BuyerBuilder().withPerson(seedu.address.testutil.TypicalPersons.FIONA)
            .withPrice("999999").build();

    // Manually added

    // Manually added - Buyer's details found in {@code CommandTestUtil}
    public static final Buyer AMY = new BuyerBuilder().withPerson(
            new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build())
            .withPrice("999999").build();
    public static final Buyer BOB = new BuyerBuilder().withPerson(
            new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build())
            .withPrice("111111").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBuyers() {
    } // prevents instantiation

}
