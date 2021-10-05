package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SELLER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.P_ALICE;
import static seedu.address.testutil.TypicalProperties.P_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PropertyBuilder;

public class PropertyTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Property property = new PropertyBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> property.getTags().remove(0));
    }

    @Test
    public void isSameProperty() {
        // same object -> returns true
        assertTrue(P_ALICE.isSameProperty(P_ALICE));

        // null -> returns false
        assertFalse(P_ALICE.isSameProperty(null));

        // same name, all other attributes different -> returns true
        Property editedAlice = new PropertyBuilder(P_ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(P_ALICE.isSameProperty(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PropertyBuilder(P_ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(P_ALICE.isSameProperty(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Property editedBob = new PropertyBuilder(P_BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(P_BOB.isSameProperty(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PropertyBuilder(P_BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(P_BOB.isSameProperty(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Property P_ALICECopy = new PropertyBuilder(P_ALICE).build();
        assertTrue(P_ALICE.equals(P_ALICECopy));

        // same object -> returns true
        assertTrue(P_ALICE.equals(P_ALICE));

        // null -> returns false
        assertFalse(P_ALICE.equals(null));

        // different type -> returns false
        assertFalse(P_ALICE.equals(5));

        // different property -> returns false
        assertFalse(P_ALICE.equals(P_BOB));

        // different name -> returns false
        Property editedAlice = new PropertyBuilder(P_ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(P_ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PropertyBuilder(P_ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(P_ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PropertyBuilder(P_ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(P_ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PropertyBuilder(P_ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(P_ALICE.equals(editedAlice));

        // different seller -> returns false
        editedAlice = new PropertyBuilder(P_ALICE).withSeller(VALID_SELLER_BOB).build();
        assertFalse(P_ALICE.equals(editedAlice));

        // different price -> returns false
        editedAlice = new PropertyBuilder(P_ALICE).withPrice(VALID_PRICE_BOB).build();
        assertFalse(P_ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PropertyBuilder(P_ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(P_ALICE.equals(editedAlice));
    }
}
