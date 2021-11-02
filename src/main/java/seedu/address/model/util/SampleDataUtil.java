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

    public static Property[] getSampleProperties() {
        return new Property[]{
                makeProperty("Azalea Park", "8 Lillian Street", "Sena Joney", "99642760",
                        "sjoney9@ow.ly", "140000", getTagSet("5rm", "near market", "pasir-panjang")),
                makeProperty("Bluwaters 2", "44441 Surrey Pass", "Luella Rae", "90307175",
                        "lrae8@odnoklassniki.ru", "3340000", getTagSet("near school", "condo", "somerset")),
                makeProperty("Carissa Park", "49 David Drive", "Isabel Clemenson", "95063833",
                        "iclemenson2@imdb.com", "2460000", getTagSet("near gym", "bungalow", "admiralty")),
                makeProperty("Celadon View", "970 Mallard Street", "Kirby Mckew", "93262911",
                        "kmckew5@ftc.gov", "2950000", getTagSet("near gym", "lakeside", "terrace")),
                makeProperty("Changi Grove", "595 Clyde Gallagher Street", "Sullivan Wyllie", "93356669",
                        "swyllieh@elegantthemes.com", "3640000", getTagSet("near market", "bungalow", "pasir-panjang")),
                makeProperty("Coastal Breeze Residences", "9009 Almo Hill", "Kailey Kinnier", "95845374",
                        "kkinnier3@who.int", "1170000", getTagSet("5rm", "near market", "bukit-panjang")),
                makeProperty("Edelweiss Park", "36158 Dovetail Point", "Lek Wanka", "96393667",
                        "lwankaj@msu.edu", "4810000", getTagSet("redhill", "beside mall", "terrace")),
                makeProperty("Jlb Residences", "455 Butternut Plaza", "Ernestus Pamment", "90516766",
                        "epammentb@delicious.com", "3180000", getTagSet("near market", "terrace", "woodleigh")),
                makeProperty("Le Loyang", "35 Randy Pass", "Cynde Challiner", "97233120",
                        "cchalliner7@wunderground.com", "4050000", getTagSet("near gym", "condo", "bartley")),
                makeProperty("Liria Terrace", "8 Westridge Parkway", "Carter Mcclary", "94516041",
                        "cmcclary4@goo.gl", "320000", getTagSet("beside mall", "tiong-bahru", "maisonette")),
                makeProperty("Loyang Gardens", "07 Hazelcrest Hill", "Myles Dewey", "96280061",
                        "mdewey1@google.es", "2730000", getTagSet("near school", "admiralty", "condo")),
                makeProperty("Loyang Green", "3665 Golf View Court", "Lauren Falkner", "96648866",
                        "lfalknerf@parallels.com", "2580000", getTagSet("bungalow", "near school", "admiralty")),
                makeProperty("Loyang Mansion", "4 South Hill", "Jaime Mulvaney", "94340579",
                        "jmulvaneyc@4shared.com", "3090000", getTagSet("near market", "bungalow", "boon-keng")),
                makeProperty("Loyang Valley", "88764 Hoard Crossing", "Electra Whitwham", "91045335",
                        "ewhitwham0@simplemachines.org", "1970000", getTagSet("holland-village", "near school",
                                "terrace")),
                makeProperty("Loyang Villas", "5472 Birchwood Hill", "Jennine Cheley", "93268669",
                        "jcheleyg@posterous.com", "4560000", getTagSet("near market", "condo", "bukit-gombak"))
        };
    }

    public static Buyer[] getSampleBuyers() {
        return new Buyer[]{
                makeBuyer("Dame Rottweiler", "93234267", "rot@wild.com", "4500000",
                        getTagSet("near gym", "condo", "botanic-gardens")),
                makeBuyer("Douglas Cunningham", "67446507", "boyd16@yahoo.com", "1610000",
                        getTagSet("near gym", "holland-village", "semi-d")),
                makeBuyer("Duke Mason", "93320325", "mason@example.com", "4430000",
                        getTagSet("near gym", "semi-d", "hillview")),
                makeBuyer("Erica Perkins", "62566001", "bmann@yahoo.com", "330000",
                        getTagSet("near market", "ec")),
                makeBuyer("Frail Duckie", "93234267", "fail@mega.com", "4500000",
                        getTagSet("near gym", "semi-d")),
                makeBuyer("Judy Webb", "62699637", "hjohns@yahoo.com", "3930000",
                        getTagSet("near market", "bungalow")),
                makeBuyer("Lorena Lucas", "65668516", "cschuster@hagenes.org", "3920000",
                        getTagSet("near market", "condo")),
                makeBuyer("Mable Munoz", "62684286", "mpredovic@gmail.com", "4140000",
                        getTagSet("near school", "condo", "somerset")),
                makeBuyer("Mate Automaton", "94423513", "auto@email.com", "600000",
                        getTagSet("5rm", "beside mall")),
                makeBuyer("Maxine Hoffman", "64381671", "windler.gisselle@yahoo.com", "1740000",
                        getTagSet("near gym", "dakota", "terrace")),
                makeBuyer("Mecurius Beanstalk", "96666666", "bean@java.com", "800000",
                        getTagSet("near gym", "hillview", "ec")),
                makeBuyer("Meredith Glover", "67437188", "mupton@cormier.com", "4020000",
                        getTagSet("near school", "condo")),
                makeBuyer("Neet Bitterman", "91234567", "bit@bucket.com", "2010000",
                        getTagSet("near school", "semi-d")),
                makeBuyer("Rex Watkins", "63772867", "michele28@yahoo.com", "3480000",
                        getTagSet("near market", "hougang", "bungalow")),
                makeBuyer("Sally Focal", "94420945", "focal@gmail.com", "1360000",
                        getTagSet("5rm", "beside park"))
        };
    }

    private static Property makeProperty(String name, String address, String sellerName, String sellerPhone,
                                         String sellerEmail, String price, Set<Tag> tags) {
        Person seller = new Person(new Name(sellerName), new Phone(sellerPhone), new Email(sellerEmail));
        return new Property(new Name(name), new Address(address), seller, new Price(price), tags);
    }

    private static Buyer makeBuyer(String name, String phone, String email, String budget, Set<Tag> tags) {
        return new Buyer(new Person(new Name(name), new Phone(phone), new Email(email)), new Price(budget), tags);
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
