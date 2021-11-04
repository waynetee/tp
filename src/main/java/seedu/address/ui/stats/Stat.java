package seedu.address.ui.stats;

import org.jfree.chart.JFreeChart;

import seedu.address.ui.UiElement;

/**
 * Any class that holds statistics elements to be displayed in UI.
 */
public interface Stat extends UiElement {

    @Override
    JFreeChart create();
}
