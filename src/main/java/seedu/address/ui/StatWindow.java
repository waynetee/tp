package seedu.address.ui;

import javafx.stage.Stage;

/**
 * Controller for the statistics page.
 */
public class StatWindow extends UiPart<Stage> {
    private static final String FXML = "StatWindow.fxml";

    public StatWindow(Stage root) {
        super(FXML, root);
    }

}
