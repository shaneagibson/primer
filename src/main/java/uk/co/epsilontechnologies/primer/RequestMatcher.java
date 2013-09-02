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

    boolean matchRequestMethod(final String requestMethod, final PrimedInvocation primedInvocation) {
        return stringMatcher.match(requestMethod, primedInvocation.getRequest().getMethod());
    }

    boolean matchRequestUri(final String requestUri, final PrimedInvocation primedInvocation) {
        return stringMatcher.match(
                requestUri.replaceFirst(contextPath, ""),
                primedInvocation.getRequest().getURI());
    }

    boolean matchRequestBody(final String requestBody, final PrimedInvocation primedInvocation) {
        return stringMatcher.match(requestBody, primedInvocation.getRequest().getBody());
    }

    boolean matchHeaders(final Map<String,String> requestHeaders, final PrimedInvocation primedInvocation) {
        return mapMatcher.match(requestHeaders, primedInvocation.getRequest().getHeaders().get());
    }

    boolean matchRequestParameters(final Map<String,String> requestParameters, final PrimedInvocation primedInvocation) {
        return mapMatcher.match(requestParameters, primedInvocation.getRequest().getParameters().get());
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
