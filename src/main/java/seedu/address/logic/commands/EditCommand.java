package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javafx.util.Pair;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.property.Taggable;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing entity (property or buyer) in the address book.
 */
public abstract class EditCommand extends SimpleCommand {
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Overwrites the details of the property or buyer "
            + "identified by the index (must be a positive integer) used in the displayed property/buyer list.\n"
            + "Only tags with modifiers ta/ or td/ are added/deleted and not overwritten.\n"
            + "Parameters (property): property INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_SELLER + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[ "
                + "( "
                    + "[" + PREFIX_TAG + "TAG]..." + " | "
                    + "[" + PREFIX_ADD_TAG + "TAG_TO_ADD]... "
                    + "[" + PREFIX_DELETE_TAG + "TAG_TO_DELETE]..."
                + " )"
            + " ]"
            + "\n"
            + "Parameters (buyer): buyer INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_PRICE + "BUDGET] "
            + "[" + PREFIX_TAG + "TAG]..."
            + "\n"
            + "Example: " + COMMAND_WORD + " property 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PROPERTY_SUCCESS = "Edited Property: %1$s\n";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_RESET_TAG_TOGETHER_WITH_MODIFY_TAG = "Tags can either be reset or modified.\n"
            + "You cannot do both at the same time.\n"
            + "An edit command can either have [t/TAG]... or [ta/TAG_TO_ADD]... [td/TAG_TO_DELETE]...";
    public static final String MESSAGE_DUPLICATE_ADD_AND_DELETE_TAG = "A tag cannot be both added and deleted.";
    public static final String EXPECTED_PREAMBLE = "(property | buyer) INDEX";


    protected Pair<Set<Tag>, String> getTags(Taggable taggableToEdit,
                                             EditCommand.EditTaggableDescriptor editTaggableDescriptor) {
        String messageEditTaggableTags = "";
        Set<Tag> originalTags = editTaggableDescriptor.getTags().orElse(taggableToEdit.getTags());
        Set<Tag> tagsToAdd = editTaggableDescriptor.getTagsToAdd().orElse(Collections.emptySet());
        Set<Tag> tagsToDelete = editTaggableDescriptor.getTagsToDelete().orElse(Collections.emptySet());

        Set<Tag> tagsAlreadyPresent = new HashSet<>(tagsToAdd);
        tagsAlreadyPresent.retainAll(originalTags);
        if (!tagsAlreadyPresent.isEmpty()) {
            messageEditTaggableTags += "These tags were already present:\n";
            for (Tag t : tagsAlreadyPresent) {
                messageEditTaggableTags += t + " ";
            }
            messageEditTaggableTags += "\n";
        }

        Set<Tag> tagsAlreadyAbsent = new HashSet<>(tagsToDelete);
        tagsAlreadyAbsent.removeAll(originalTags);
        if (!tagsAlreadyAbsent.isEmpty()) {
            messageEditTaggableTags += "These tags were not present:\n";
            for (Tag t : tagsAlreadyAbsent) {
                messageEditTaggableTags += t + " ";
            }
            messageEditTaggableTags += "\n";
        }

        Set<Tag> mergedSet = new HashSet<>(originalTags);
        mergedSet.removeAll(tagsToDelete);
        mergedSet.addAll(tagsToAdd);

        Set<Tag> editedTags = Collections.unmodifiableSet(mergedSet);
        return new Pair<>(editedTags, messageEditTaggableTags);
    }

    public static class EditTaggableDescriptor {
        private Set<Tag> tags;
        private Set<Tag> tagsToAdd;
        private Set<Tag> tagsToDelete;

        public EditTaggableDescriptor() {}

        /**
         * Copy constructor.
         * A defense copy of {@code tags}, {@tagsToAdd} and {@tagsToSDelete} is used internally.
         */
        public EditTaggableDescriptor(EditTaggableDescriptor toCopy) {
            setTags(toCopy.tags);
            setTagsToAdd(toCopy.tagsToAdd);
            setTagsToDelete(toCopy.tagsToDelete);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(tags, tagsToAdd, tagsToDelete);
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
            if (!(other instanceof EditCommand.EditTaggableDescriptor)) {
                return false;
            }

            // state check
            EditCommand.EditTaggableDescriptor e = (EditCommand.EditTaggableDescriptor) other;

            return getTags().equals(e.getTags())
                    && getTagsToAdd().equals(e.getTagsToAdd())
                    && getTagsToDelete().equals(e.getTagsToDelete());
        }
    }
}
