package uk.co.epsilontechnologies.primer.matcher;

import java.util.regex.Pattern;

/**
 * Matches the String values, using a Regular Expression pattern matcher for comparisons.
 *
 * @author Shane Gibson
 */
public class StringMatcher implements Matcher<String,String> {

    /**
     * Returns true if the request string matches the primed string
     * @param primedString the primed string to compare
     * @param requestString the request string to compare against
     * @return true if the primed string is not null and matches the request string, false otherwise
     */
    @Override
    public boolean match(final String primedString, final String requestString) {
        return primedString != null && primedString.equals(requestString);
    }

}