package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Edits the details of an existing property in the address book.
 */
public abstract class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the property or buyer identified "
            + "by the index number used in the displayed property/buyer list. "
            + "Existing values will be overwritten by the input values.\n"
            + "with the exception of adding tags. "
            + "Added tags will be appended to the current tags. \n"
            + "Parameters: (property INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_SELLER + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "["
                + "("
                    + "[" + PREFIX_TAG + "TAG]..." + " | "
                    + "[" + PREFIX_ADD_TAG + "TAG_TO_ADD]..."
                    + "[" + PREFIX_DELETE_TAG + "TAG_TO_DELETE]..."
                + ")"
            + "]"
            + "\n"
            + "|"
            + "\n"
            + "buyer INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_MAX_PRICE + "MAX_PRICE] "
            + "[" + PREFIX_TAG + "TAG]..."
            + ")"
            + "\n"
            + "Example: " + COMMAND_WORD + " property 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PROPERTY_SUCCESS = "Edited Property: %1$s\n";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists in the address book.";
    public static final String MESSAGE_RESET_TAG_TOGETHER_WITH_MODIFY_TAG = "Tags can either be reset or modified.\n"
            + "You cannot do both at the same time.\n"
            + "An edit command can either have [t/TAG]... or [ta/TAG_TO_ADD]... [td/TAG_TO_DELETE]...";
    public static final String MESSAGE_DUPLICATE_ADD_AND_DELETE_TAG = "A tag cannot be both added and deleted.";










}
