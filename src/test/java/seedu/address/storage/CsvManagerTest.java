package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.field.Email;
import seedu.address.model.field.Price;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

public class CsvManagerTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvManagerTest");

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

    @Test
    public void importProperties_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> importProperties((File) null));
    }

    @Test
    public void importProperties_success() {
        try {
            List<Property> expectedProperties = getTypicalAddressBook().getPropertyList();
            List<Property> properties = importProperties("validProperties.csv");
            assertEquals(properties, expectedProperties);
        } catch (ParseException pe) {
            throw new AssertionError("Import should not fail.", pe);
        }
    }

    @Test
    public void importProperties_invalidFieldFormat_throwsParseException() {
        assertThrows(ParseException.class,
                Price.MESSAGE_CONSTRAINTS, () -> importProperties("invalidPropertiesPriceFormat.csv"));
    }

    @Test
    public void importProperties_missingColumn_throwsParseException() {
        assertThrows(ParseException.class, CsvManager.MESSAGE_MISSING_HEADER + CsvManager.HEADER_TAGS, () ->
                importProperties("invalidPropertiesMissingColumn.csv"));
    }

    @Test
    public void importProperties_empty_throwsParseException() {
        assertThrows(ParseException.class,
                CsvManager.MESSAGE_INVALID_CSV_FORMAT, () -> importProperties("empty.csv"));
    }

    @Test
    public void importProperties_headerOnly_throwsParseException() {
        assertThrows(ParseException.class,
                CsvManager.MESSAGE_NO_ENTRIES, () -> importProperties("headerOnly.csv"));
    }

    private List<Property> importProperties(File file) throws ParseException {
        try {
            return CsvManager.importProperties(file);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error reading the file.", ioe);
        }
    }

    /**
     * Imports properties at the specified {@code filePath}.
     */
    private List<Property> importProperties(String filePath) throws ParseException {
        return importProperties(TEST_DATA_FOLDER.resolve(filePath).toFile());
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

    @Test
    public void importBuyers_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> importBuyers((File) null));
    }

    @Test
    public void importBuyers_success() {
        try {
            List<Buyer> expectedBuyers = getTypicalAddressBook().getBuyerList();
            List<Buyer> buyers = importBuyers("validBuyers.csv");
            assertEquals(buyers, expectedBuyers);
        } catch (ParseException pe) {
            throw new AssertionError("Import should not fail.", pe);
        }
    }

    @Test
    public void importBuyers_invalidFieldFormat_throwsParseException() {
        assertThrows(ParseException.class,
                Email.MESSAGE_CONSTRAINTS, () -> importBuyers("invalidBuyersEmailFormat.csv"));
    }

    @Test
    public void importBuyers_missingColumn_throwsParseException() {
        assertThrows(ParseException.class, CsvManager.MESSAGE_MISSING_HEADER + CsvManager.HEADER_TAGS, () ->
                importBuyers("invalidBuyersMissingColumn.csv"));
    }

    @Test
    public void importBuyers_empty_throwsParseException() {
        assertThrows(ParseException.class,
                CsvManager.MESSAGE_INVALID_CSV_FORMAT, () -> importBuyers("empty.csv"));
    }

    @Test
    public void importBuyers_headerOnly_throwsParseException() {
        assertThrows(ParseException.class,
                CsvManager.MESSAGE_NO_ENTRIES, () -> importBuyers("headerOnly.csv"));
    }

    private List<Buyer> importBuyers(File file) throws ParseException {
        try {
            return CsvManager.importBuyers(file);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error reading the file.", ioe);
        }
    }

    /**
     * Imports buyers at the specified {@code filePath}.
     */
    private List<Buyer> importBuyers(String filePath) throws ParseException {
        return importBuyers(TEST_DATA_FOLDER.resolve(filePath).toFile());
    }
}
