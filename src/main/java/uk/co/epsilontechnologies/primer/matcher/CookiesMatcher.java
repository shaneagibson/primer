package uk.co.epsilontechnologies.primer.matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.epsilontechnologies.primer.domain.HttpServletRequestWrapper;
import uk.co.epsilontechnologies.primer.domain.Request;

/**
 * Implementation for matching the cookies of the primed request against actual requests
 *
 * @author Shane Gibson
 */
public class CookiesMatcher implements Matcher<Request,HttpServletRequestWrapper> {

    /**
     * Logger to use for error / warn / debug logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CookiesMatcher.class);

    /**
     * The map matcher to use for matching the cookies of the request against the cookies that are primed
     */
    private final MapMatcher mapMatcher;

    /**
     * Constructor for the cookie matcher
     * @param mapMatcher the map matcher to use
     */
    public CookiesMatcher(final MapMatcher mapMatcher) {
        this.mapMatcher = mapMatcher;
    }

    /**
     * Matches the cookies of the request against the primed request headers
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request cookies match, false otherwise
     */
    @Override
    public boolean match(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = mapMatcher.match(primedRequest.getCookies(), requestWrapper.getCookiesAsMap());
        if (!result) {
            LOGGER.debug("PRIMER :-- cookies do not match: primed '" + primedRequest.getCookies() + "' but was '" + requestWrapper.getCookiesAsMap() + "'");
        }
        return result;
    }

}