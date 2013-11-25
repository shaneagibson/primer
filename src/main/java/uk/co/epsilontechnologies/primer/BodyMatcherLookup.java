package uk.co.epsilontechnologies.primer;

import java.util.HashMap;
import java.util.Map;

public class BodyMatcherLookup {

    private final Map<String,Matcher<String,String>> bodyMatchers = new HashMap<String,Matcher<String,String>>();
    private final StringMatcher defaultBodyMatcher;

    BodyMatcherLookup() {
        this(new StringMatcher());
    }

    public BodyMatcherLookup(final StringMatcher defaultBodyMatcher) {
        this.defaultBodyMatcher = defaultBodyMatcher;
        this.bodyMatchers.put("application/xml", new XmlBodyMatcher());
        this.bodyMatchers.put("application/json", new JsonBodyMatcher());
    }

    public Matcher<String,String> getMatcher(final String contentType) {
        if (bodyMatchers.containsKey(contentType)) {
            return bodyMatchers.get(contentType);
        }
        return defaultBodyMatcher;
    }

}