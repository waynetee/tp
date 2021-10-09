package seedu.address.model.property;

import org.junit.jupiter.api.Test;
import seedu.address.model.property.exceptions.DuplicateBuyerException;
import seedu.address.model.property.exceptions.BuyerNotFoundException;
import seedu.address.testutil.BuyerBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBuyers.B_ALICE;
import static seedu.address.testutil.TypicalBuyers.B_BOB;

public class UniqueBuyerListTest {

    private final UniqueBuyerList uniqueBuyerList = new UniqueBuyerList();

    @Test
    public void contains_nullBuyer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBuyerList.contains(null));
    }

    @Test
    public void contains_propertyNotInList_returnsFalse() {
        assertFalse(uniqueBuyerList.contains(B_ALICE));
    }

    @Test
    public void contains_propertyInList_returnsTrue() {
        uniqueBuyerList.add(B_ALICE);
        assertTrue(uniqueBuyerList.contains(B_ALICE));
    }

    @Test
    public void contains_propertyWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBuyerList.add(B_ALICE);
        Buyer editedAlice = new BuyerBuilder(B_ALICE).withMaxPrice("500000").build();
        assertTrue(uniqueBuyerList.contains(editedAlice));
    }

    @Test
    public void add_nullBuyer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBuyerList.add(null));
    }

    @Test
    public void add_duplicateBuyer_throwsDuplicateBuyerException() {
        uniqueBuyerList.add(B_ALICE);
        assertThrows(DuplicateBuyerException.class, () -> uniqueBuyerList.add(B_ALICE));
    }

    @Test
    public void setBuyer_nullTargetBuyer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBuyerList.setBuyer(null, B_ALICE));
    }

    @Test
    public void setBuyer_nullEditedBuyer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBuyerList.setBuyer(B_ALICE, null));
    }

    @Test
    public void setBuyer_targetBuyerNotInList_throwsBuyerNotFoundException() {
        assertThrows(BuyerNotFoundException.class, () -> uniqueBuyerList.setBuyer(B_ALICE, B_ALICE));
    }

    @Test
    public void setBuyer_editedBuyerIsSameBuyer_success() {
        uniqueBuyerList.add(B_ALICE);
        uniqueBuyerList.setBuyer(B_ALICE, B_ALICE);
        UniqueBuyerList expectedUniqueBuyerList = new UniqueBuyerList();
        expectedUniqueBuyerList.add(B_ALICE);
        assertEquals(expectedUniqueBuyerList, uniqueBuyerList);
    }

    @Test
    public void setBuyer_editedBuyerHasSameIdentity_success() {
        uniqueBuyerList.add(B_ALICE);
        Buyer editedAlice = new BuyerBuilder(B_ALICE).withMaxPrice("500000")
                .build();
        uniqueBuyerList.setBuyer(B_ALICE, editedAlice);
        UniqueBuyerList expectedUniqueBuyerList = new UniqueBuyerList();
        expectedUniqueBuyerList.add(editedAlice);
        assertEquals(expectedUniqueBuyerList, uniqueBuyerList);
    }

    @Test
    public void setBuyer_editedBuyerHasDifferentIdentity_success() {
        uniqueBuyerList.add(B_ALICE);
        uniqueBuyerList.setBuyer(B_ALICE, B_BOB);
        UniqueBuyerList expectedUniqueBuyerList = new UniqueBuyerList();
        expectedUniqueBuyerList.add(B_BOB);
        assertEquals(expectedUniqueBuyerList, uniqueBuyerList);
    }

    @Test
    public void setBuyer_editedBuyerHasNonUniqueIdentity_throwsDuplicateBuyerException() {
        uniqueBuyerList.add(B_ALICE);
        uniqueBuyerList.add(B_BOB);
        assertThrows(DuplicateBuyerException.class, () -> uniqueBuyerList.setBuyer(B_ALICE, B_BOB));
    }

    @Test
    public void remove_nullBuyer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBuyerList.remove(null));
    }

    @Test
    public void remove_propertyDoesNotExist_throwsBuyerNotFoundException() {
        assertThrows(BuyerNotFoundException.class, () -> uniqueBuyerList.remove(B_ALICE));
    }

    @Test
    public void remove_existingBuyer_removesBuyer() {
        uniqueBuyerList.add(B_ALICE);
        uniqueBuyerList.remove(B_ALICE);
        UniqueBuyerList expectedUniqueBuyerList = new UniqueBuyerList();
        assertEquals(expectedUniqueBuyerList, uniqueBuyerList);
    }

    @Test
    public void setBuyers_nullUniqueBuyerList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBuyerList.setBuyers((UniqueBuyerList) null));
    }

    @Test
    public void setBuyers_uniqueBuyerList_replacesOwnListWithProvidedUniqueBuyerList() {
        uniqueBuyerList.add(B_ALICE);
        UniqueBuyerList expectedUniqueBuyerList = new UniqueBuyerList();
        expectedUniqueBuyerList.add(B_BOB);
        uniqueBuyerList.setBuyers(expectedUniqueBuyerList);
        assertEquals(expectedUniqueBuyerList, uniqueBuyerList);
    }

    @Test
    public void setBuyers_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBuyerList.setBuyers((List<Buyer>) null));
    }

    @Test
    public void setBuyers_list_replacesOwnListWithProvidedList() {
        uniqueBuyerList.add(B_ALICE);
        List<Buyer> propertyList = Collections.singletonList(B_BOB);
        uniqueBuyerList.setBuyers(propertyList);
        UniqueBuyerList expectedUniqueBuyerList = new UniqueBuyerList();
        expectedUniqueBuyerList.add(B_BOB);
        assertEquals(expectedUniqueBuyerList, uniqueBuyerList);
    }

    @Test
    public void setBuyers_listWithDuplicateBuyers_throwsDuplicateBuyerException() {
        List<Buyer> listWithDuplicateBuyers = Arrays.asList(B_ALICE, B_ALICE);
        assertThrows(DuplicateBuyerException.class, () -> uniqueBuyerList
                .setBuyers(listWithDuplicateBuyers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueBuyerList.asUnmodifiableObservableList().remove(0));
    }
}
