package seedu.address.model.stats;

import javafx.collections.ObservableList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

public class HistogramStat implements Stat {

    private final ObservableList<Buyer> buyerList;
    private final ObservableList<Property> propertyList;

    public HistogramStat(ObservableList<Buyer> buyerList, ObservableList<Property> propertyList) {
        this.buyerList = buyerList;
        this.propertyList = propertyList;
    }

    public JFreeChart create() {
        double[] buyerPrices = buyerList.stream().mapToDouble(buyer -> (double) buyer.getMaxPrice().value).toArray();
        double[] propertyPrices = propertyList.stream().mapToDouble(property -> (double) property.getPrice().value).toArray();

        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("buyers", buyerPrices, 20);
        dataset.addSeries("properties", propertyPrices, 20);

        JFreeChart histogram = ChartFactory.createHistogram("JFreeChart HistogramStat",
                "Price", "Count", dataset);

        return histogram;
    }
}
