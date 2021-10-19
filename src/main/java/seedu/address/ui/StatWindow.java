package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jfree.chart.fx.ChartViewer;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.stats.Stat;

/**
 * Controller for the statistics page.
 */
public class StatWindow extends UiPart<Stage> {
    private final Stat stat;
    private static final Logger logger = LogsCenter.getLogger(StatWindow.class);
    private static final String FXML = "StatWindow.fxml";

    /**
     * Creates a new StatWindow.
     * @param root Stage to use as the root of the HelpWindow.
     * @param stat Stat that creates the necessary JFreeChart.
     */
    public StatWindow(Stage root, Stat stat) {
        super(FXML, root);
        this.stat = stat;
        ChartViewer viewer = new ChartViewer(stat.create());
        root.setScene(new Scene(viewer));
        root.setTitle("Prices");
        root.setWidth(600);
        root.setHeight(480);
    }

    /**
     * Creates a new StatWindow.
     * @param stat Stat that creates the necessary JFreeChart.
     */
    public StatWindow(Stat stat) {
        this(new Stage(), stat);
    }

    /**
     * Shows the stat window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing statistics.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the stat window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the stat window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the stat window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
