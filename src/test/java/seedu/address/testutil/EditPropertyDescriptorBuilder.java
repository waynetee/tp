package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.property.EditPropertyCommand;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.property.Address;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPropertyDescriptor objects.
 */
public class EditPropertyDescriptorBuilder {

    private EditPropertyCommand.EditPropertyDescriptor descriptor;

    public EditPropertyDescriptorBuilder() {
        descriptor = new EditPropertyCommand.EditPropertyDescriptor();
    }

    public EditPropertyDescriptorBuilder(EditPropertyCommand.EditPropertyDescriptor descriptor) {
        this.descriptor = new EditPropertyCommand.EditPropertyDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPropertyDescriptor} with fields containing {@code property}'s details
     */
    public EditPropertyDescriptorBuilder(Property property) {
        descriptor = new EditPropertyCommand.EditPropertyDescriptor();
        descriptor.setPropertyName(property.getName());
        descriptor.setAddress(property.getAddress());
        descriptor.setSellerName(property.getSeller().getName());
        descriptor.setSellerPhone(property.getSeller().getPhone());
        descriptor.setSellerEmail(property.getSeller().getEmail());
        descriptor.setPrice(property.getPrice());
        descriptor.setTags(property.getTags());
        descriptor.setTagsToAdd(null);
        descriptor.setTagsToDelete(null);
    }

    /**
     * Sets the {@code Name} of the {@code EditPropertyDescriptor} that we are building.
     */
    public EditPropertyDescriptorBuilder withName(String name) {
        descriptor.setPropertyName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPropertyDescriptor} that we are building.
     */
    public EditPropertyDescriptorBuilder withPhone(String phone) {
        descriptor.setSellerPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPropertyDescriptor} that we are building.
     */
    public EditPropertyDescriptorBuilder withEmail(String email) {
        descriptor.setSellerEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPropertyDescriptor} that we are building.
     */
    public EditPropertyDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Seller} of the {@code EditPropertyDescriptor} that we are building.
     */
    public EditPropertyDescriptorBuilder withSeller(String seller) {
        descriptor.setSellerName(new Name(seller));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditPropertyDescriptor} that we are building.
     */
    public EditPropertyDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPropertyDescriptor}
     * that we are building.
     */
    public EditPropertyDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code tagsToAdd} into a {@code Set<Tag>} and set it to the {@code EditPropertyDescriptor}
     * that we are building.
     */
    public EditPropertyDescriptorBuilder withTagsToAdd(String... tagsToAdd) {
        Set<Tag> tagSet = Stream.of(tagsToAdd).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTagsToAdd(tagSet);
        return this;
    }

    /**
     * Parses the {@code tagsToDelete} into a {@code Set<Tag>} and set it to the {@code EditPropertyDescriptor}
     * that we are building.
     */
    public EditPropertyDescriptorBuilder withTagsToDelete(String... tagsToDelete) {
        Set<Tag> tagSet = Stream.of(tagsToDelete).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTagsToDelete(tagSet);
        return this;
    }

    public EditPropertyCommand.EditPropertyDescriptor build() {
        return descriptor;
    }
}
