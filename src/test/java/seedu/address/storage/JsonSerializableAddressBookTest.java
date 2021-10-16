package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalAddressBook;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PROPERTIES_FILE = TEST_DATA_FOLDER.resolve("typicalPropertiesAddressBook.json");
    private static final Path INVALID_PROPERTY_FILE = TEST_DATA_FOLDER.resolve("invalidPropertyAddressBook.json");
    private static final Path DUPLICATE_PROPERTY_FILE = TEST_DATA_FOLDER.resolve("duplicatePropertyAddressBook.json");

    private static final Path WRITE_TO_FILE = TEST_DATA_FOLDER.resolve("test.json");

    @Test
    public void toModelType_typicalPropertiesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PROPERTIES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPropertiesAddressBook = TypicalAddressBook.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPropertiesAddressBook);
    }

    // Helper method to write the current typicalAddressBook to a temporary file
    // Disabled as this is meant to be run manually.
    @Test
    public void writeAddressBook() throws IOException {
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(TypicalAddressBook.getTypicalAddressBook()),
                WRITE_TO_FILE);
    }

    @Test
    public void toModelType_invalidPropertyFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PROPERTY_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateProperties_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PROPERTY_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PROPERTY,
                dataFromFile::toModelType);
    }

}
