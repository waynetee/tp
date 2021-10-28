package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     *
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Filters nonempty strings from the given strings and concatenates them with newline.
     *
     * @param lines Lines to be joined.
     * @return Concatenated string.
     */
    public static String joinLines(String... lines) {
        StringBuilder builder = new StringBuilder();
        for (String s : lines) {
            if (s.length() > 0) {
                builder.append(s.trim());
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * Removes all trailing whitespace of the input space and compresses consecutive spaces between words.
     *
     * @param s String to be processed.
     * @return Processed string.
     */
    public static String compressWhitespace(String s) {
        requireNonNull(s);
        return s.trim().replaceAll(" +", " ");
    }

    /**
     * Capitalizes the first letter of each word delimited by space, and sets all other letters to lowercase.
     *
     * @param s String to be processed.
     * @return Processed string.
     */
    public static String startCaseSentence(String s) {
        requireNonNull(s);
        List<String> list = Arrays.stream(s.split(" "))
                .map(StringUtil::startCase)
                .collect(Collectors.toList());
        return String.join(" ", list);
    }

    /**
     * Capitalizes the first letter of the given string and sets all other letters to lowercase.
     *
     * @param s String to be processed.
     * @return Processed string.
     */
    public static String startCase(String s) {
        requireNonNull(s);
        if (s.length() == 0) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    //@@author sunjc826-reused
    //Reused from https://stackoverflow.com/questions/2255500

    /**
     * Returns a given string repeated for the specified number of times.
     *
     * @param count Number of times a string is to be repeated.
     * @param with  String to be repeated.
     * @return Repeated string.
     */
    public static String repeat(int count, String with) {
        requireNonNull(with);
        return new String(new char[count]).replace("\0", with);
    }
    //@@author

    //@@author sunjc826-reused
    //Reused from https://stackoverflow.com/questions/2800739

    /**
     * Returns a string from by stripping the given string of leading zeroes.
     *
     * @param s String to be processed.
     * @return Processed string.
     */
    public static String stripLeadingZeroes(String s) {
        requireNonNull(s);
        return s.replaceFirst("^0+(?!$)", "");
    }
    //@@author
}
