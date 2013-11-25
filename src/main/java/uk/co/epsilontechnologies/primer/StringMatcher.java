package uk.co.epsilontechnologies.primer;

import java.util.regex.Pattern;

public class StringMatcher implements Matcher<String,String> {

    @Override
    public boolean match(final String primedString, final String requestString) {
        return (primedString != null && primedString.equals(requestString)) || Pattern.matches(primedString, requestString);
    }

}