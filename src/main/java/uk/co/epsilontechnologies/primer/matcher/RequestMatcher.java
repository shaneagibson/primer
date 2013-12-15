package uk.co.epsilontechnologies.primer.matcher;

import uk.co.epsilontechnologies.primer.domain.HttpServletRequestWrapper;
import uk.co.epsilontechnologies.primer.domain.Request;

/**
 * Matches the HTTP Servlet Request against the attributes of the given primed Request.
 *
 * @author Shane Gibson
 */
public class RequestMatcher implements Matcher<Request,HttpServletRequestWrapper> {

    /**
     * The matcher to use for comparing request body
     */
    private final BodyMatcher bodyMatcher;

    /**
     * The matcher to use for comparing request parameters
     */
    private final ParametersMatcher parametersMatcher;

    /**
     * The matcher to use for comparing request headers
     */
    private final HeadersMatcher headersMatcher;

    /**
     * The matcher to use for comparing request cookies
     */
    private final CookiesMatcher cookiesMatcher;

    /**
     * The matcher to use for comparing request method
     */
    private final MethodMatcher methodMatcher;

    /**
     * The matcher to use for comparing request uri
     */
    private final UriMatcher uriMatcher;

    /**
     * Constructs the request matcher for the given context, using the regular expression string matcher, map matcher and body matcher lookup
     * @param contextPath the context path for the primed request
     */
    public RequestMatcher(final String contextPath) {
        this(
                new BodyMatcher(),
                new UriMatcher(contextPath),
                new ParametersMatcher(new MapMatcher()),
                new HeadersMatcher(new MapMatcher()),
                new CookiesMatcher(new MapMatcher()),
                new MethodMatcher(new StringMatcher()));
    }

    /**
     * Constructs the request matcher for the given matcher implementations
     * @param bodyMatcher the body matcher to use for comparing the actual request against the primed request
     * @param uriMatcher the uri matcher to use for comparing the actual request against the primed request
     * @param parametersMatcher the parameters matcher to use for comparing the actual request against the primed request
     * @param headersMatcher the headers matcher to use for comparing the actual request against the primed request
     * @param cookiesMatcher the cookies matcher to use for comparing the actual request against the primed request
     * @param methodMatcher the method matcher to use for comparing the actual request against the primed request
     */
    public RequestMatcher(
            final BodyMatcher bodyMatcher,
            final UriMatcher uriMatcher,
            final ParametersMatcher parametersMatcher,
            final HeadersMatcher headersMatcher,
            final CookiesMatcher cookiesMatcher,
            final MethodMatcher methodMatcher) {
        this.bodyMatcher = bodyMatcher;
        this.uriMatcher = uriMatcher;
        this.parametersMatcher = parametersMatcher;
        this.headersMatcher = headersMatcher;
        this.cookiesMatcher = cookiesMatcher;
        this.methodMatcher = methodMatcher;
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
                bodyMatcher.match(primedRequest, requestWrapper) &&
                uriMatcher.match(primedRequest, requestWrapper) &&
                methodMatcher.match(primedRequest, requestWrapper) &&
                parametersMatcher.match(primedRequest, requestWrapper) &&
                headersMatcher.match(primedRequest, requestWrapper) &&
                cookiesMatcher.match(primedRequest, requestWrapper);
    }

}