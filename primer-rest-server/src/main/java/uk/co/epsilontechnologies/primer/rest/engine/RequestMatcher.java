package uk.co.epsilontechnologies.primer.rest.engine;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import uk.co.epsilontechnologies.primer.client.rest.model.Request;

import java.util.Map;
import java.util.regex.Pattern;

@Component
public class RequestMatcher {
    
    public boolean matches(
            final Request primeRequest,
            final HttpMethod requestMethod,
            final String requestPath,
            final String requestBody,
            final Map<String,String> headers,
            final Map<String,String> requestParameters) {
        final boolean httpMethodMatches = matchHttpMethod(requestMethod, primeRequest.getMethod());
        final boolean pathMatches = matchString(requestPath, primeRequest.getPathRegEx());
        final boolean bodyMatches = matchString(requestBody, primeRequest.getBodyRegEx());
        final boolean headersMatch = matchMap(headers, primeRequest.getHeaders());
        final boolean requestParametersMatch = matchMap(requestParameters, primeRequest.getRequestParameters());

        System.out.println(httpMethodMatches);
        System.out.println(pathMatches);
        System.out.println(bodyMatches);
        System.out.println(headersMatch);
        System.out.println(requestParametersMatch);

        return httpMethodMatches && pathMatches && bodyMatches && headersMatch && requestParametersMatch;
    }

    private boolean matchMap(final Map<String, String> requestMap, final Map<String, String> primedMap) {
        for (final String primedKey : primedMap.keySet()) {
            if (!(requestMap.containsKey(primedKey) && matchString(primedMap.get(primedKey), requestMap.get(primedKey)))) {
                return false;
            }
        }
        return true;
    }

    private boolean matchString(final String requestString, final String primedString) {
        return (primedString != null && primedString.equals(requestString)) || Pattern.matches(primedString, requestString);
    }

    private boolean matchHttpMethod(final HttpMethod requestMethod, final HttpMethod primedMethod) {
        return requestMethod.equals(primedMethod);
    }

}
