package uk.co.epsilontechnologies.primer.matcher;

import java.util.Map;

/**
 * Matches the map key / values, using a Regular Expression String matcher for value comparisons.
 * Returns true if the requestMap matches all of the key / value pairs of the primedMap, otherwise false.
 *
 * @author Shane Gibson
 */
public class MapMatcher implements Matcher<Map<String,String>,Map<String,String>> {

    private final StringMatcher stringMatcher;

    MapMatcher() {
        this(new StringMatcher());
    }

    MapMatcher(final StringMatcher stringMatcher) {
        this.stringMatcher = stringMatcher;
    }

    @Override
    public boolean match(final Map<String, String> primedMap, final Map<String, String> requestMap) {
        for (final String primedKey : primedMap.keySet()) {
            if (!(requestMap.containsKey(primedKey) && stringMatcher.match(primedMap.get(primedKey), requestMap.get(primedKey)))) {
                return false;
            }
        }
        return true;
    }

}
