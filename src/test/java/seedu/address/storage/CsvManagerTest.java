package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

public class CsvManagerTest {
    @TempDir
    public Path testFolder;

    // ================ Properties tests ==============================

    @Test
    public void exportProperties_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportProperties(getTypicalAddressBook(), (File) null));
    }

    @Test
    public void exportProperties_nullProperties_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportProperties((List<Property>) null, "SomeFile.csv"));
    }

    @Test
    public void exportProperties_success() {
        exportProperties(getTypicalAddressBook(), "TempProperties.csv");
    }

    private void exportProperties(List<Property> properties, File file) {
        try {
            CsvManager.exportProperties(properties, file);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    private void exportProperties(List<Property> properties, String filePath) {
        exportProperties(properties, testFolder.resolve(filePath).toFile());
    }

    private void exportProperties(ReadOnlyAddressBook addressBook, File file) {
        exportProperties(addressBook.getPropertyList(), file);
    }

    /**
     * Exports properties in {@code addressBook} at the specified {@code filePath}.
     */
    private void exportProperties(ReadOnlyAddressBook addressBook, String filePath) {
        exportProperties(addressBook.getPropertyList(), testFolder.resolve(filePath).toFile());
    }

    // ================ Buyer tests ==============================

    @Test
    public void exportBuyers_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportBuyers(getTypicalAddressBook(), (File) null));
    }

    @Test
    public void exportBuyers_nullBuyers_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportBuyers((List<Buyer>) null, "SomeFile.csv"));
    }

    @Test
    public void exportBuyers_success() {
        exportBuyers(getTypicalAddressBook(), "TempBuyers.csv");
    }

    private void exportBuyers(List<Buyer> buyers, File file) {
        try {
            CsvManager.exportBuyers(buyers, file);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    private void exportBuyers(List<Buyer> buyers, String filePath) {
        exportBuyers(buyers, testFolder.resolve(filePath).toFile());
    }

    private void exportBuyers(ReadOnlyAddressBook addressBook, File file) {
        exportBuyers(addressBook.getBuyerList(), file);
    }

    /**
     * Exports buyers in {@code addressBook} at the specified {@code filePath}.
     */
    private void exportBuyers(ReadOnlyAddressBook addressBook, String filePath) {
        exportBuyers(addressBook.getBuyerList(), testFolder.resolve(filePath).toFile());
    }
}
