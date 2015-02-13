package uk.co.epsilontechnologies.primer.domain;

public class StringMatchable implements Matchable {

    public static Matchable eq(final String string) {
        return new StringMatchable(string);
    }

    public static Matchable any() {
        return new StringMatchable(null);
    }

    public static Matchable empty() {
        return new StringMatchable("");
    }

    private final String primedString;

    StringMatchable(final String primedString) {
        this.primedString = primedString;
    }

    @Override
    public boolean match(final String requestString) {
        return primedString == null || primedString.equals(requestString);
    }

    @Override
    public String toString() {
        return this.primedString;
    }

}