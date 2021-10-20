package seedu.address.storage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Person;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.property.Address;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;

/**
 * Manages exporting to or importing from csv.
 */
public class CsvManager {
    private static final String HEADER_NAME = "Name";
    private static final String HEADER_PHONE = "Phone";
    private static final String HEADER_EMAIL = "Email";
    private static final String HEADER_ADDRESS = "Address";
    private static final String HEADER_SELLER = "Seller Name";
    private static final String HEADER_PRICE = "Price";
    private static final String HEADER_BUDGET = "budget";
    private static final String HEADER_TAGS = "Tags";

    private static final String[] propertyHeaders = {HEADER_NAME, HEADER_ADDRESS, HEADER_SELLER,
            HEADER_PHONE, HEADER_EMAIL, HEADER_PRICE, HEADER_TAGS};

    private static final String[] buyerHeaders = {HEADER_NAME, HEADER_PHONE, HEADER_EMAIL, HEADER_BUDGET, HEADER_TAGS};


    /**
     * Exports properties in the given {@link ReadOnlyAddressBook} to the csv file.
     *
     * @param properties cannot be null.
     * @param file cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public static void exportProperties(List<Property> properties, File file) throws IOException {
        requireAllNonNull(file, properties);
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        writer.writeNext(propertyHeaders);
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
     * @param file cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public static void exportBuyers(List<Buyer> buyers, File file) throws IOException {
        requireAllNonNull(file, buyers);
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        writer.writeNext(buyerHeaders);
        for (Buyer buyer : buyers) {
            String tagString = buyer.getTags().stream()
                    .map(x -> x.tagName)
                    .collect(Collectors.joining(","));
            String[] entries = {buyer.getName().toString(), buyer.getPhone().toString(),
                    buyer.getEmail().toString(), buyer.getMaxPrice().toString(), tagString};
            writer.writeNext(entries);
        }
        writer.close();
    }

    private static Property getProperty(Map<String, String> values) throws ParseException {
        Name propertyName = ParserUtil.parseName(values.get(PREFIX_NAME));
        Address address = ParserUtil.parseAddress(values.get(PREFIX_ADDRESS));
        Name sellerName = ParserUtil.parseName(values.get(PREFIX_SELLER));
        Phone sellerPhone = ParserUtil.parsePhone(values.get(PREFIX_PHONE));
        Email sellerEmail = ParserUtil.parseEmail(values.get(PREFIX_EMAIL));
        Person seller = new Person(sellerName, sellerPhone, sellerEmail);
        Price price = ParserUtil.parsePrice(values.get(PREFIX_PRICE));
        Set<Tag> tagList = ParserUtil.parseTags(Arrays.asList(values.get(PREFIX_TAG).split(",")));

        Property property = new Property(propertyName, address, seller, price, tagList);
        return property;
    }

    /**
     * Imports properties in the given csv file to the {@link ReadOnlyAddressBook}.
     *
     * @param file cannot be null.
     * @throws IOException if there was any problem writing to the file.
     * @throws ParseException if the csv file content cannot be recognized.
     */
    public static List<Property> importProperties(File file) throws IOException, ParseException {
        requireAllNonNull(file);
        String[] headers = {"Name", "Address", "Seller Name", "Phone", "Email", "Price", "Tags"};
        CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(file));
        Map<String, String> values;
        List<Property> properties = new ArrayList<>();
        try {
            while ((values = reader.readMap()) != null) {
                for (String header : headers) {
                    if (!values.containsKey(header)) {
                        throw new ParseException("missing entry in importProperties: " + header);
                    }
                }
                properties.add(getProperty(values));
            }
        } catch (CsvValidationException e) {
            throw new ParseException("Invalid csv format!");
        }
        return properties;
    }
}
