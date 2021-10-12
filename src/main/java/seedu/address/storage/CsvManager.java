package seedu.address.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.property.Property;

/**
 * Manages exporting to or importing from csv.
 */
public class CsvManager {
    private final Logger logger = LogsCenter.getLogger(CsvManager.class);

    /**
     * Exports properties to csv file.
     */
    public void exportProperties(File file, ObservableList<Property> properties) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            String[] header = { "Name", "Address", "Seller Name", "Phone", "Email", "Price", "Tags"};
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
}
