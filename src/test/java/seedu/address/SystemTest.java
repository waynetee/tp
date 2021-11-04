package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiAction;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;

public class SystemTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "SystemTest");
    private static final Path INPUT_PATH = TEST_DATA_FOLDER.resolve("input.txt");
    private static final Path ACTUAL_OUTPUT_PATH = TEST_DATA_FOLDER.resolve("actual_output.txt");
    private static final Path EXPECTED_OUTPUT_PATH = TEST_DATA_FOLDER.resolve("expected_output.txt");


    @Test
    public void runSystemTest() throws IOException {
        Logic logic = createApp();
        List<String> log = runCommands(logic); // Run commands from input.txt
        writeOutput(log); // Writes to actual_output.txt
        assertOutputMatchesExpected(); // Asserts actual_output.txt matches expected_output.txt
        assertDataRestored(logic); // Asserts data saving and loading works
    }

    private Logic createApp() {
        MainApp app = new MainApp();
        app.initBackend(null);
        return app.getLogic();
    }

    private List<String> runCommands(Logic logic) throws IOException {
        List<String> commands = Files.readAllLines(INPUT_PATH);
        List<String> log = new ArrayList<>();
        boolean showingMatchView = false;
        for (String command : commands) {
            try {
                log.add(command);
                logic.validateCommand(command, showingMatchView);
                CommandResult result = logic.execute(command);
                showingMatchView = getShowingMatchView(showingMatchView, result.getUiAction());
                log.add(result.toString());
            } catch (CommandException | ParseException e) {
                log.add(e.getMessage());
            }
            log.add(logic.getFilteredPropertyList().toString());
            log.add(logic.getFilteredBuyerList().toString());
            log.add(logic.getMatchList().toString() + '\n');
        }
        return log;
    }

    private boolean getShowingMatchView(boolean previous, UiAction uiAction) {
        if (uiAction.equals(UiAction.SHOW_MATCHES)) {
            return true;
        }
        if (uiAction.equals(UiAction.SHOW_DEFAULT)) {
            return false;
        }
        return previous;
    }

    private void writeOutput(List<String> log) throws IOException {
        FileWriter writer = new FileWriter(ACTUAL_OUTPUT_PATH.toFile());
        for (String entry : log) {
            writer.write(entry + "\n");
        }
        writer.close();
    }

    private void assertOutputMatchesExpected() throws IOException {
        List<String> expected = Files.readAllLines(EXPECTED_OUTPUT_PATH);
        List<String> actual = Files.readAllLines(ACTUAL_OUTPUT_PATH);
        assertEquals(expected, actual);
    }

    private void assertDataRestored(Logic originalLogic) {
        ReadOnlyAddressBook original = originalLogic.getAddressBook();
        ReadOnlyAddressBook restored = createApp().getAddressBook();
        assertEquals(original.getPropertyList(), restored.getPropertyList());
        assertEquals(original.getBuyerList(), restored.getBuyerList());
    }

}
