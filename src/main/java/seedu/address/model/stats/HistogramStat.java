package seedu.address.model.stats;

import javafx.collections.ObservableList;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Pricable;
import seedu.address.model.property.Property;

/**
 * Stat that creates a histogram of visible buyers, properties, or both.
 */
public class HistogramStat implements Stat {
    private static final int BIN_COUNT = 10;
    private static final int MILLION = 1000000;
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
     * Returns a two element array containing the min in index 0, and max in index 1.
     */
    private double[] getGlobalMinMax() {
        double buyerMin = buyerList.stream()
                .min((x, y) -> x.getPrice().compareTo(y.getPrice())).map(p -> p.getPrice().value).get();
        double buyerMax = buyerList.stream()
                .max((x, y) -> x.getPrice().compareTo(y.getPrice())).map(p -> p.getPrice().value).get();
        double propertyMin = propertyList.stream()
                .min((x, y) -> x.getPrice().compareTo(y.getPrice())).map(p -> p.getPrice().value).get();
        double propertyMax = propertyList.stream()
                .max((x, y) -> x.getPrice().compareTo(y.getPrice())).map(p -> p.getPrice().value).get();
        double[] minmax = new double[2];
        minmax[0] = (int) (Math.min(buyerMin, propertyMin) / 100000) * 100000;
        minmax[1] = (int) (Math.max(buyerMax, propertyMax) / 100000 + 1) * 100000;
        return minmax;
    }

    /**
     * Returns the correct bin for this price.
     */
    private int getBin(double price, double min, double interval) {
        return (int) Math.min((price - min) / interval, BIN_COUNT - 1);
    }

    /**
     * Returns the label for the bin of this interval.
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
     *  Adds this list to the dataset.
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
        assert !buyerList.isEmpty() && !propertyList.isEmpty();
        double[] minmax = getGlobalMinMax();
        double min = minmax[0];
        double max = minmax[1];

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        addToDataSet(dataset, buyerList, "buyers", min, max);
        addToDataSet(dataset, propertyList, "properties", min, max);

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

        return chart;
    }
}
