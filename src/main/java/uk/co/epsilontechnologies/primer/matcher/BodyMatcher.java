package uk.co.epsilontechnologies.primer.matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.epsilontechnologies.primer.domain.HttpServletRequestWrapper;
import uk.co.epsilontechnologies.primer.domain.Request;

/**
 * Implementation for matching the body of the primed request against actual requests
 *
 * @author Shane Gibson
 */
public class BodyMatcher implements Matcher<Request,HttpServletRequestWrapper> {

    /**
     * Logger to use for error / warn / debug logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BodyMatcher.class);

    /**
     * Matches the body of the request against the primed request body
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request body matches, false otherwise
     */
    @Override
    public boolean match(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = primedRequest.getBody().match(requestWrapper.getBody());
        if (!result) {
            LOGGER.debug("PRIMER :-- body does not match: primed '" + primedRequest.getBody() + "' but was '" + requestWrapper.getBody() + "'");
        }
        return result;
    }

}
