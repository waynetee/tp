package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.P_ALICE;
import static seedu.address.testutil.TypicalProperties.P_BOB;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertiesSortedNameAsc;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertiesSortedNameDesc;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertiesSortedPriceAsc;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertiesSortedPriceDesc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.field.SortDirection;
import seedu.address.model.field.SortType;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.testutil.PropertyBuilder;

public class UniquePropertyListTest {

    private final UniquePropertyList uniquePropertyList = new UniquePropertyList();

    @Test
    public void contains_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.contains(null));
    }

    @Test
    public void contains_propertyNotInList_returnsFalse() {
        assertFalse(uniquePropertyList.contains(P_ALICE));
    }

    @Test
    public void contains_propertyInList_returnsTrue() {
        uniquePropertyList.add(P_ALICE);
        assertTrue(uniquePropertyList.contains(P_ALICE));
    }

    @Test
    public void contains_propertyWithSameIdentityFieldsInList_returnsTrue() {
        uniquePropertyList.add(P_BOB);
        Property editedAlice = new PropertyBuilder(P_ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePropertyList.contains(editedAlice));
    }

    @Test
    public void add_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.add(null));
    }

    @Test
    public void add_duplicateProperty_throwsDuplicatePropertyException() {
        uniquePropertyList.add(P_ALICE);
        assertThrows(DuplicatePropertyException.class, () -> uniquePropertyList.add(P_ALICE));
    }

    @Test
    public void setProperty_nullTargetProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperty(null, P_ALICE));
    }

    @Test
    public void setProperty_nullEditedProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperty(P_ALICE, null));
    }

    @Test
    public void setProperty_targetPropertyNotInList_throwsPropertyNotFoundException() {
        assertThrows(PropertyNotFoundException.class, () -> uniquePropertyList.setProperty(P_ALICE, P_ALICE));
    }

    @Test
    public void setProperty_editedPropertyIsSameProperty_success() {
        uniquePropertyList.add(P_ALICE);
        uniquePropertyList.setProperty(P_ALICE, P_ALICE);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.add(P_ALICE);
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setProperty_editedPropertyHasSameIdentity_success() {
        uniquePropertyList.add(P_ALICE);
        Property editedAlice = new PropertyBuilder(P_ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePropertyList.setProperty(P_ALICE, editedAlice);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.add(editedAlice);
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setProperty_editedPropertyHasDifferentIdentity_success() {
        uniquePropertyList.add(P_ALICE);
        uniquePropertyList.setProperty(P_ALICE, P_BOB);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.add(P_BOB);
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setProperty_editedPropertyHasNonUniqueIdentity_throwsDuplicatePropertyException() {
        uniquePropertyList.add(P_ALICE);
        uniquePropertyList.add(P_BOB);
        assertThrows(DuplicatePropertyException.class, () -> uniquePropertyList.setProperty(P_ALICE, P_BOB));
    }

    @Test
    public void remove_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.remove(null));
    }

    @Test
    public void remove_propertyDoesNotExist_throwsPropertyNotFoundException() {
        assertThrows(PropertyNotFoundException.class, () -> uniquePropertyList.remove(P_ALICE));
    }

    @Test
    public void remove_existingProperty_removesProperty() {
        uniquePropertyList.add(P_ALICE);
        uniquePropertyList.remove(P_ALICE);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setProperties_nullUniquePropertyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperties((UniquePropertyList) null));
    }

    @Test
    public void setProperties_uniquePropertyList_replacesOwnListWithProvidedUniquePropertyList() {
        uniquePropertyList.add(P_ALICE);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.add(P_BOB);
        uniquePropertyList.setProperties(expectedUniquePropertyList);
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setProperties_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperties((List<Property>) null));
    }

    @Test
    public void setProperties_list_replacesOwnListWithProvidedList() {
        uniquePropertyList.add(P_ALICE);
        List<Property> propertyList = Collections.singletonList(P_BOB);
        uniquePropertyList.setProperties(propertyList);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.add(P_BOB);
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setProperties_listWithDuplicateProperties_throwsDuplicatePropertyException() {
        List<Property> listWithDuplicateProperties = Arrays.asList(P_ALICE, P_ALICE);
        assertThrows(DuplicatePropertyException.class, () -> uniquePropertyList
                .setProperties(listWithDuplicateProperties));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePropertyList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void sort_sortListByPriceInAscOrder() {
        uniquePropertyList.setProperties(getTypicalPropertiesSortedPriceDesc());
        uniquePropertyList.sort(SortType.PRICE, SortDirection.ASC);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.setProperties(getTypicalPropertiesSortedPriceAsc());
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void sort_sortListByPriceInDescOrder() {
        uniquePropertyList.setProperties(getTypicalPropertiesSortedPriceAsc());
        uniquePropertyList.sort(SortType.PRICE, SortDirection.DESC);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.setProperties(getTypicalPropertiesSortedPriceDesc());
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void sort_sortListByNameInAscOrder() {
        uniquePropertyList.setProperties(getTypicalPropertiesSortedNameDesc());
        uniquePropertyList.sort(SortType.NAME, SortDirection.ASC);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.setProperties(getTypicalPropertiesSortedNameAsc());
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void sort_sortListByNameInDescOrder() {
        uniquePropertyList.setProperties(getTypicalPropertiesSortedNameAsc());
        uniquePropertyList.sort(SortType.NAME, SortDirection.DESC);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.setProperties(getTypicalPropertiesSortedNameDesc());
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }
}
