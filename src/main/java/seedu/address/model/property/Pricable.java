package seedu.address.model.property;

import seedu.address.model.field.Price;

public interface Pricable {

    /**
     * For statistics classes to get prices from all Listables.
     */
    Price getPrice();

}
