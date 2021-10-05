package seedu.address.logic.commands.buyer;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROPERTIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing property in the address book.
 */
public class EditBuyerCommand extends EditCommand {
    public static final String MESSAGE_EDIT_PROPERTY_SUCCESS = "Edited Buyer: %1$s";
    public static final String MESSAGE_DUPLICATE_BUYER = "This buyer already exists in the address book.";

    private final Index index;
    private final EditBuyerDescriptor editBuyerDescriptor;

    /**
     * @param index of the property in the filtered property list to edit
     * @param editBuyerDescriptor details to edit the buyer with
     */
    public EditBuyerCommand(Index index, EditBuyerDescriptor editBuyerDescriptor) {
        this.index = index;
        this.editBuyerDescriptor = new EditBuyerDescriptor(editBuyerDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Property> lastShownList = model.getFilteredPropertyList();
        // TODO: Buyer edit
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
        }

        Property propertyToEdit = lastShownList.get(index.getZeroBased());
        Property editedProperty = createdEditedProperty(propertyToEdit, editPropertyDescriptor);

        if (!propertyToEdit.isSameProperty(editedProperty) && model.hasProperty(editedProperty)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUYER);
        }

        model.setProperty(propertyToEdit, editedProperty);
        model.updateFiltedPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
        return new CommandResult(String.format(MESSAGE_EDIT_PROPERTY_SUCCESS, editedProperty));
    }

    /**
     * Creates and returns a {@code Buyer} with the details of {@code buyerToEdit}
     * edited with {@code editBuyerDescriptor}.
     */
    private static Buyer createdEditBuyer(Buyer buyerToEdit, EditBuyerDescriptor editBuyerDescriptor) {
        assert buyerToEdit != null;

        Name updatedName = editBuyerDescriptor.getName().orElse(buyerToEdit.getName());

        Phone updatedPhone = editBuyerDescriptor.getPhone()
                .orElse(buyerToEdit.getPhone());
        Email updatedEmail = editBuyerDescriptor.getEmail()
                .orElse(buyerToEdit.getEmail());
        Price updatedPrice = editBuyerDescriptor.getMaxPrice().orElse(buyerToEdit.getMaxPrice());
        Set<Tag> updatedTags = editBuyerDescriptor.getTags().orElse(buyerToEdit.getTags());

        return new Buyer(updatedName, updatedPhone, updatedEmail, updatedPrice, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBuyerCommand)) {
            return false;
        }

        // state check
        EditBuyerCommand e = (EditBuyerCommand) other;
        return index.equals(e.index)
                && editBuyerDescriptor.equals(e.editBuyerDescriptor);
    }

    public static class EditBuyerDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Price maxPrice;
        // TODO
        private Set<Tag> tags;

        public EditBuyerDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBuyerDescriptor(EditBuyerDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setMaxPrice(toCopy.maxPrice);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, maxPrice, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setMaxPrice(Price maxPrice) {
            this.maxPrice = maxPrice;
        }

        public Optional<Price> getMaxPrice() {
            return Optional.ofNullable(maxPrice);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBuyerDescriptor)) {
                return false;
            }

            // state check
            EditBuyerDescriptor e = (EditBuyerDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getMaxPrice().equals(e.getMaxPrice())
                    && getTags().equals(e.getTags());
        }
    }
}
