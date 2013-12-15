package uk.co.epsilontechnologies.primer.matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.epsilontechnologies.primer.domain.HttpServletRequestWrapper;
import uk.co.epsilontechnologies.primer.domain.Request;

/**
 * Implementation for matching the http method of the primed request against actual requests
 *
 * @author Shane Gibson
 */
public class MethodMatcher implements Matcher<Request,HttpServletRequestWrapper> {

    /**
     * Logger to use for error / warn / debug logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodMatcher.class);

    /**
     * The string matcher to use when comparing the http method of the primed request against the actual requests
     */
    private final StringMatcher stringMatcher;

    /**
     * Constructor for the method matcher
     * @param stringMatcher the string matcher to use when comparing the http method
     */
    public MethodMatcher(final StringMatcher stringMatcher) {
        this.stringMatcher = stringMatcher;
    }

    /**
     * Matches the method of the request against the primed request method
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request method matches, false otherwise
     */
    @Override
    public boolean match(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = stringMatcher.match(primedRequest.getMethod(), requestWrapper.getMethod());
        if (!result) {
            LOGGER.debug("PRIMER :-- method does not match: primed '" + primedRequest.getMethod() + "' but was '" + requestWrapper.getMethod() + "'");
        }
        return result;
    }

}