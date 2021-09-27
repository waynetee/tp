package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROPERTIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Person;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.property.Address;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagSet;

/**
 * Edits the details of an existing property in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the property identified "
            + "by the index number used in the displayed property list. "
            + "Existing values will be overwritten by the input values, with the exception of adding tags. "
            + "Added tags will be appended to the current tags. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_SELLER + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_ADD_TAG + "TAG]..."
            + "[" + PREFIX_DELETE_TAG + "TAG]..."
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PROPERTY_SUCCESS = "Edited Property: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists in the address book.";

    private final Index index;
    private final EditPropertyDescriptor editPropertyDescriptor;

    /**
     * @param index of the property in the filtered property list to edit
     * @param editPropertyDescriptor details to edit the property with
     */
    public EditCommand(Index index, EditPropertyDescriptor editPropertyDescriptor) {
        requireNonNull(index);
        requireNonNull(editPropertyDescriptor);

        this.index = index;
        this.editPropertyDescriptor = new EditPropertyDescriptor(editPropertyDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Property> lastShownList = model.getFilteredPropertyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
        }

        Property propertyToEdit = lastShownList.get(index.getZeroBased());
        Property editedProperty = createdEditedProperty(propertyToEdit, editPropertyDescriptor);

        if (!propertyToEdit.isSameProperty(editedProperty) && model.hasProperty(editedProperty)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        }

        model.setProperty(propertyToEdit, editedProperty);
        model.updateFiltedPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
        return new CommandResult(String.format(MESSAGE_EDIT_PROPERTY_SUCCESS, editedProperty));
    }

    /**
     * Creates and returns a {@code Property} with the details of {@code propertyToEdit}
     * edited with {@code editPropertyDescriptor}.
     */
    private static Property createdEditedProperty(Property propertyToEdit, EditPropertyDescriptor
            editPropertyDescriptor) {
        assert propertyToEdit != null;

        Name updatedName = editPropertyDescriptor.getPropertyName().orElse(propertyToEdit.getName());
        Address updatedAddress = editPropertyDescriptor.getAddress().orElse(propertyToEdit.getAddress());
        Name updatedSellerName = editPropertyDescriptor.getSellerName().orElse(propertyToEdit.getSeller().getName());
        Phone updatedSellerPhone = editPropertyDescriptor.getSellerPhone()
                .orElse(propertyToEdit.getSeller().getPhone());
        Email updatedSellerEmail = editPropertyDescriptor.getSellerEmail()
                .orElse(propertyToEdit.getSeller().getEmail());
        Person updatedSeller = new Person(updatedSellerName, updatedSellerPhone, updatedSellerEmail);
        Price updatedPrice = editPropertyDescriptor.getPrice().orElse(propertyToEdit.getPrice());
        Set<Tag> updatedTags = editPropertyDescriptor.getTags().orElse(propertyToEdit.getTags());
        Set<Tag> editedTags = TagSet.mergeAndRemove(updatedTags,
                editPropertyDescriptor.getTagsToAdd().get(),
                editPropertyDescriptor.getTagsToDelete().get());

        return new Property(updatedName, updatedAddress, updatedSeller, updatedPrice, editedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPropertyDescriptor.equals(e.editPropertyDescriptor);
    }

    /**
     * Stores the details to edit the property with. Each non-empty field value will replace the
     * corresponding field value of the property.
     */
    public static class EditPropertyDescriptor {
        private Name propertyName;
        private Address address;
        private Name sellerName;
        private Phone sellerPhone;
        private Email sellerEmail;
        private Price price;
        private Set<Tag> tags;
        private Set<Tag> tagsToAdd;
        private Set<Tag> tagsToDelete;

        public EditPropertyDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPropertyDescriptor(EditPropertyDescriptor toCopy) {
            setPropertyName(toCopy.propertyName);
            setAddress(toCopy.address);
            setSellerName(toCopy.sellerName);
            setSellerPhone(toCopy.sellerPhone);
            setSellerEmail(toCopy.sellerEmail);
            setPrice(toCopy.price);
            setTags(toCopy.tags);
            setTagsToAdd(toCopy.tagsToAdd);
            setTagsToDelete(toCopy.tagsToDelete);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(propertyName, sellerPhone, sellerEmail,
                    address, tags, tagsToAdd, tagsToDelete, sellerName, price);
        }

        public void setPropertyName(Name propertyName) {
            this.propertyName = propertyName;
        }

        public Optional<Name> getPropertyName() {
            return Optional.ofNullable(propertyName);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setSellerName(Name sellerName) {
            this.sellerName = sellerName;
        }

        public Optional<Name> getSellerName() {
            return Optional.ofNullable(sellerName);
        }

        public void setSellerPhone(Phone sellerPhone) {
            this.sellerPhone = sellerPhone;
        }

        public Optional<Phone> getSellerPhone() {
            return Optional.ofNullable(sellerPhone);
        }

        public void setSellerEmail(Email sellerEmail) {
            this.sellerEmail = sellerEmail;
        }

        public Optional<Email> getSellerEmail() {
            return Optional.ofNullable(sellerEmail);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tagsToAppend} is used internally.
         */
        public void setTagsToAdd(Set<Tag> tags) {
            this.tagsToAdd = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tagsToDelete} is used internally.
         */
        public void setTagsToDelete(Set<Tag> tags) {
            this.tagsToDelete = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tagsToAdd} is null.
         */
        public Optional<Set<Tag>> getTagsToAdd() {
            return (tagsToAdd != null) ? Optional.of(Collections.unmodifiableSet(tagsToAdd)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tagsToDelete} is null.
         */
        public Optional<Set<Tag>> getTagsToDelete() {
            return (tagsToDelete != null) ? Optional.of(Collections.unmodifiableSet(tagsToDelete)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPropertyDescriptor)) {
                return false;
            }

            // state check
            EditPropertyDescriptor e = (EditPropertyDescriptor) other;

            return getPropertyName().equals(e.getPropertyName())
                    && getAddress().equals(e.getAddress())
                    && getSellerName().equals(e.getSellerName())
                    && getSellerPhone().equals(e.getSellerPhone())
                    && getSellerEmail().equals(e.getSellerEmail())
                    && getPrice().equals(e.getPrice())
                    && getTags().equals(e.getTags())
                    && getTagsToAdd().equals(e.getTagsToAdd())
                    && getTagsToDelete().equals(e.getTagsToDelete());
        }
    }
}
