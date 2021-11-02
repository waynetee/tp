package seedu.address.storage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

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
     * @param file cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    static void exportProperties(ReadOnlyAddressBook addressBook, File file) throws IOException {
        requireAllNonNull(file, addressBook);
        CsvManager.exportProperties(addressBook.getCurrPropertyList(), file);
    }

    /**
     * Exports buyers in the given {@link ReadOnlyAddressBook} to the csv file.
     *
     * @param addressBook cannot be null.
     * @param file cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    static void exportBuyers(ReadOnlyAddressBook addressBook, File file) throws IOException {
        requireAllNonNull(file, addressBook);
        CsvManager.exportBuyers(addressBook.getCurrBuyerList(), file);
    }

    /**
     * Imports properties from the given csv file to the {@link ReadOnlyAddressBook}.
     *
     * @param addressBook cannot be null.
     * @param file cannot be null.
     * @return new AddressBook after importing properties.
     * @throws IOException if there was any problem reading from the file.
     * @throws ParseException if the csv file content cannot be recognized.
     */
    static AddressBook importProperties(ReadOnlyAddressBook addressBook, File file) throws IOException, ParseException {
        requireAllNonNull(file, addressBook);
        List<Property> properties = CsvManager.importProperties(file);
        AddressBook newAddressBook = new AddressBook(addressBook);
        newAddressBook.addAllProperties(properties);
        return newAddressBook;
    }

    /**
     * Imports buyers from the given csv file to the {@link ReadOnlyAddressBook}.
     *
     * @param addressBook cannot be null.
     * @param file cannot be null.
     * @return new AddressBook after importing buyers.
     * @throws IOException if there was any problem reading from the file.
     * @throws ParseException if the csv file content cannot be recognized.
     */
    static AddressBook importBuyers(ReadOnlyAddressBook addressBook, File file) throws IOException, ParseException {
        requireAllNonNull(file, addressBook);
        List<Buyer> buyers = CsvManager.importBuyers(file);
        AddressBook newAddressBook = new AddressBook(addressBook);
        newAddressBook.addAllBuyers(buyers);
        return newAddressBook;
    }
}
