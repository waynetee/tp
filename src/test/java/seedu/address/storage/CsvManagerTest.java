package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.getTypicalAddressBook;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

public class CsvManagerTest {
    @TempDir
    public Path testFolder;

    // ================ Properties tests ==============================

    @Test
    public void exportProperties_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportProperties((File) null, getTypicalAddressBook()));
    }

    @Test
    public void exportProperties_nullProperties_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportProperties("SomeFile.csv", null));
    }

    @Test
    public void exportProperties_success() {
        exportProperties(testFolder.resolve("TempProperties.csv"), getTypicalAddressBook());
    }

    private void exportProperties(File file, ReadOnlyAddressBook addressBook) {
        new CsvManager().exportProperties(file, addressBook.getPropertyList());
    }

    private void exportProperties(String filePath, ObservableList<Property> properties) {
        new CsvManager().exportProperties(new File(Paths.get(filePath).toString()), properties);
    }

    /**
     * Exports properties in {@code addressBook} at the specified {@code filePath}.
     */
    private void exportProperties(Path filePath, ReadOnlyAddressBook addressBook) {
        new CsvManager().exportProperties(new File(filePath.toString()), addressBook.getPropertyList());
    }

    // ================ Buyer tests ==============================

    @Test
    public void exportBuyers_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportBuyers((File) null, getTypicalAddressBook()));
    }

    @Test
    public void exportBuyers_nullBuyers_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exportBuyers("SomeFile.csv", null));
    }

    @Test
    public void exportBuyers_success() {
        exportBuyers(testFolder.resolve("TempBuyers.csv"), getTypicalAddressBook());
    }

    private void exportBuyers(File file, ReadOnlyAddressBook addressBook) {
        new CsvManager().exportBuyers(file, addressBook.getBuyerList());
    }

    private void exportBuyers(String filePath, ObservableList<Buyer> buyers) {
        new CsvManager().exportBuyers(new File(Paths.get(filePath).toString()), buyers);
    }

    /**
     * Exports buyers in {@code addressBook} at the specified {@code filePath}.
     */
    private void exportBuyers(Path filePath, ReadOnlyAddressBook addressBook) {
        new CsvManager().exportBuyers(new File(filePath.toString()), addressBook.getBuyerList());
    }
}
