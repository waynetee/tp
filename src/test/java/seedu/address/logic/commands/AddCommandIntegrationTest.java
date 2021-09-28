package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProperties.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.Property;
import seedu.address.testutil.PropertyBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newProperty_success() {
        Property validProperty = new PropertyBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addProperty(validProperty);

        assertCommandSuccess(new AddCommand(validProperty), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validProperty), expectedModel);
    }

    @Test
    public void execute_duplicateProperty_throwsCommandException() {
        Property propertyInList = model.getAddressBook().getPropertyList().get(0);
        assertCommandFailure(new AddCommand(propertyInList), model, AddCommand.MESSAGE_DUPLICATE_PROPERTY);
    }

}
