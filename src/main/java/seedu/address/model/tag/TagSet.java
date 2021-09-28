package seedu.address.model.tag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TagSet {
    /**
     * Combines the results of removing {@code tagsToDelete} from {@code originalTags} adding {@code tagsToAdd}.
     * @param originalTags Original collection of tags.
     * @param tagsToAdd A collection of tags to be added.
     * @param tagsToDelete A collection of tags to be removed from {@code originalTags}.
     * @return
     */
    public static Set<Tag> mergeAndRemove(Collection<Tag> originalTags, Collection<Tag> tagsToAdd,
                                          Collection<Tag> tagsToDelete) {
        Set<Tag> mergedSet = new HashSet<>();
        for (Tag t : originalTags) {
            if (tagsToDelete.contains(t)) {
                continue;
            }

            mergedSet.add(t);
        }

        mergedSet.addAll(tagsToAdd);
        return Collections.unmodifiableSet(mergedSet);
    }
}
