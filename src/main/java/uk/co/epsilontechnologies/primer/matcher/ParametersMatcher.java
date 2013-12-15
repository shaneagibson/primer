package uk.co.epsilontechnologies.primer.matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.epsilontechnologies.primer.domain.HttpServletRequestWrapper;
import uk.co.epsilontechnologies.primer.domain.Request;

/**
 * Implementation for matching the parameter attributes of the primed request against actual requests
 *
 * @author Shane Gibson
 */
public class ParametersMatcher implements Matcher<Request,HttpServletRequestWrapper> {

    /**
     * Logger to use for error / warn / debug logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParametersMatcher.class);

    /**
     * The map matcher to use for matching the parameters of the request against the parameters that are primed
     */
    private final MapMatcher mapMatcher;

    /**
     * Constructor for the parameters matcher
     * @param mapMatcher the map matcher to use
     */
    public ParametersMatcher(final MapMatcher mapMatcher) {
        this.mapMatcher = mapMatcher;
    }

    /**
     * Matches the parameters of the request against the primed request parameters
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request parameters match, false otherwise
     */
    @Override
    public boolean match(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = mapMatcher.match(primedRequest.getParameters(), requestWrapper.getParametersAsMap());
        if (!result) {
            LOGGER.debug("PRIMER :-- parameters do not match: primed '" + primedRequest.getParameters() + "' but was '" + requestWrapper.getParametersAsMap() + "'");
        }
        return result;
    }

}
