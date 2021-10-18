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
    public static final String MICROSCOPIC_PRICE = "419999";
    public static final String VERY_SMALL_PRICE = "420000";
    public static final String SMALL_PRICE = "420001";
    public static final String MEDIUM_PRICE = "420002";
    public static final String LARGE_PRICE = "420003";
    public static final String VERY_LARGE_PRICE = "420004";
    public static final String LUDICROUS_PRICE = "420005";

    public static Property[] getSampleProperties() {
        return new Property[] {
            new Property(new Name("Geylang St 29"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com")), new Price(MICROSCOPIC_PRICE),
                getTagSet("flowers", "garden")),
            new Property(new Name("Dee Gardens"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Person(new Name("Beatrice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com")), new Price(VERY_SMALL_PRICE),
                getTagSet( "quiet")),
            new Property(new Name("Olive Gardens"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com")), new Price(SMALL_PRICE),
                getTagSet("cathedral")),
            new Property(new Name("Pear Gardens"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Person(new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com")), new Price(MEDIUM_PRICE),
                getTagSet("near cbd")),
            new Property(new Name("Tampa Bay"), new Address("Blk 47 Tampines Street 20, #17-35"),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com")), new Price(LARGE_PRICE),
                getTagSet("noisy", "party")),
            new Property(new Name("Aljunied St 85"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com")), new Price(VERY_LARGE_PRICE),
                getTagSet("noisefree"))
        };
    }

    public static Buyer[] getSampleBuyers() {
        return new Buyer[] {
            new Buyer(new Person(new Name("Sally Focal"), new Phone("94420945"), new Email("focal@gmail.com")),
                new Price(MICROSCOPIC_PRICE),
                getTagSet("near school", "flowers", "garden")),
            new Buyer(new Person(new Name("Duke Mason"), new Phone("93320325"), new Email("mason@example.com")),
                new Price(VERY_SMALL_PRICE),
                getTagSet("4rm")),
            new Buyer(new Person(new Name("Mate Automaton"), new Phone("94423513"), new Email("auto@email.com")),
                new Price(SMALL_PRICE),
                getTagSet("near cdb")),
            new Buyer(new Person(new Name("Neet Bitterman"), new Phone("91234567"), new Email("bit@bucket.com")),
                new Price(MEDIUM_PRICE),
                getTagSet("huge house", "party", "noisy")),
            new Buyer(new Person(new Name("Frail Duckie"), new Phone("93234267"), new Email("fail@mega.com")),
                new Price(LARGE_PRICE),
                getTagSet("1rm", "tiny")),
            new Buyer(new Person(new Name("Dame Rottweiler"), new Phone("93234267"), new Email("rot@wild.com")),
                new Price(VERY_LARGE_PRICE),
                getTagSet("house", "1rm")),
            new Buyer(new Person(new Name("Mecurius Beanstalk"), new Phone("96666666"), new Email("bean@java.com")),
                new Price(LUDICROUS_PRICE),
                getTagSet("house", "1rm")),
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
