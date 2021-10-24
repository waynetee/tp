package seedu.address.storage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

/**
 * Manages exporting to or importing from csv.
 */
public class CsvManager {
    /**
     * Exports properties in the given {@link ReadOnlyAddressBook} to the csv file.
     *
     * @param properties cannot be null.
     * @param file        cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public static void exportProperties(List<Property> properties, File file) throws IOException {
        requireAllNonNull(file, properties);
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        String[] header = {"Name", "Address", "Seller Name", "Phone", "Email", "Price", "Tags"};
        writer.writeNext(header);
        for (Property property : properties) {
            String tagString = property.getTags().stream()
                    .map(x -> x.tagName)
                    .collect(Collectors.joining(","));
            String[] entries = {property.getName().toString(), property.getAddress().toString(),
                    property.getSeller().getName().toString(), property.getSeller().getPhone().toString(),
                    property.getSeller().getEmail().toString(), property.getPrice().toString(), tagString};
            writer.writeNext(entries);
        }
        writer.close();
    }

    /**
     * Exports buyers in the given {@link ReadOnlyAddressBook} to the csv file.
     *
     * @param buyers cannot be null.
     * @param file        cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public static void exportBuyers(List<Buyer> buyers, File file) throws IOException {
        requireAllNonNull(file, buyers);
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        String[] header = {"Name", "Phone", "Email", "Budget", "Tags"};
        writer.writeNext(header);
        for (Buyer buyer : buyers) {
            String tagString = buyer.getTags().stream()
                    .map(x -> x.tagName)
                    .collect(Collectors.joining(","));
            String[] entries = {buyer.getName().toString(), buyer.getPhone().toString(),
                    buyer.getEmail().toString(), buyer.getPrice().toString(), tagString};
            writer.writeNext(entries);
        }
        writer.close();
    }
}
