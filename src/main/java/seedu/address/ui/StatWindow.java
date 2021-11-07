package seedu.address.ui;

import java.util.logging.Logger;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;

import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.stats.Stat;

/**
 * Controller for the statistics page.
 */
public class StatWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(StatWindow.class);
    private static final String FXML = "StatWindow.fxml";

    private Stat stat;

    /**
     * Creates a new StatWindow.
     * @param root Stage to use as the root of the HelpWindow.
     * @param stat Stat that creates the necessary JFreeChart.
     */
    public StatWindow(Stage root, Stat stat) {
        super(FXML, root);
        this.stat = stat;
    }

    /**
     * Creates a new StatWindow with an empty Stage.
     */
    public StatWindow() {
        super(FXML, new Stage());
    }

    public void setStat(Stat stat) {
        this.stat = stat;
        JFreeChart chart = stat.create();
        ChartViewer viewer = new ChartViewer(chart);
        getRoot().setScene(new Scene(viewer));
        getRoot().setTitle("Price Chart");
        getRoot().setWidth(600);
        getRoot().setHeight(480);
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
