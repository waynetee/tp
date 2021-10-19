package seedu.address.model;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.data.statistics.HistogramDataset;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Property;

public class StatView extends Application {

    private final ObservableList<Buyer> buyerList;
    private final ObservableList<Property> propertyList;

    public StatView(ObservableList<Buyer> buyerList, ObservableList<Property> propertyList) {
        this.buyerList = buyerList;
        this.propertyList = propertyList;
    }

    public JFreeChart createHistogram() {
        double[] buyerPrices = buyerList.stream().mapToDouble(buyer -> (double) buyer.getMaxPrice().value).toArray();
        double[] propertyPrices = propertyList.stream().mapToDouble(property -> (double) property.getPrice().value).toArray();

        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("buyers", buyerPrices, 20);
        dataset.addSeries("properties", propertyPrices, 20);

        JFreeChart histogram = ChartFactory.createHistogram("JFreeChart Histogram",
                "Price", "Count", dataset);

        return histogram;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ChartViewer viewer = new ChartViewer(createHistogram());
        stage.setScene(new Scene(viewer));
        stage.setTitle("JFreeChart: Histogram");
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();
    }
}
