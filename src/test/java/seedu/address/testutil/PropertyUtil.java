package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_PROPERTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.property.EditPropertyCommand.EditPropertyDescriptor;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Property.
 */
public class PropertyUtil {

    /**
     * Returns an add command string for adding the {@code property}.
     */
    public static String getAddCommand(Property property) {
        return AddCommand.COMMAND_WORD + " " + PREAMBLE_PROPERTY + " " + getPropertyDetails(property);
    }

    /**
     * Returns the part of command string for the given {@code property}'s details.
     */
    public static String getPropertyDetails(Property property) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + property.getName().fullName + " ");
        sb.append(PREFIX_ADDRESS + property.getAddress().value + " ");
        sb.append(PREFIX_SELLER + property.getSeller().getName().fullName + " ");
        sb.append(PREFIX_PHONE + property.getSeller().getPhone().value + " ");
        sb.append(PREFIX_EMAIL + property.getSeller().getEmail().value + " ");
        sb.append(PREFIX_PRICE + property.getPrice().value.toString() + " ");
        property.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPropertyDescriptor}'s details.
     */
    public static String getEditPropertyDescriptorDetails(EditPropertyDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getPropertyName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getSellerName().ifPresent(seller -> sb.append(PREFIX_SELLER).append(seller.fullName).append(" "));
        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_PRICE).append(price.value).append(" "));
        descriptor.getSellerPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getSellerEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
