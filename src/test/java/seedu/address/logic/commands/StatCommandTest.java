package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.StatCommand.ALL_WORD;
import static seedu.address.logic.commands.StatCommand.BUYER_WORD;
import static seedu.address.logic.commands.StatCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.StatCommand.PROPERTY_WORD;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for StatCommand.
 */
public class StatCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_statBuyer_success() {
        String success = String.format(MESSAGE_ARGUMENTS, BUYER_WORD);
        assertCommandSuccess(new StatCommand(true, false), model, success, expectedModel);
    }

    @Test
    public void execute_statProperty_success() {
        String success = String.format(MESSAGE_ARGUMENTS, PROPERTY_WORD);
        assertCommandSuccess(new StatCommand(false, true), model, success, expectedModel);
    }

    @Test
    public void execute_stat_success() {
        String success = String.format(MESSAGE_ARGUMENTS, ALL_WORD);
        assertCommandSuccess(new StatCommand(true, true), model, success, expectedModel);
    }

}
