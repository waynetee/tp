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
    public static final Buyer B_ALICE = new BuyerBuilder(TypicalPersons.ALICE)
            .withPrice("123000").build();
    public static final Buyer B_BENSON = new BuyerBuilder(TypicalPersons.BENSON)
            .withPrice("1231000").build();
    public static final Buyer B_CARL = new BuyerBuilder(TypicalPersons.CARL)
            .withPrice("678000").build();
    public static final Buyer B_DANIEL = new BuyerBuilder(TypicalPersons.DANIEL)
            .withPrice("102200").build();
    public static final Buyer B_ELLE = new BuyerBuilder(TypicalPersons.ELLE)
            .withPrice("999000").build();
    public static final Buyer B_FIONA = new BuyerBuilder(TypicalPersons.FIONA)
            .withPrice("999999").build();

    // Manually added

    // Manually added - Buyer's details found in {@code CommandTestUtil}
    public static final Buyer B_AMY = new BuyerBuilder(
            new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build())
            .withPrice("999999").build();
    public static final Buyer B_BOB = new BuyerBuilder(
            new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build())
            .withPrice("111111").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBuyers() {
    } // prevents instantiation

}
