package seedu.address.testutil;

import static seedu.address.testutil.TypicalBuyers.B_ALICE;
import static seedu.address.testutil.TypicalBuyers.B_BENSON;
import static seedu.address.testutil.TypicalBuyers.B_CARL;
import static seedu.address.testutil.TypicalProperties.P_ALICE;
import static seedu.address.testutil.TypicalProperties.P_BENSON;

import seedu.address.model.property.Match;

public class TypicalMatches {
    public static final Match M_P_ALICE_B_ALICE = new Match(P_ALICE, B_ALICE);
    public static final Match M_P_ALICE_B_BENSON = new Match(P_ALICE, B_BENSON);
    public static final Match M_P_ALICE_B_CARL = new Match(P_ALICE, B_CARL);
    public static final Match M_P_BENSON_B_BENSON = new Match(P_BENSON, B_BENSON);
    public static final Match M_P_BENSON_B_CARL = new Match(P_BENSON, B_CARL);

    private TypicalMatches() {
    } // prevents instantiation
}
