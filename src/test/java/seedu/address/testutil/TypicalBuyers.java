package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Buyer} objects to be used in tests.
 */
public class TypicalBuyers {

    // Uses TypicalPersons to build TypicalBuyers
    public static final Buyer B_ALICE = new BuyerBuilder(TypicalPersons.ALICE)
            .withMaxPrice("123000").build();
    public static final Buyer B_BENSON = new BuyerBuilder(TypicalPersons.BENSON)
            .withMaxPrice("1231000").build();
    public static final Buyer B_CARL = new BuyerBuilder(TypicalPersons.CARL)
            .withMaxPrice("678000").build();
    public static final Buyer B_DANIEL = new BuyerBuilder(TypicalPersons.DANIEL)
            .withMaxPrice("102200").build();
    public static final Buyer B_ELLE = new BuyerBuilder(TypicalPersons.ELLE)
            .withMaxPrice("999000").build();
    public static final Buyer B_FIONA = new BuyerBuilder(TypicalPersons.FIONA)
            .withMaxPrice("999999").build();
    public static final Buyer B_GEORGE = new BuyerBuilder(TypicalPersons.GEORGE)
            .withMaxPrice("999999").build();

    // Manually added

    // Manually added - Buyer's details found in {@code CommandTestUtil}
    public static final Buyer B_AMY = new BuyerBuilder(
            new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build())
            .withMaxPrice("999999").build();
    public static final Buyer B_BOB = new BuyerBuilder(
            new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build())
            .withMaxPrice("111111").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBuyers() {
    } // prevents instantiation

    public static List<Buyer> getTypicalBuyers() {
        return new ArrayList<>(Arrays.asList(B_ALICE, B_BENSON, B_CARL, B_DANIEL, B_ELLE, B_FIONA));
    }

    public static List<Buyer> getTypicalBuyersSortedNameAsc() {
        return new ArrayList<>(Arrays.asList(B_ALICE, B_BENSON, B_CARL, B_DANIEL, B_ELLE, B_FIONA, B_GEORGE));
    }

    public static List<Buyer> getTypicalBuyersSortedNameDesc() {
        return new ArrayList<>(Arrays.asList(B_GEORGE, B_FIONA, B_ELLE, B_DANIEL, B_CARL, B_BENSON, B_ALICE));
    }

    public static List<Buyer> getTypicalBuyersSortedPriceAsc() {
        return new ArrayList<>(Arrays.asList(B_DANIEL, B_ALICE, B_CARL, B_ELLE, B_FIONA, B_GEORGE, B_BENSON));
    }

    public static List<Buyer> getTypicalBuyersSortedPriceDesc() {
        return new ArrayList<>(Arrays.asList(B_BENSON, B_FIONA, B_GEORGE, B_ELLE, B_CARL, B_ALICE, B_DANIEL));
    }
}
