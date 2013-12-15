package uk.co.epsilontechnologies.primer.domain;

import java.util.regex.Pattern;

/**
 * Regular Expression implementation of the matchable interface.
 * This provides the ability to match a regular expression against the request string.
 */
public class RegExMatchable implements Matchable {

    /**
     * Convenience static method for constructing a RegExMatchable
     * @param regex the regular expression to use
     * @return the RegExMatchable for the given regex string
     */
    public static Matchable regex(final String regex) {
        return new RegExMatchable(regex);
    }

    /**
     * The primed string value of the regular expression
     */
    private final String primedString;

    /**
     * Constructor for the RegEx Matcher
     * @param primedString the regular expression that is being primed
     */
    RegExMatchable(String primedString) {
        this.primedString = primedString;
    }

    /**
     * Matches the primed string (regular expression) against the given request string
     * @see Matchable#match(String)
     * @param requestString the request string to check
     * @return true if the regular expression matches, false otherwise
     */
    @Override
    public boolean match(final String requestString) {
        return primedString != null && Pattern.matches(primedString, requestString);
    }

}
