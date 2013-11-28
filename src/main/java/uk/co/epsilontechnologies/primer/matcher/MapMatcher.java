package uk.co.epsilontechnologies.primer.matcher;

import java.util.Map;

/**
 * Matches the map key / values, using a Regular Expression String matcher for value comparisons.
 * Returns true if the requestMap matches all of the key / value pairs of the primedMap, otherwise false.
 *
 * @author Shane Gibson
 */
public class MapMatcher implements Matcher<Map<String,String>,Map<String,String>> {

    /**
     * The string matcher to use when comparing values of the map
     */
    private final StringMatcher stringMatcher;

    /**
     * Constructs the map matcher using a String Matcher for comparison of values
     */
    MapMatcher() {
        this(new StringMatcher());
    }

    /**
     * Constructs the map matcher using the given String Matcher for comparison of values
     * @param stringMatcher
     */
    MapMatcher(final StringMatcher stringMatcher) {
        this.stringMatcher = stringMatcher;
    }

    /**
     * Matches the primed map against the request map
     * @param primedMap the primed map to match against
     * @param requestMap the request map to match
     * @return true if the request map contains all of the primed map keys and the corresponding values are equivalent, false otherwise
     */
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
