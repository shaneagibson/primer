package uk.co.epsilontechnologies.primer;

import java.util.Map;
import java.util.regex.Pattern;

class RequestMatcher {

    private final String contextPath;
    private final StringMatcher stringMatcher;
    private final MapMatcher mapMatcher;

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

    public boolean matches(
            final Request requestToMatch,
            final HttpServletRequestWrapper requestWrapper) {
        return
                bodyMatches(requestToMatch, requestWrapper) &&
                        uriMatches(requestToMatch, requestWrapper) &&
                        methodMatches(requestToMatch, requestWrapper) &&
                        parametersMatch(requestToMatch, requestWrapper) &&
                        headersMatch(requestToMatch, requestWrapper);
    }

    private boolean headersMatch(final Request requestToMatch, final HttpServletRequestWrapper requestWrapper) {
        return mapMatcher.match(requestWrapper.getHeadersAsMap(), requestToMatch.getHeaders().get());
    }

    private boolean parametersMatch(final Request requestToMatch, final HttpServletRequestWrapper requestWrapper) {
        return mapMatcher.match(requestWrapper.getParametersAsMap(), requestToMatch.getParameters().get());
    }

    private boolean methodMatches(final Request requestToMatch, final HttpServletRequestWrapper requestWrapper) {
        return stringMatcher.match(requestWrapper.getMethod(), requestToMatch.getMethod());
    }

    private boolean uriMatches(final Request requestToMatch, final HttpServletRequestWrapper requestWrapper) {
        return stringMatcher.match(requestWrapper.getRequestURI(), contextPath + requestToMatch.getURI());
    }

    private boolean bodyMatches(final Request requestToMatch, final HttpServletRequestWrapper requestWrapper) {
        return stringMatcher.match(requestWrapper.getBody(), requestToMatch.getBody());
    }

    static class MapMatcher {

        private final StringMatcher stringMatcher;

        MapMatcher() {
            this(new StringMatcher());
        }

        MapMatcher(final StringMatcher stringMatcher) {
            this.stringMatcher = stringMatcher;
        }

        public boolean match(
                final Map<String, String> requestMap,
                final Map<String, String> primedMap) {
            for (final String primedKey : primedMap.keySet()) {
                if (!(requestMap.containsKey(primedKey) && stringMatcher.match(primedMap.get(primedKey), requestMap.get(primedKey)))) {
                    return false;
                }
            }
            return true;
        }

    }

    static class StringMatcher {

        public boolean match(
                final String requestString,
                final String primedString) {
            return (primedString != null && primedString.equals(requestString)) || Pattern.matches(primedString, requestString);
        }

    }

}
