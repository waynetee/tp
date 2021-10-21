package seedu.address.ui.stats;

import java.util.Optional;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javafx.collections.ObservableList;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Pricable;
import seedu.address.model.property.Property;

/**
 * Stat that creates a histogram of visible buyers, properties, or both.
 */
public class HistogramStat implements Stat {
    private static final int BIN_COUNT = 10;
    private static final int MILLION = 1000000;
    private static final int HUNDRED_THOUSAND = 100000;
    private static final int THOUSAND = 1000;

    private final ObservableList<Buyer> buyerList;
    private final ObservableList<Property> propertyList;
    private final boolean showBuyer;
    private final boolean showProperty;
    private final String titleArgs;

    /**
     * Constructor for price histograms.
     * @param buyerList Currently viewable list of buyers.
     * @param propertyList Currently viewable list of properties.
     */
    public HistogramStat(ObservableList<Buyer> buyerList, ObservableList<Property> propertyList,
                         boolean showBuyer, boolean showProperty, String titleArgs) {
        this.buyerList = buyerList;
        this.propertyList = propertyList;
        this.showBuyer = showBuyer;
        this.showProperty = showProperty;
        this.titleArgs = titleArgs;
    }

    /**
     * Helper function that returns the min or max in a list.
     * If the list is empty, returns Double.MAX_VALUE if isMin, otherwise returns Double.MIN_VALUE.
     * @param isMin true for minimum, false for maximum
     */
    private double getListMinMax(ObservableList<? extends Pricable> list, boolean isMin) {
        if (isMin) {
            Optional<Double> optMin = list.stream()
                    .min((x, y) -> x.getPrice().compareTo(y.getPrice())).map(p -> (double) p.getPrice().value);
            if (optMin.isPresent()) {
                return optMin.get();
            }
            return Double.MAX_VALUE;
        } else {
            Optional<Double> optMax = list.stream()
                    .max((x, y) -> x.getPrice().compareTo(y.getPrice())).map(p -> (double) p.getPrice().value);
            if (optMax.isPresent()) {
                return optMax.get();
            }
            return Double.MIN_VALUE;
        }
    }

    /**
     * Helper function that returns a two element array containing the min in index 0, and max in index 1.
     */
    private double[] getGlobalMinMax() {
        double buyerMin = getListMinMax(buyerList, true);
        double buyerMax = getListMinMax(buyerList, false);
        double propertyMin = getListMinMax(propertyList, true);
        double propertyMax = getListMinMax(propertyList, false);
        double[] minmax = new double[2];
        minmax[0] = (int) (Math.min(buyerMin, propertyMin) / HUNDRED_THOUSAND) * HUNDRED_THOUSAND;
        minmax[1] = (int) (Math.max(buyerMax, propertyMax) / HUNDRED_THOUSAND + 1) * HUNDRED_THOUSAND;
        return minmax;
    }

    /**
     * Helper function that returns the correct bin for this price.
     */
    private int getBin(double price, double min, double interval) {
        return (int) Math.min((price - min) / interval, BIN_COUNT - 1);
    }

    /**
     * Helper function that returns the label for the bin of this interval.
     */
    private String getRangeLabel(double price) {
        if (price >= MILLION) {
            price /= MILLION;
            return String.format("%.2fM", price);
        } else {
            price /= THOUSAND;
            return String.format("%dK", (long) price);
        }
    }

    /**
     * Adds this list to the dataset.
     */
    private void addToDataSet(DefaultCategoryDataset dataset,
                              ObservableList<? extends Pricable> list, String key,
                              double min, double max) {
        double interval = (max - min) / BIN_COUNT;
        double binVal = min;
        int[] bins = new int[BIN_COUNT];
        list.stream()
                .forEach(x -> bins[getBin(x.getPrice().value, min, interval)]++);
        for (int i = 0; i < bins.length; i++, binVal += interval) {
            String label = String.format("%s -- %s", getRangeLabel(binVal), getRangeLabel(binVal + interval));
            dataset.addValue(bins[i], key, label);
        }
    }

    /**
     * Creates a histogram to displayed as a popup.
     * Shows either buyers, properties or both.
     *
     * @return price histogram.
     */
    public JFreeChart create() {

        double[] minmax = getGlobalMinMax();
        double min = minmax[0];
        double max = minmax[1];

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (showBuyer && !buyerList.isEmpty()) {
            addToDataSet(dataset, buyerList, "buyers", min, max);
        }

        if (showProperty && !propertyList.isEmpty()) {
            addToDataSet(dataset, propertyList, "properties", min, max);
        }

        final CategoryPlot plot = new CategoryPlot();
        plot.setDomainAxis(new CategoryAxis("Prices ($)"));
        plot.setRangeAxis(new NumberAxis("Count"));
        plot.setDomainGridlinesVisible(true);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        BarRenderer.setDefaultShadowsVisible(false);
        final CategoryItemRenderer renderer = new BarRenderer();
        renderer.setDefaultItemLabelsVisible(true);
        plot.setDataset(dataset);
        plot.setRenderer(renderer);

        final JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("Prices of " + titleArgs);

        return chart;
    }
}
