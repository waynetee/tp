package seedu.address.storage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

/**
 * Manages exporting to or importing from csv.
 */
public class CsvManager {
    private final Logger logger = LogsCenter.getLogger(CsvManager.class);

    /**
     * Exports properties to csv file.
     */
    public void exportProperties(File file, List<Property> properties) {
        requireAllNonNull(file, properties);
        try {
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
        } catch (IOException ioe) {
            logger.warning("Problem while exporting Properties.");
        }
    }

    /**
     * Exports buyers to csv file.
     */
    public void exportBuyers(File file, List<Buyer> buyers) {
        requireAllNonNull(file, buyers);
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            String[] header = {"Name", "Phone", "Email", "Budget", "Tags"};
            writer.writeNext(header);
            for (Buyer buyer : buyers) {
                String tagString = buyer.getTags().stream()
                        .map(x -> x.tagName)
                        .collect(Collectors.joining(","));
                String[] entries = {buyer.getName().toString(), buyer.getPhone().toString(),
                        buyer.getEmail().toString(), buyer.getMaxPrice().toString(), tagString};
                writer.writeNext(entries);
            }
            writer.close();
        } catch (IOException ioe) {
            logger.warning("Problem while exporting Buyers.");
        }
    }
}
