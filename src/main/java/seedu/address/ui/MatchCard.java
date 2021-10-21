package seedu.address.ui;

import java.util.Comparator;

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

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Match match;


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
     */
    public MatchCard(Match match, int displayedIndex) {
        super(FXML);
        this.match = match;
        id.setText(displayedIndex + ". ");
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
        property.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> propertyTags.getChildren().add(new Label(tag.tagName)));
    }

    private void initBuyer(Buyer buyer) {
        buyerName.setText(buyer.getName().fullName);
        buyerPrice.setText('$' + buyer.getPrice().toString());
        buyerPhone.setText(buyer.getPhone().value);
        buyerEmail.setText(buyer.getEmail().value);
        buyer.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> buyerTags.getChildren().add(new Label(tag.tagName)));
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
