package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBuyers.B_ALICE;
import static seedu.address.testutil.TypicalBuyers.B_BOB;
import static seedu.address.testutil.TypicalBuyers.getTypicalBuyersSortedNameAsc;
import static seedu.address.testutil.TypicalBuyers.getTypicalBuyersSortedNameDesc;
import static seedu.address.testutil.TypicalBuyers.getTypicalBuyersSortedPriceAsc;
import static seedu.address.testutil.TypicalBuyers.getTypicalBuyersSortedPriceDesc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;
import seedu.address.model.property.exceptions.BuyerNotFoundException;
import seedu.address.model.property.exceptions.DuplicateBuyerException;
import seedu.address.testutil.BuyerBuilder;

public class UniqueBuyerListTest {

    private final UniqueBuyerList uniqueBuyerList = new UniqueBuyerList();

    @Test
    public void contains_nullBuyer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBuyerList.contains(null));
    }

    @Test
    public void contains_buyerNotInList_returnsFalse() {
        assertFalse(uniqueBuyerList.contains(B_ALICE));
    }

    @Test
    public void contains_buyerInList_returnsTrue() {
        uniqueBuyerList.add(B_ALICE);
        assertTrue(uniqueBuyerList.contains(B_ALICE));
    }

    @Test
    public void contains_buyerWithSameIdentityFieldsInList_returnsTrue() {
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
    public void remove_buyerDoesNotExist_throwsBuyerNotFoundException() {
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

    @Test
    public void sort_sortListByPriceInAscOrder() {
        uniqueBuyerList.setBuyers(getTypicalBuyersSortedPriceDesc());
        uniqueBuyerList.sort(SortType.PRICE, SortDirection.ASC);
        UniqueBuyerList expectedUniqueBuyerList = new UniqueBuyerList();
        expectedUniqueBuyerList.setBuyers(getTypicalBuyersSortedPriceAsc());
        assertEquals(expectedUniqueBuyerList, uniqueBuyerList);
    }

    @Test
    public void sort_sortListByPriceInDescOrder() {
        uniqueBuyerList.setBuyers(getTypicalBuyersSortedPriceAsc());
        uniqueBuyerList.sort(SortType.PRICE, SortDirection.DESC);
        UniqueBuyerList expectedUniqueBuyerList = new UniqueBuyerList();
        expectedUniqueBuyerList.setBuyers(getTypicalBuyersSortedPriceDesc());
        assertEquals(expectedUniqueBuyerList, uniqueBuyerList);
    }

    @Test
    public void sort_sortListByNameInAscOrder() {
        uniqueBuyerList.setBuyers(getTypicalBuyersSortedNameDesc());
        uniqueBuyerList.sort(SortType.NAME, SortDirection.ASC);
        UniqueBuyerList expectedUniqueBuyerList = new UniqueBuyerList();
        expectedUniqueBuyerList.setBuyers(getTypicalBuyersSortedNameAsc());
        assertEquals(expectedUniqueBuyerList, uniqueBuyerList);
    }

    @Test
    public void sort_sortListByNameInDescOrder() {
        uniqueBuyerList.setBuyers(getTypicalBuyersSortedNameAsc());
        uniqueBuyerList.sort(SortType.NAME, SortDirection.DESC);
        UniqueBuyerList expectedUniqueBuyerList = new UniqueBuyerList();
        expectedUniqueBuyerList.setBuyers(getTypicalBuyersSortedNameDesc());
        assertEquals(expectedUniqueBuyerList, uniqueBuyerList);
    }
}
