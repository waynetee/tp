package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPrices.LARGE_PRICE;
import static seedu.address.testutil.TypicalPrices.LUDICROUS_PRICE;
import static seedu.address.testutil.TypicalPrices.MEDIUM_PRICE;
import static seedu.address.testutil.TypicalPrices.MICROSCOPIC_PRICE;
import static seedu.address.testutil.TypicalPrices.SMALL_PRICE;
import static seedu.address.testutil.TypicalPrices.VERY_LARGE_PRICE;
import static seedu.address.testutil.TypicalPrices.VERY_SMALL_PRICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.property.Buyer;

/**
 * A utility class containing a list of {@code Buyer} objects to be used in tests.
 */
public class TypicalBuyers {

    // Uses TypicalPersons to build TypicalBuyers
    // Price matches with P_ALICE only
    public static final Buyer B_ALICE = new BuyerBuilder(TypicalPersons.ALICE)
            .withMaxPrice(MICROSCOPIC_PRICE)
            .build();
    // Price matches with P_ALICE, P_BENSON
    public static final Buyer B_BENSON = new BuyerBuilder(TypicalPersons.BENSON)
            .withMaxPrice(VERY_SMALL_PRICE)
            .withTags("condo", "HDB")
            .build();
    // Price matches with P_ALICE, P_BENSON, P_CARL
    public static final Buyer B_CARL = new BuyerBuilder(TypicalPersons.CARL)
            .withMaxPrice(SMALL_PRICE)
            .build();
    // Price matches with P_ALICE to P_DANIEL
    public static final Buyer B_DANIEL = new BuyerBuilder(TypicalPersons.DANIEL)
            .withMaxPrice(MEDIUM_PRICE)
            .build();
    // Price matches with P_ALICE to P_ELLE
    public static final Buyer B_ELLE = new BuyerBuilder(TypicalPersons.ELLE)
            .withMaxPrice(LARGE_PRICE)
            .build();
    // Price matches with P_ALICE to P_FIONA
    public static final Buyer B_FIONA = new BuyerBuilder(TypicalPersons.FIONA)
            .withMaxPrice(VERY_LARGE_PRICE)
            .build();
    // Price matches with P_ALICE to P_GEORGE
    public static final Buyer B_GEORGE = new BuyerBuilder(TypicalPersons.GEORGE)
            .withMaxPrice(LUDICROUS_PRICE)
            .build();

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
        return new ArrayList<>(Arrays.asList(B_ALICE, B_BENSON, B_CARL, B_DANIEL, B_ELLE, B_FIONA, B_GEORGE));
    }

    public static List<Buyer> getTypicalBuyersSortedNameAsc() {
        return new ArrayList<>(Arrays.asList(B_ALICE, B_BENSON, B_CARL, B_DANIEL, B_ELLE, B_FIONA, B_GEORGE));
    }

    public static List<Buyer> getTypicalBuyersSortedNameDesc() {
        return new ArrayList<>(Arrays.asList(B_GEORGE, B_FIONA, B_ELLE, B_DANIEL, B_CARL, B_BENSON, B_ALICE));
    }

    public static List<Buyer> getTypicalBuyersSortedPriceAsc() {
        return new ArrayList<>(Arrays.asList(B_ALICE, B_BENSON, B_CARL, B_DANIEL, B_ELLE, B_FIONA, B_GEORGE));
    }

    public static List<Buyer> getTypicalBuyersSortedPriceDesc() {
        return new ArrayList<>(Arrays.asList(B_GEORGE, B_FIONA, B_ELLE, B_DANIEL, B_CARL, B_BENSON, B_ALICE));
    }
}
