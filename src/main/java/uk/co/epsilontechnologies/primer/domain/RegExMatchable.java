package uk.co.epsilontechnologies.primer.domain;

import java.util.regex.Pattern;

public class RegExMatchable extends Matchable {

    public static Matchable regex(final String regex) {
        return new RegExMatchable(regex);
    }

    private final String primedString;

    RegExMatchable(String primedString) {
        this.primedString = primedString;
    }

    @Override
    public boolean match(final String requestString) {
        return primedString != null && Pattern.matches(primedString, requestString);
    }

}
