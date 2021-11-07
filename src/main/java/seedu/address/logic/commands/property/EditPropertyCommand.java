package seedu.address.logic.commands.property;

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
    public static final String MESSAGE_DUPLICATE_PROPERTY = "A property with the same address already exists.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " property: Edits the details of the property identified "
            + "by the index number used in the displayed property list. "
            + "Existing values will be overwritten by the input values.\n"
            + "with the exception of adding tags. "
            + "Added tags will be appended to the current tags.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_SELLER + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "["
            + "("
            + "[" + PREFIX_TAG + "TAG]..." + " | "
            + "[" + PREFIX_ADD_TAG + "TAG_TO_ADD]... "
            + "[" + PREFIX_DELETE_TAG + "TAG_TO_DELETE]..."
            + ")"
            + "]"
            + "\n"
            + "Example: " + COMMAND_WORD + " property 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

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
        Pair<Set<Tag>, String> tagInfo = super.getTags(propertyToEdit, editPropertyDescriptor);
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
    public static class EditPropertyDescriptor extends EditTaggableDescriptor {
        private Name propertyName;
        private Address address;
        private Name sellerName;
        private Phone sellerPhone;
        private Email sellerEmail;
        private Price price;

        public EditPropertyDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditPropertyDescriptor(EditPropertyDescriptor toCopy) {
            super(toCopy);
            setPropertyName(toCopy.propertyName);
            setAddress(toCopy.address);
            setSellerName(toCopy.sellerName);
            setSellerPhone(toCopy.sellerPhone);
            setSellerEmail(toCopy.sellerEmail);
            setPrice(toCopy.price);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(propertyName, sellerPhone, sellerEmail,
                    address, sellerName, price) || super.isAnyFieldEdited();
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
                    && super.equals(e);
        }
    }
}
