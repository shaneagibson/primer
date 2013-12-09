package uk.co.epsilontechnologies.primer.matcher;

import uk.co.epsilontechnologies.primer.domain.Matchable;

import java.util.Map;

/**
 * Matches the map key / values, using a Regular Expression String matcher for value comparisons.
 * Returns true if the requestMap matches all of the key / value pairs of the primedMap, otherwise false.
 *
 * @author Shane Gibson
 */
public class MapMatcher implements Matcher<Map<String,Matchable>,Map<String,String>> {

    /**
     * Matches the primed map against the request map
     * @param primedMap the primed map to match against
     * @param requestMap the request map to match
     * @return true if the request map contains all of the primed map keys and the corresponding values are equivalent, false otherwise
     */
    public boolean match(final Map<String,Matchable> primedMap, final Map<String,String> requestMap) {
        for (final String primedKey : primedMap.keySet()) {
            if (!(requestMap.containsKey(primedKey) && primedMap.get(primedKey).match(requestMap.get(primedKey)))) {
                return false;
            }
        }
        return true;
    }

}