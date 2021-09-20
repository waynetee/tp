package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.ALICE;
import static seedu.address.testutil.TypicalProperties.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.property.exceptions.DuplicatePersonException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.testutil.PropertyBuilder;

public class UniquePropertyListTest {

    private final UniquePropertyList uniquePropertyList = new UniquePropertyList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePropertyList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePropertyList.add(ALICE);
        assertTrue(uniquePropertyList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePropertyList.add(ALICE);
        Property editedAlice = new PropertyBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePropertyList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePropertyList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePropertyList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperty(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperty(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PropertyNotFoundException.class, () -> uniquePropertyList.setProperty(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePropertyList.add(ALICE);
        uniquePropertyList.setProperty(ALICE, ALICE);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.add(ALICE);
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePropertyList.add(ALICE);
        Property editedAlice = new PropertyBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePropertyList.setProperty(ALICE, editedAlice);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.add(editedAlice);
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePropertyList.add(ALICE);
        uniquePropertyList.setProperty(ALICE, BOB);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.add(BOB);
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePropertyList.add(ALICE);
        uniquePropertyList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePropertyList.setProperty(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PropertyNotFoundException.class, () -> uniquePropertyList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePropertyList.add(ALICE);
        uniquePropertyList.remove(ALICE);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperties((UniquePropertyList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePropertyList.add(ALICE);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.add(BOB);
        uniquePropertyList.setProperties(expectedUniquePropertyList);
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperties((List<Property>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePropertyList.add(ALICE);
        List<Property> propertyList = Collections.singletonList(BOB);
        uniquePropertyList.setProperties(propertyList);
        UniquePropertyList expectedUniquePropertyList = new UniquePropertyList();
        expectedUniquePropertyList.add(BOB);
        assertEquals(expectedUniquePropertyList, uniquePropertyList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Property> listWithDuplicateProperties = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePropertyList.setProperties(listWithDuplicateProperties));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePropertyList.asUnmodifiableObservableList().remove(0));
    }
}
