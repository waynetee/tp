package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalBuyers.B_ALICE;
import static seedu.address.testutil.TypicalBuyers.B_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BuyerBuilder;

public class BuyerTest {
    private static final String DIFFERENT_VALID_MAX_PRICE = "1000";

    @Test
    public void isSameBuyer() {
        // same person -> returns true
        assertTrue(B_ALICE.isSameBuyer(B_ALICE));

        // null -> returns false
        assertFalse(B_ALICE.isSameBuyer(null));

        // different price, all other attributes same -> returns true
        Buyer editedAlice = new BuyerBuilder(B_ALICE)
                .withMaxPrice(DIFFERENT_VALID_MAX_PRICE)
                .build();
        assertTrue(B_ALICE.isSameBuyer(editedAlice));

        // different tag, all other attributes same -> returns true
        editedAlice = new BuyerBuilder(B_ALICE)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(B_ALICE.isSameBuyer(editedAlice));

        // same name, all different attributes -> returns true
        editedAlice = new BuyerBuilder(B_ALICE)
                .withMaxPrice(DIFFERENT_VALID_MAX_PRICE)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(B_ALICE.isSameBuyer(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new BuyerBuilder(B_ALICE)
                .withName(VALID_NAME_BOB)
                .build();
        assertFalse(B_ALICE.isSameBuyer(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Buyer editedBob = new BuyerBuilder(B_BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(B_BOB.isSameBuyer(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new BuyerBuilder(B_BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(B_BOB.isSameBuyer(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Buyer aliceCopy = new BuyerBuilder(B_ALICE).build();
        assertTrue(B_ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(B_ALICE.equals(B_ALICE));

        // null -> returns false
        assertFalse(B_ALICE.equals(null));

        // different type -> returns false
        assertFalse(B_ALICE.equals(5));

        // different person -> returns false
        assertFalse(B_ALICE.equals(B_BOB));

        // different name -> returns false
        Buyer editedAlice = new BuyerBuilder(B_ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(B_ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new BuyerBuilder(B_ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(B_ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new BuyerBuilder(B_ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(B_ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new BuyerBuilder(B_ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(B_ALICE.equals(editedAlice));
    }

}
