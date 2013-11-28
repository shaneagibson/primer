package uk.co.epsilontechnologies.primer;

import java.util.regex.Pattern;

/**
 * Matches the String values, using a Regular Expression pattern matcher for comparisons.
 *
 * @author Shane Gibson
 */
public class StringMatcher implements Matcher<String,String> {

    @Override
    public boolean match(final String primedString, final String requestString) {
        return (primedString != null && primedString.equals(requestString)) || Pattern.matches(primedString, requestString);
    }

}