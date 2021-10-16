package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Property[] getSampleProperties() {
        return new Property[] {
            new Property(new Name("Geylang St 29"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com")), new Price("654321"),
                getTagSet("friends")),
            new Property(new Name("Dee Gardens"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Person(new Name("Beatrice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com")), new Price("654321"),
                getTagSet("colleagues", "friends")),
            new Property(new Name("Olive Gardens"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com")), new Price("654321"),
                getTagSet("neighbours")),
            new Property(new Name("Pear Gardens"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Person(new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com")), new Price("654321"),
                getTagSet("family")),
            new Property(new Name("Tampa Bay"), new Address("Blk 47 Tampines Street 20, #17-35"),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com")), new Price("654321"),
                getTagSet("classmates")),
            new Property(new Name("Aljunied St 85"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com")), new Price("654321"),
                getTagSet("colleagues"))
        };
    }

    public static Buyer[] getSampleBuyers() {
        return new Buyer[] {
            new Buyer(new Person(new Name("Sally Focal"), new Phone("94420945"), new Email("focal@gmail.com")),
                new Price("420000"),
                getTagSet("near school")),
            new Buyer(new Person(new Name("Duke Mason"), new Phone("93320325"), new Email("mason@example.com")),
                new Price("420005"),
                getTagSet("4rm")),
            new Buyer(new Person(new Name("Mate Automaton"), new Phone("94423513"), new Email("auto@email.com")),
                new Price("420010"),
                getTagSet("near cdb")),
            new Buyer(new Person(new Name("Neet Bitterman"), new Phone("91234567"), new Email("bit@bucket.com")),
                new Price("240015"),
                getTagSet("huge house", "friends")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Property sampleProperty : getSampleProperties()) {
            sampleAb.addProperty(sampleProperty);
        }

        for (Buyer sampleBuyer : getSampleBuyers()) {
            sampleAb.addBuyer(sampleBuyer);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
