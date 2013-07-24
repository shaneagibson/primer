package uk.co.epsilontechnologies.primer.client.jms.matcher;

public class RegExMatchers {

    private static final String ANY_LONG = "^-?\\d{1,19}$";

    public static String anyLong() {
        return ANY_LONG;
    }

}
