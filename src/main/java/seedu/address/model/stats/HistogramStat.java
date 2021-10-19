package seedu.address.model.stats;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

import javafx.collections.ObservableList;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

/**
 * Stat that creates a histogram of visible buyers, properties, or both.
 */
public class HistogramStat implements Stat {
    private static final int BIN_COUNT = 20;

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
     * Creates a histogram to displayed as a popup.
     * Shows either buyers, properties or both.
     *
     * @return price histogram.
     */
    public JFreeChart create() {
        double[] buyerPrices = buyerList.stream()
                .mapToDouble(buyer -> (double) buyer.getMaxPrice().value).toArray();
        double[] propertyPrices = propertyList.stream()
                .mapToDouble(property -> (double) property.getPrice().value).toArray();

        HistogramDataset dataset = new HistogramDataset();
        if (showBuyer) {
            dataset.addSeries("buyers", buyerPrices, BIN_COUNT);
        }

        if (showProperty) {
            dataset.addSeries("properties", propertyPrices, BIN_COUNT);
        }

        JFreeChart histogram = ChartFactory.createHistogram("Prices of " + titleArgs,
                "Price", "Count", dataset);

        return histogram;
    }
}
