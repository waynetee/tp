package seedu.address.logic.commands.buyer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUYERS;

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
import seedu.address.model.field.Phone;
import seedu.address.model.field.Price;
import seedu.address.model.property.Buyer;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing buyer in the address book.
 */
public class EditBuyerCommand extends EditCommand {
    public static final String MESSAGE_EDIT_BUYER_SUCCESS = "Edited Buyer: %1$s";
    public static final String MESSAGE_DUPLICATE_BUYER = "This buyer already exists in the address book.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " buyer: Edits the details of the buyer identified "
            + "by the index number used in the displayed buyer list. "
            + "Existing values will be overwritten by the input values.\n"
            + "with the exception of adding tags. "
            + "Added tags will be appended to the current tags.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_PRICE + "BUDGET] "
            + "["
            + "("
            + "[" + PREFIX_TAG + "TAG]..." + " | "
            + "[" + PREFIX_ADD_TAG + "TAG_TO_ADD]... "
            + "[" + PREFIX_DELETE_TAG + "TAG_TO_DELETE]..."
            + ")"
            + "]"
            + "\n"
            + "Example: " + COMMAND_WORD + " buyer 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    private final Index index;
    private final EditBuyerDescriptor editBuyerDescriptor;

    /**
     * @param index of the buyer in the filtered buyer list to edit
     * @param editBuyerDescriptor details to edit the buyer with
     */
    public EditBuyerCommand(Index index, EditBuyerDescriptor editBuyerDescriptor) {
        this.index = index;
        this.editBuyerDescriptor = new EditBuyerDescriptor(editBuyerDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Buyer> lastShownList = model.getFilteredBuyerList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUYER_DISPLAYED_INDEX);
        }

        Buyer buyerToEdit = lastShownList.get(index.getZeroBased());

        Pair<Set<Tag>, String> tagInfo = super.getTags(buyerToEdit, editBuyerDescriptor);
        Set<Tag> editTags = tagInfo.getKey();

        Buyer editedBuyer = createdEditedBuyer(buyerToEdit, editBuyerDescriptor, editTags);

        if (!buyerToEdit.isSameBuyer(editedBuyer) && model.hasBuyer(editedBuyer)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUYER);
        }

        model.setBuyer(buyerToEdit, editedBuyer);
        model.updateFilteredBuyerList(PREDICATE_SHOW_ALL_BUYERS);
        String editTagDescription = tagInfo.getValue();
        String resultString = StringUtil.joinLines(String.format(MESSAGE_EDIT_BUYER_SUCCESS, editedBuyer),
                editTagDescription);
        return new CommandResult(resultString);
    }

    /**
     * Creates and returns a {@code Buyer} with the details of {@code buyerToEdit}
     * edited with {@code editBuyerDescriptor}.
     */
    private static Buyer createdEditedBuyer(Buyer buyerToEdit, EditBuyerDescriptor editBuyerDescriptor,
                                            Set<Tag> editedTags) {
        assert buyerToEdit != null;

        Name updatedName = editBuyerDescriptor.getName().orElse(buyerToEdit.getName());

        Phone updatedPhone = editBuyerDescriptor.getPhone()
                .orElse(buyerToEdit.getPhone());
        Email updatedEmail = editBuyerDescriptor.getEmail()
                .orElse(buyerToEdit.getEmail());
        Price updatedPrice = editBuyerDescriptor.getMaxPrice().orElse(buyerToEdit.getPrice());

        return new Buyer(updatedName, updatedPhone, updatedEmail, updatedPrice, editedTags);
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

    public static class EditBuyerDescriptor extends EditTaggableDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Price maxPrice;

        public EditBuyerDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditBuyerDescriptor(EditBuyerDescriptor toCopy) {
            super(toCopy);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setMaxPrice(toCopy.maxPrice);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, maxPrice) || super.isAnyFieldEdited();
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

            return super.equals(e)
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getMaxPrice().equals(e.getMaxPrice());
        }
    }
}
