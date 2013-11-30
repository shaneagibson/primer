package uk.co.epsilontechnologies.primer.matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.epsilontechnologies.primer.domain.HttpServletRequestWrapper;
import uk.co.epsilontechnologies.primer.domain.Request;

/**
 * Matches the HTTP Servlet Request against the attributes of the given primed Request.
 *
 * @author Shane Gibson
 */
public class RequestMatcher implements Matcher<Request,HttpServletRequestWrapper> {

    /**
     * Logger to use for error / warn / debug logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestMatcher.class);

    /**
     * The context path of the request being matched
     */
    private final String contextPath;

    /**
     * The matcher to use for string comparisons
     */
    private final StringMatcher stringMatcher;

    /**
     * The matcher to use for map comparisons
     */
    private final MapMatcher mapMatcher;

    /**
     * The lookup for which matcher to use for body comparisons
     */
    private final BodyMatcherLookup bodyMatcherLookup;

    /**
     * Constructs the request matcher for the given context, using the regular expression string matcher, map matcher and body matcher lookup
     * @param contextPath the context path for the primed request
     */
    public RequestMatcher(final String contextPath) {
        this(contextPath, new StringMatcher(), new MapMatcher(), new BodyMatcherLookup());
    }

    /**
     * Constructs the request matcher for the given context, string matcher, map matcher and body matcher lookup
     * @param contextPath the context path of the primed request
     * @param stringMatcher the string matcher to use for comparing the method and uri
     * @param mapMatcher the map matcher to use for comparing headers and parameters
     * @param bodyMatcherLookup the body matcher lookup for resolving the body matcher
     */
    public RequestMatcher(
            final String contextPath,
            final StringMatcher stringMatcher,
            final MapMatcher mapMatcher,
            final BodyMatcherLookup bodyMatcherLookup) {
        this.contextPath = contextPath;
        this.stringMatcher = stringMatcher;
        this.mapMatcher = mapMatcher;
        this.bodyMatcherLookup = bodyMatcherLookup;
    }

    /**
     * Determines if the given HTTP servlet request matches the primed request
     * @param primedRequest the primed request to match against
     * @param requestWrapper the HTTP servlet request to check
     * @return true if the request matches the primed request, false otherwise
     */
    @Override
    public boolean match(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        return
                matchBody(primedRequest, requestWrapper) &&
                matchUri(primedRequest, requestWrapper) &&
                matchMethod(primedRequest, requestWrapper) &&
                matchParameters(primedRequest, requestWrapper) &&
                matchHeaders(primedRequest, requestWrapper);
    }

    /**
     * Matches the headers of the request against the primed request headers
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request headers match, false otherwise
     */
    private boolean matchHeaders(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = mapMatcher.match(primedRequest.getHeaders().get(), requestWrapper.getHeadersAsMap());
        if (!result) {
            LOGGER.debug("PRIMER :-- headers do not match: primed '" + primedRequest.getHeaders() + "' but was '" + requestWrapper.getHeadersAsMap() + "'");
        }
        return result;
    }

    /**
     * Matches the parameters of the request against the primed request parameters
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request parameters match, false otherwise
     */
    private boolean matchParameters(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = mapMatcher.match(primedRequest.getParameters().get(), requestWrapper.getParametersAsMap());
        if (!result) {
            LOGGER.debug("PRIMER :-- parameters do not match: primed '" + primedRequest.getParameters() + "' but was '" + requestWrapper.getParametersAsMap() + "'");
        }
        return result;
    }

    /**
     * Matches the method of the request against the primed request method
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request method matches, false otherwise
     */
    private boolean matchMethod(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = stringMatcher.match(primedRequest.getMethod(), requestWrapper.getMethod());
        if (!result) {
            LOGGER.debug("PRIMER :-- method does not match: primed '" + primedRequest.getMethod() + "' but was '" + requestWrapper.getMethod() + "'");
        }
        return result;
    }

    /**
     * Matches the URI of the request against the primed request URI
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request URI matches, false otherwise
     */
    private boolean matchUri(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = stringMatcher.match(contextPath + primedRequest.getURI(), requestWrapper.getRequestURI());
        if (!result) {
            LOGGER.debug("PRIMER :-- uri does not match: primed '" + contextPath + primedRequest.getURI() + "' but was '" + requestWrapper.getRequestURI() + "'");
        }
        return result;
    }

    /**
     * Matches the body of the request against the primed request body
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request body matches, false otherwise
     */
    private boolean matchBody(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final Matcher<String,String> bodyMatcher = bodyMatcherLookup.getMatcher(requestWrapper.getContentType());
        final boolean result = bodyMatcher.match(primedRequest.getBody(), requestWrapper.getBody());
        if (!result) {
            LOGGER.debug("PRIMER :-- body does not match: primed '" + primedRequest.getBody() + "' but was '" + requestWrapper.getBody() + "'");
        }
        return result;
    }

}