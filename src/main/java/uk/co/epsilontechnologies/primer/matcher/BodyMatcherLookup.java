package uk.co.epsilontechnologies.primer.matcher;

import java.util.HashMap;
import java.util.Map;

/**
 * Resolvers the appropriate 'matcher' for a HTTP request body, based on the Content-Type of the request.
 *
 * @author Shane Gibson
 */
public class BodyMatcherLookup {

    /**
     * The map of Content-Type to Matcher implementation
     */
    private final Map<String,Matcher<String,String>> bodyMatchers = new HashMap<String,Matcher<String,String>>();

    /**
     * The default Matcher implementation - for when no matcher exists for the content type
     */
    private final StringMatcher defaultBodyMatcher;

    /**
     * Constructor for the Body Matcher Lookup, with a String Matcher as the default
     */
    BodyMatcherLookup() {
        this(new StringMatcher());
    }

    /**
     * Constructor for the Body Matcher Lookup, configuring matchers for application/xml and application/json Content Types
     * @param defaultBodyMatcher the default body matcher for when no matcher exists for the Content-Type
     */
    public BodyMatcherLookup(final StringMatcher defaultBodyMatcher) {
        this.defaultBodyMatcher = defaultBodyMatcher;
        this.bodyMatchers.put("application/xml", new XmlBodyMatcher());
        this.bodyMatchers.put("application/json", new JsonBodyMatcher());
    }

    /**
     * Gets the matcher for the given content type
     * @param contentType the content type
     * @return the matcher for the given content type
     */
    public Matcher<String,String> getMatcher(final String contentType) {
        if (bodyMatchers.containsKey(contentType)) {
            return bodyMatchers.get(contentType);
        }
        return defaultBodyMatcher;
    }

}
