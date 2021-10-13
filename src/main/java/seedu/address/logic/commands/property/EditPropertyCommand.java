package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROPERTIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.util.Pair;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
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

/**
 * Edits the details of an existing property in the address book.
 */
public class EditPropertyCommand extends EditCommand {

    public static final String MESSAGE_EDIT_PROPERTY_SUCCESS = "Edited Property: %1$s";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists in the address book.";

    private final Index index;
    private final EditPropertyDescriptor editPropertyDescriptor;

    /**
     * @param index of the property in the filtered property list to edit
     * @param editPropertyDescriptor details to edit the property with
     */
    public EditPropertyCommand(Index index, EditPropertyDescriptor editPropertyDescriptor) {
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
        Pair<Set<Tag>, String> tagInfo = getTags(propertyToEdit, editPropertyDescriptor);
        Set<Tag> editTags = tagInfo.getKey();
        Property editedProperty = createdEditedProperty(propertyToEdit, editPropertyDescriptor, editTags);


        if (!propertyToEdit.isSameProperty(editedProperty) && model.hasProperty(editedProperty)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        }

        model.setProperty(propertyToEdit, editedProperty);
        model.updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
        String editTagDescription = tagInfo.getValue();
        String resultString = StringUtil.joinLines(String.format(MESSAGE_EDIT_PROPERTY_SUCCESS, editedProperty),
                editTagDescription);
        return new CommandResult(resultString);
    }

    /**
     * Creates and returns a {@code Property} with the details of {@code propertyToEdit}
     * edited with {@code editPropertyDescriptor}.
     */
    private Property createdEditedProperty(Property propertyToEdit, EditPropertyDescriptor
            editPropertyDescriptor, Set<Tag> editedTags) {
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

        return new Property(updatedName, updatedAddress, updatedSeller, updatedPrice, editedTags);
    }

    private Pair<Set<Tag>, String> getTags(Property propertyToEdit,
                                           EditPropertyCommand.EditPropertyDescriptor editPropertyDescriptor) {
        String messageEditPropertyTags = "";
        Set<Tag> originalTags = editPropertyDescriptor.getTags().orElse(propertyToEdit.getTags());
        Set<Tag> tagsToAdd = editPropertyDescriptor.getTagsToAdd().orElse(Collections.emptySet());
        Set<Tag> tagsToDelete = editPropertyDescriptor.getTagsToDelete().orElse(Collections.emptySet());

        Set<Tag> tagsAlreadyPresent = new HashSet<>(tagsToAdd);
        tagsAlreadyPresent.retainAll(originalTags);
        if (!tagsAlreadyPresent.isEmpty()) {
            messageEditPropertyTags += "These tags were already present:\n";
            for (Tag t : tagsAlreadyPresent) {
                messageEditPropertyTags += t + " ";
            }
            messageEditPropertyTags += "\n";
        }

        Set<Tag> tagsAlreadyAbsent = new HashSet<>(tagsToDelete);
        tagsAlreadyAbsent.removeAll(originalTags);
        if (!tagsAlreadyAbsent.isEmpty()) {
            messageEditPropertyTags += "These tags were not present:\n";
            for (Tag t : tagsAlreadyAbsent) {
                messageEditPropertyTags += t + " ";
            }
            messageEditPropertyTags += "\n";
        }

        Set<Tag> mergedSet = new HashSet<>(originalTags);
        mergedSet.removeAll(tagsToDelete);
        mergedSet.addAll(tagsToAdd);

        Set<Tag> editedTags = Collections.unmodifiableSet(mergedSet);
        return new Pair<>(editedTags, messageEditPropertyTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPropertyCommand)) {
            return false;
        }

        // state check
        EditPropertyCommand e = (EditPropertyCommand) other;
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
         * A defensive copy of {@code tags}, {@code tagsToAdd}, {@code tagsToDelete} is used internally.
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

        /**
         * Returns true if tags are reset and tags are modified by addition or deletion.
         */
        public boolean isTagsBothResetAndModified() {
            return tags != null && (CollectionUtil.isAnyNonNull(tagsToAdd, tagsToDelete));
        }

        /**
         * Returns true if the same tag is both to be added and deleted.
         */
        public boolean isAddAndDeleteTagsOverlapping() {
            if (tagsToAdd == null || tagsToDelete == null) {
                return false;
            }
            Set<Tag> intersection = new HashSet<>(tagsToAdd);
            intersection.retainAll(tagsToDelete);
            return !intersection.isEmpty();
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
         * Sets {@code tags} to this object's {@code tagsToAppend}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTagsToAdd(Set<Tag> tags) {
            this.tagsToAdd = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Sets {@code tags} to this object's {@code tagsToDelete}.
         * A defensive copy of {@code tags} is used internally.
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
