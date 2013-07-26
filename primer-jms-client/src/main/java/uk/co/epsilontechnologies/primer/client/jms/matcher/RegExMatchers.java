package uk.co.epsilontechnologies.primer.client.jms.matcher;

public final class RegExMatchers {

    private static final String ANY_LONG = "^-?\\d{1,19}$";

    private RegExMatchers() {
        super();
    }

    public static String anyLong() {
        return ANY_LONG;
    }

}
