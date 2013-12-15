package uk.co.epsilontechnologies.primer.matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.epsilontechnologies.primer.domain.HttpServletRequestWrapper;
import uk.co.epsilontechnologies.primer.domain.Request;

/**
 * Implementation for matching the header attributes of the primed request against actual requests
 *
 * @author Shane Gibson
 */
public class HeadersMatcher implements Matcher<Request,HttpServletRequestWrapper> {

    /**
     * Logger to use for error / warn / debug logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HeadersMatcher.class);

    /**
     * The map matcher to use for matching the header attributes of the request against the cookies that are primed
     */
    private final MapMatcher mapMatcher;

    /**
     * Constructor for the headers matcher
     * @param mapMatcher the map matcher to use
     */
    public HeadersMatcher(final MapMatcher mapMatcher) {
        this.mapMatcher = mapMatcher;
    }

    /**
     * Matches the headers of the request against the primed request headers
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request headers match, false otherwise
     */
    @Override
    public boolean match(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = mapMatcher.match(primedRequest.getHeaders(), requestWrapper.getHeadersAsMap());
        if (!result) {
            LOGGER.debug("PRIMER :-- headers do not match: primed '" + primedRequest.getHeaders() + "' but was '" + requestWrapper.getHeadersAsMap() + "'");
        }
        return result;
    }

}