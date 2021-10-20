package seedu.address.model.property;

import seedu.address.model.field.Price;

public interface PricedListable extends Listable {

    /**
     * For statistics classes to get prices from all Listables.
     */
    Price getPrice();

}
