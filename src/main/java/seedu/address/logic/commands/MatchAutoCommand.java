package seedu.address.logic.commands;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Buyer;
import seedu.address.model.property.Match;
import seedu.address.model.property.Property;

/**
 * Matches as many properties and buyers together as possible.
 */
public class MatchAutoCommand extends MatchCommand {

    public static final String ARGUMENT_WORD = "auto";
    public static final String MESSAGE_NO_PROPERTIES_LISTED = "Error: No properties available for matching";
    public static final String MESSAGE_NO_BUYERS_LISTED = "Error: No buyers available for matching";
    public static final String MESSAGE_ONE_MATCH_FOUND = "1 match found!";
    public static final String MESSAGE_MATCHES_FOUND = "%1$d matches found!";

    private List<Property> properties; // Properties to match
    private List<Buyer> buyers; // Buyers to match
    private Set<Property> matchedProperties; // Properties that have been matched
    private Set<Buyer> matchedBuyers; // Buyers that have been matched
    private List<Match> matches; // Confirmed matches


    @Override
    public CommandResult execute(Model model) throws CommandException {
        initialise(model);
        runMatching();
        updateModel(model);
        return getCommandResult();
    }

    private void initialise(Model model) throws CommandException {
        if (model.getFilteredPropertyList().isEmpty()) {
            throw new CommandException(MESSAGE_NO_PROPERTIES_LISTED);
        }
        if (model.getFilteredBuyerList().isEmpty()) {
            throw new CommandException(MESSAGE_NO_BUYERS_LISTED);
        }
        properties = new ArrayList<>(model.getFilteredPropertyList());
        buyers = new ArrayList<>(model.getFilteredBuyerList());
        matchedProperties = new HashSet<>();
        matchedBuyers = new HashSet<>();
        matches = new ArrayList<>();
    }

    /**
     * Runs matching algorithm to find best matches.
     */
    private void runMatching() {
        List<Match> sortedMatchCandidates = getSortedMatchCandidates(); // Get all possible matches
        for (Match match : sortedMatchCandidates) {
            if (matchIsAvailable(match)) { // If buyer and property not taken
                confirmMatch(match);
            }
        }
    }

    private void updateModel(Model model) {
        model.setMatchList(matches);
    }

    private CommandResult getCommandResult() {
        String feedback;
        if (matches.size() == 1) {
            feedback = MESSAGE_ONE_MATCH_FOUND;
        } else {
            feedback = String.format(MESSAGE_MATCHES_FOUND, matches.size());
        }
        return new CommandResult(feedback, UiAction.SHOW_MATCHES);
    }

    /**
     * Returns possible matches in descending order of desirability.
     */
    private List<Match> getSortedMatchCandidates() {
        buyers.sort(Buyer.getPriceComparator());  // Prefer matching low budget buyers first
        properties.sort(Property.getPriceComparator().reversed()); // Prefer matching expensive properties first
        List<Match> matchCandidates = new ArrayList<>();
        for (Buyer buyer : buyers) {
            for (Property property : properties) {
                matchCandidates.add(new Match(property, buyer));
            }
        }
        matchCandidates.sort(Comparator.comparing(Match::getMatchScore).reversed()); // Sort by descending match score
        return matchCandidates;
    }

    /**
     * Returns true if the buyer and property in the match are both unmatched.
     */
    private boolean matchIsAvailable(Match match) {
        boolean buyerUnmatched = !matchedBuyers.contains(match.getBuyer());
        boolean propertyUnmatched = !matchedProperties.contains(match.getProperty());
        return buyerUnmatched && propertyUnmatched;
    }

    /**
     * Confirms a match.
     */
    private void confirmMatch(Match match) {
        matchedProperties.add(match.getProperty());
        matchedBuyers.add(match.getBuyer());
        matches.add(match);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof MatchAutoCommand;
    }
}
