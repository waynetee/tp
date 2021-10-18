package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    // ================ Properties tests ==============================

    @Test
    public void exportProperties_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportProperties(getTypicalAddressBook(), (File) null));
    }

    @Test
    public void exportProperties_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportProperties(null, "SomeFile.csv"));
    }

    @Test
    public void exportProperties_success() {
        exportProperties(getTypicalAddressBook(), "TempProperties.csv");
    }

    private void exportProperties(ReadOnlyAddressBook addressBook, File file) {
        try {
            Storage.exportProperties(addressBook, file);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    /**
     * Exports properties in {@code addressBook} at the specified {@code filePath}.
     */
    private void exportProperties(ReadOnlyAddressBook addressBook, String filePath) {
        exportProperties(addressBook, testFolder.resolve(filePath).toFile());
    }

    // ================ Buyer tests ==============================

    @Test
    public void exportBuyers_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportBuyers(getTypicalAddressBook(), (File) null));
    }

    @Test
    public void exportBuyers_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportBuyers(null, "SomeFile.csv"));
    }

    @Test
    public void exportBuyers_success() {
        exportBuyers(getTypicalAddressBook(), "TempBuyers.csv");
    }

    private void exportBuyers(ReadOnlyAddressBook addressBook, File file) {
        try {
            Storage.exportBuyers(addressBook, file);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    /**
     * Exports buyers in {@code addressBook} at the specified {@code filePath}.
     */
    private void exportBuyers(ReadOnlyAddressBook addressBook, String filePath) {
        exportBuyers(addressBook, testFolder.resolve(filePath).toFile());
    }

}
