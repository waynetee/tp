package seedu.address.storage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    // ================ Import/ Export Csv methods ==============================
    /**
     * Exports properties in the given {@link ReadOnlyAddressBook} to the csv file.
     *
     * @param addressBook cannot be null.
     * @param file        cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    static void exportProperties(ReadOnlyAddressBook addressBook, File file) throws IOException {
        requireAllNonNull(file, addressBook);
        CsvManager.exportProperties(addressBook.getPropertyList(), file);
    }

    /**
     * Exports buyers in the given {@link ReadOnlyAddressBook} to the csv file.
     *
     * @param addressBook cannot be null.
     * @param file        cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    static void exportBuyers(ReadOnlyAddressBook addressBook, File file) throws IOException {
        requireAllNonNull(file, addressBook);
        CsvManager.exportBuyers(addressBook.getBuyerList(), file);
    }
}
