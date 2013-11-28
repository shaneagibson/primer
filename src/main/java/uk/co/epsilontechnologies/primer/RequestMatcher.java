package uk.co.epsilontechnologies.primer;

/**
 * Matches the HTTP Servlet Request against the attributes of the given primed Request.
 *
 * @author Shane Gibson
 */
class RequestMatcher implements Matcher<Request,HttpServletRequestWrapper> {

    private final String contextPath;
    private final StringMatcher stringMatcher;
    private final MapMatcher mapMatcher;
    private final BodyMatcherLookup bodyMatcherLookup;

    public RequestMatcher(final String contextPath) {
        this(contextPath, new StringMatcher(), new MapMatcher(), new BodyMatcherLookup());
    }

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

    @Override
    public boolean match(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        return
                matchBody(primedRequest, requestWrapper) &&
                matchUri(primedRequest, requestWrapper) &&
                matchMethod(primedRequest, requestWrapper) &&
                matchParameters(primedRequest, requestWrapper) &&
                matchHeaders(primedRequest, requestWrapper);
    }

    private boolean matchHeaders(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        return mapMatcher.match(primedRequest.getHeaders().get(), requestWrapper.getHeadersAsMap());
    }

    private boolean matchParameters(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        return mapMatcher.match(primedRequest.getParameters().get(), requestWrapper.getParametersAsMap());
    }

    private boolean matchMethod(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        return stringMatcher.match(primedRequest.getMethod(), requestWrapper.getMethod());
    }

    private boolean matchUri(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        return stringMatcher.match(contextPath + primedRequest.getURI(), requestWrapper.getRequestURI());
    }

    private boolean matchBody(final Request primedRequest, final HttpServletRequestWrapper requestWrapper) {
        final Matcher<String,String> bodyMatcher = bodyMatcherLookup.getMatcher(requestWrapper.getContentType());
        return bodyMatcher.match(primedRequest.getBody(), requestWrapper.getBody());
    }

}
