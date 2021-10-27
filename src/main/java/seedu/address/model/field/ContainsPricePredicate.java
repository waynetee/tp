package seedu.address.model.field;

import java.util.function.Predicate;

import seedu.address.model.property.Pricable;

/**
 * Tests that a {@code Pricable}'s {@code Price}s is within the {@code minPrice} and {@code maxPrice}.
 */
public class ContainsPricePredicate<T extends Pricable> implements Predicate<T> {
    private final Price minPrice;
    private final Price maxPrice;

    /**
     * Constructs an empty predicate. Initialises minPrice and maxPrice to {@code null}.
     */
    public ContainsPricePredicate() {
        minPrice = null;
        maxPrice = null;
    }

    /**
     * Construct a predicate with the given {@code minPrice} and {@code maxPrice}.
     * {@code minPrice} and {@code maxPrice} may be {@code null}.
     */
    public ContainsPricePredicate(Price minPrice, Price maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean test(T pricable) {
        boolean isGreaterThanOrEqualToMinPrice = true;
        boolean isLessThanOrEqualToMaxPrice = true;
        if (minPrice != null) {
            isGreaterThanOrEqualToMinPrice = minPrice.isLessThanOrEqualTo(pricable.getPrice());
        }

        if (maxPrice != null) {
            isLessThanOrEqualToMaxPrice = maxPrice.isGreaterThanOrEqualTo(pricable.getPrice());
        }

        return isGreaterThanOrEqualToMinPrice && isLessThanOrEqualToMaxPrice;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContainsPricePredicate)) {
            return false;
        }

        ContainsPricePredicate<?> otherPricePredicate = (ContainsPricePredicate<?>) other;

        return checkPriceEquality(minPrice, otherPricePredicate.minPrice)
                && checkPriceEquality(maxPrice, otherPricePredicate.maxPrice);
    }

    private boolean checkPriceEquality(Price price1, Price price2) {
        if (price1 == null && price2 == null) {
            return true;
        } else if (price1 == null || price2 == null) {
            return false;
        } else {
            return price1.equals(price2);
        }
    }
}
