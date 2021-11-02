package seedu.address.ui;

import java.util.Comparator;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Match;
import seedu.address.model.property.Property;

/**
 * An UI component that displays information of a {@code Match}.
 */
public class MatchCard extends UiPart<Region> {

    private static final String FXML = "MatchListCard.fxml";
    private static final int WIDTH_PADDING = 20; // Horizontal padding around each half

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Match match;
    private ObservableDoubleValue maxWidth;

    @FXML
    private Label id;

    @FXML
    private Label propertyName;
    @FXML
    private Label propertyPrice;
    @FXML
    private Label propertyAddress;
    @FXML
    private Label propertySeller;
    @FXML
    private Label propertyPhone;
    @FXML
    private Label propertyEmail;
    @FXML
    private FlowPane propertyTags;

    @FXML
    private Label buyerName;
    @FXML
    private Label buyerPrice;
    @FXML
    private Label buyerPhone;
    @FXML
    private Label buyerEmail;
    @FXML
    private FlowPane buyerTags;

    /**
     * Creates a {@code MatchCard} with the given {@code Match} and index to display.
     *
     * @param match The match to display.
     * @param displayedIndex The index of the card.
     * @param parentWidthProperty The width of the parent container.
     */
    public MatchCard(Match match, int displayedIndex, ReadOnlyDoubleProperty parentWidthProperty) {
        super(FXML);
        this.match = match;
        id.setText(displayedIndex + ". ");

        // Set internal width of property/buyer half
        maxWidth = parentWidthProperty.divide(2).subtract(WIDTH_PADDING);
        initProperty(match.getProperty());
        initBuyer(match.getBuyer());
    }

    private void initProperty(Property property) {
        propertyName.setText(property.getName().fullName);
        propertyPrice.setText('$' + property.getPrice().toString());
        propertyAddress.setText(property.getAddress().value);
        propertySeller.setText(property.getSeller().getName().fullName);
        propertyPhone.setText(property.getSeller().getPhone().value);
        propertyEmail.setText(property.getSeller().getEmail().value);

        // Allow wrapping of labels
        propertyPhone.maxWidthProperty().bind(maxWidth);
        propertySeller.maxWidthProperty().bind(maxWidth);
        propertyEmail.maxWidthProperty().bind(maxWidth);

        property.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> propertyTags.getChildren().add(makeLabel(tag.tagName)));
    }

    private void initBuyer(Buyer buyer) {
        buyerName.setText(buyer.getName().fullName);
        buyerPrice.setText('$' + buyer.getPrice().toString());
        buyerPhone.setText(buyer.getPhone().value);
        buyerEmail.setText(buyer.getEmail().value);

        buyer.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> buyerTags.getChildren().add(makeLabel(tag.tagName)));
    }

    private Label makeLabel(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.maxWidthProperty().bind(maxWidth);
        return label;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatchCard)) {
            return false;
        }

        // state check
        MatchCard card = (MatchCard) other;
        return id.getText().equals(card.id.getText())
                && match.equals(card.match);
    }
}
