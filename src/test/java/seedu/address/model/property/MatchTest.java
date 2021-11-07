package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalBuyers.B_ALICE;
import static seedu.address.testutil.TypicalMatches.M_P_ALICE_B_ALICE;
import static seedu.address.testutil.TypicalMatches.M_P_ALICE_B_BENSON;
import static seedu.address.testutil.TypicalPrices.LUDICROUS_PRICE;
import static seedu.address.testutil.TypicalProperties.P_ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BuyerBuilder;
import seedu.address.testutil.PropertyBuilder;

public class MatchTest {
    @Test
    public void isSameMatch() {
        // same object -> returns true
        assertTrue(M_P_ALICE_B_ALICE.isSameMatch(M_P_ALICE_B_ALICE));

        // null -> returns false
        assertFalse(M_P_ALICE_B_ALICE.isSameMatch(null));

        // same property address, all other attributes different -> returns true
        Property editedAliceProperty = new PropertyBuilder(P_ALICE).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        Match editedMatch = new Match(editedAliceProperty,
                new BuyerBuilder(M_P_ALICE_B_ALICE.getBuyer()).build());
        assertTrue(M_P_ALICE_B_ALICE.isSameMatch(editedMatch));

        // same buyer name, all other attributes different -> returns true
        Buyer editedAliceBuyer = new BuyerBuilder(B_ALICE).withMaxPrice(LUDICROUS_PRICE).withTags(VALID_TAG_HUSBAND)
                .build();
        editedMatch = new Match(new PropertyBuilder(M_P_ALICE_B_ALICE.getProperty()).build(), editedAliceBuyer);
        assertTrue(M_P_ALICE_B_ALICE.isSameMatch(editedMatch));

        // different property address, all other attributes same -> returns false
        editedAliceProperty = new PropertyBuilder(P_ALICE).withAddress(VALID_ADDRESS_BOB).build();
        editedMatch = new Match(editedAliceProperty, new BuyerBuilder(M_P_ALICE_B_ALICE.getBuyer()).build());
        assertFalse(M_P_ALICE_B_ALICE.isSameMatch(editedMatch));

        // different buyer name, all other attributes same -> returns false
        editedAliceBuyer = new BuyerBuilder(B_ALICE).withName(VALID_NAME_BOB).build();
        editedMatch = new Match(new PropertyBuilder(M_P_ALICE_B_ALICE.getProperty()).build(), editedAliceBuyer);
        assertFalse(M_P_ALICE_B_ALICE.isSameMatch(editedMatch));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(M_P_ALICE_B_ALICE.equals(M_P_ALICE_B_ALICE));

        // null -> returns false
        assertFalse(M_P_ALICE_B_ALICE.equals(null));

        // different type -> returns false
        assertFalse(M_P_ALICE_B_ALICE.equals(5));

        // different property -> returns false
        assertFalse(M_P_ALICE_B_ALICE.equals(M_P_ALICE_B_BENSON));
    }
}
