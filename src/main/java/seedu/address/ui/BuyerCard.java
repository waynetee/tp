package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.property.Buyer;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Buyer}.
 */
public class BuyerCard extends UiPart<Region> {

    private static final String FXML = "BuyerListCard.fxml";
    private static final int WIDTH_PADDING = 40; // Horizontal padding around cell

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Buyer buyer;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label price;

    /**
     * Creates a {@code BuyerCard} with the given {@code Buyer} and index to display.
     *
     * @param buyer The buyer to display.
     * @param displayedIndex The index of the card.
     * @param parentWidthProperty The width of the parent container.
     */
    public BuyerCard(Buyer buyer, int displayedIndex, ReadOnlyDoubleProperty parentWidthProperty) {
        super(FXML);
        this.buyer = buyer;
        id.setText(displayedIndex + ". ");
        name.setText(buyer.getName().fullName);
        price.setText('$' + buyer.getPrice().toString());
        phone.setText(buyer.getPhone().value);
        email.setText(buyer.getEmail().value);

        // Allow wrapping of labels
        ObservableDoubleValue maxWidth = parentWidthProperty.subtract(WIDTH_PADDING);
        phone.maxWidthProperty().bind(maxWidth);
        email.maxWidthProperty().bind(maxWidth);

        List<Tag> tagList = new ArrayList<>(buyer.getTags());
        tagList.sort(Comparator.comparing(tag -> tag.tagName));
        for (Tag tag : tagList) {
            Label label = new Label(tag.tagName);
            label.setWrapText(true);
            label.maxWidthProperty().bind(maxWidth);
            tags.getChildren().add(label);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BuyerCard)) {
            return false;
        }

        // state check
        BuyerCard card = (BuyerCard) other;
        return id.getText().equals(card.id.getText())
                && buyer.equals(card.buyer);
    }
}
