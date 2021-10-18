package seedu.address.commons.util;

/**
 * Mixin interface that provides helpers to {@code Comparable} objects.
 * Note: While this may not be the intended usage of interfaces in Java, mixins are still
 * extremely helpful at times.
 *
 * @param <T> Type of the {@code Comparable} object.
 */
public interface ComparerMixin<T extends Comparable<T>> extends Comparable<T> {
    default boolean isGreaterThanOrEqualTo(T other) {
        return compareTo(other) >= 0;
    }

    default boolean isGreaterThan(T other) {
        return compareTo(other) > 0;
    }

    default boolean isLessThanOrEqualTo(T other) {
        return compareTo(other) <= 0;
    }

    default boolean isLessThan(T other) {
        return compareTo(other) < 0;
    }
}
