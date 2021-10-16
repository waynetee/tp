package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandPreActionTest {
    @Test
    public void equals() {
        CommandPreAction commandPreAction = new CommandPreAction("export properties", true);

        // same values -> returns true
        assertTrue(commandPreAction.equals(new CommandPreAction("export properties", true)));

        // same object -> returns true
        assertTrue(commandPreAction.equals(commandPreAction));

        // null -> returns false
        assertFalse(commandPreAction.equals(null));

        // different types -> returns false
        assertFalse(commandPreAction.equals(0.5f));

        // different requiresFile value -> returns false
        assertFalse(commandPreAction.equals(new CommandPreAction()));

        // different fileDialogPrompt value -> returns false
        assertFalse(commandPreAction.equals(new CommandPreAction("different", true)));

        // different isFileSave value -> returns false
        assertFalse(commandPreAction.equals(new CommandPreAction("export properties", false)));
    }
}
