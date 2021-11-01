package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.property.Match;

/**
 * Panel containing the list of matches.
 */
public class MatchListPanel extends UiPart<Region> {
    private static final String FXML = "MatchListPanel.fxml";

    @FXML
    private ListView<Match> matchListView;

    /**
     * Creates a {@code MatchListPanel} with the given {@code ObservableList}.
     */
    public MatchListPanel(ObservableList<Match> matchList) {
        super(FXML);
        matchListView.setItems(matchList);
        matchListView.setCellFactory(listView -> new MatchListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Match}
     */
    class MatchListViewCell extends ListCell<Match> {
        @Override
        protected void updateItem(Match match, boolean empty) {
            super.updateItem(match, empty);

            if (empty || match == null) {
                setGraphic(null);
                setText(null);
            } else {
                MatchCard card = new MatchCard(match, getIndex() + 1, matchListView.widthProperty());
                setGraphic(card.getRoot());
            }
        }
    }

}
