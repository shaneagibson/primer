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

    private final StringMatcher stringMatcher;

    private final MapMatcher mapMatcher;

    /**
     * Constructs the request matcher for the given context, using the regular expression string matcher, map matcher and body matcher lookup
     * @param contextPath the context path for the primed request
     */
    public RequestMatcher(final String contextPath) {
        this(contextPath, new StringMatcher(), new MapMatcher());
    }

    public RequestMatcher(
            final String contextPath,
            final StringMatcher stringMatcher,
            final MapMatcher mapMatcher) {
        this.contextPath = contextPath;
        this.stringMatcher = stringMatcher;
        this.mapMatcher = mapMatcher;
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
        final boolean result = mapMatcher.match(primedRequest.getHeaders(), requestWrapper.getHeadersAsMap());
        if (!result) {
            LOGGER.debug("PRIMER :-- headers do not match: primed '" + primedRequest.getHeaders() + "' but was '" + requestWrapper.getHeadersAsMap() + "'");
        }
        return result;
    }

    /**
     * Matches the cookies of the request against the primed request cookies
     * @param primedRequest the primed request
     * @param requestWrapper the actual request
     * @return false if the request cookies match, false otherwise
     */
    private boolean matchCookies(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final boolean result = mapMatcher.match(primedRequest.getCookies(), requestWrapper.getCookiesAsMap());
        if (!result) {
            LOGGER.debug("PRIMER :-- cookies do not match: primed '" + primedRequest.getCookies() + "' but was '" + requestWrapper.getCookiesAsMap() + "'");
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
        final boolean result = mapMatcher.match(primedRequest.getParameters(), requestWrapper.getParametersAsMap());
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
        final boolean result = primedRequest.getURI().match(requestWrapper.getRequestURI().replaceFirst(contextPath, ""));
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
        final boolean result = primedRequest.getBody().match(requestWrapper.getBody());
        if (!result) {
            LOGGER.debug("PRIMER :-- body does not match: primed '" + primedRequest.getBody() + "' but was '" + requestWrapper.getBody() + "'");
        }
        return result;
    }

}