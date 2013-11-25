package uk.co.epsilontechnologies.primer;

import java.util.Map;

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
