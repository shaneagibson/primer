package uk.co.epsilontechnologies.primer.matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.epsilontechnologies.primer.domain.HttpServletRequestWrapper;
import uk.co.epsilontechnologies.primer.domain.Request;

/**
 * Implementation for matching the URI of the primed request against actual requests
 *
 * @author Shane Gibson
 */
public class UriMatcher implements Matcher<Request,HttpServletRequestWrapper> {

    /**
     * Logger to use for error / warn / debug logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UriMatcher.class);

    /**
     * The context path for the primer server
     */
    private final String contextPath;

    /**
     * Constructor for the URI matcher
     * @param contextPath the context path for the primer server
     */
    public UriMatcher(final String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * Matches the URI of the request against the primed request URI
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request URI matches, false otherwise
     */
    @Override
    public boolean match(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = primedRequest.getURI().match(requestWrapper.getRequestURI().replaceFirst(contextPath, ""));
        if (!result) {
            LOGGER.debug("PRIMER :-- uri does not match: primed '" + contextPath + primedRequest.getURI() + "' but was '" + requestWrapper.getRequestURI() + "'");
        }
        return result;
    }

}