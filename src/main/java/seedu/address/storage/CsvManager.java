package seedu.address.storage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

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
    public static final String HEADER_NAME = "Name";
    public static final String HEADER_PHONE = "Phone";
    public static final String HEADER_EMAIL = "Email";
    public static final String HEADER_ADDRESS = "Address";
    public static final String HEADER_SELLER = "Seller Name";
    public static final String HEADER_PRICE = "Price";
    public static final String HEADER_BUDGET = "Budget";
    public static final String HEADER_TAGS = "Tags";

    public static final String MESSAGE_INVALID_CSV_FORMAT = "Invalid csv format!";
    public static final String MESSAGE_MISSING_HEADER = "Missing Header: ";
    public static final String MESSAGE_NO_ENTRIES = "No recognized entries within csv!";

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
                    buyer.getEmail().toString(), buyer.getPrice().toString(), tagString};
            writer.writeNext(entries);
        }
        writer.close();
    }

    private static Property getProperty(Map<String, String> values) throws ParseException {
        for (String header : propertyHeaders) {
            if (!values.containsKey(header)) {
                throw new ParseException(MESSAGE_MISSING_HEADER + header);
            }
        }
        Name propertyName = ParserUtil.parseName(values.get(HEADER_NAME));
        Address address = ParserUtil.parseAddress(values.get(HEADER_ADDRESS));
        Name sellerName = ParserUtil.parseName(values.get(HEADER_SELLER));
        Phone sellerPhone = ParserUtil.parsePhone(values.get(HEADER_PHONE));
        Email sellerEmail = ParserUtil.parseEmail(values.get(HEADER_EMAIL));
        Person seller = new Person(sellerName, sellerPhone, sellerEmail);
        Price price = ParserUtil.parsePrice(values.get(HEADER_PRICE));
        Set<Tag> tagList = new HashSet<Tag>();
        if (!values.get(HEADER_TAGS).isEmpty()) {
            tagList = ParserUtil.parseTags(Arrays.asList(values.get(HEADER_TAGS).split(",")));
        }

        Property property = new Property(propertyName, address, seller, price, tagList);
        return property;
    }

    private static Buyer getBuyer(Map<String, String> values) throws ParseException {
        for (String header : buyerHeaders) {
            if (!values.containsKey(header)) {
                throw new ParseException(MESSAGE_MISSING_HEADER + header);
            }
        }
        Name name = ParserUtil.parseName(values.get(HEADER_NAME));
        Phone phone = ParserUtil.parsePhone(values.get(HEADER_PHONE));
        Email email = ParserUtil.parseEmail(values.get(HEADER_EMAIL));
        Price maxPrice = ParserUtil.parsePrice(values.get(HEADER_BUDGET));
        Set<Tag> tagList = new HashSet<Tag>();
        if (!values.get(HEADER_TAGS).isEmpty()) {
            tagList = ParserUtil.parseTags(Arrays.asList(values.get(HEADER_TAGS).split(",")));
        }

        Buyer buyer = new Buyer(name, phone, email, maxPrice, tagList);
        return buyer;
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
        CSVReaderHeaderAware reader;
        try {
            reader = new CSVReaderHeaderAware(new FileReader(file));
        } catch (NullPointerException ex) {
            throw new ParseException(MESSAGE_INVALID_CSV_FORMAT);
        }
        Map<String, String> values;
        List<Property> properties = new ArrayList<>();
        try {
            while ((values = reader.readMap()) != null) {
                properties.add(getProperty(values));
            }
        } catch (CsvValidationException e) {
            throw new ParseException(MESSAGE_INVALID_CSV_FORMAT);
        } catch (IOException ioe) {
            throw new ParseException(MESSAGE_INVALID_CSV_FORMAT, ioe);
        }
        if (properties.isEmpty()) {
            throw new ParseException(MESSAGE_NO_ENTRIES);
        }
        return properties;
    }

    /**
     * Imports buyers in the given csv file to the {@link ReadOnlyAddressBook}.
     *
     * @param file cannot be null.
     * @throws IOException if there was any problem writing to the file.
     * @throws ParseException if the csv file content cannot be recognized.
     */
    public static List<Buyer> importBuyers(File file) throws IOException, ParseException {
        requireAllNonNull(file);
        CSVReaderHeaderAware reader;
        try {
            reader = new CSVReaderHeaderAware(new FileReader(file));
        } catch (NullPointerException ex) {
            throw new ParseException(MESSAGE_INVALID_CSV_FORMAT);
        }
        Map<String, String> values;
        List<Buyer> buyers = new ArrayList<>();
        try {
            while ((values = reader.readMap()) != null) {
                buyers.add(getBuyer(values));
            }
        } catch (CsvValidationException e) {
            throw new ParseException(MESSAGE_INVALID_CSV_FORMAT);
        } catch (IOException ioe) {
            throw new ParseException(MESSAGE_INVALID_CSV_FORMAT, ioe);
        }
        if (buyers.isEmpty()) {
            throw new ParseException(MESSAGE_NO_ENTRIES);
        }
        return buyers;
    }
}
