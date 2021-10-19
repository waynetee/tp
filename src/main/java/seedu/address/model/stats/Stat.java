package seedu.address.model.stats;

import org.jfree.chart.JFreeChart;

import seedu.address.ui.UiElement;

public interface Stat extends UiElement {

    @Override
    JFreeChart create();
}
