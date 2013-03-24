package uk.co.epsilontechnologies.primer.server.engine;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import uk.co.epsilontechnologies.primer.server.model.Request;

import java.util.regex.Pattern;

@Component
public class RequestMatcher {
    
    public boolean matches(final Request primeRequest, final HttpMethod requestMethod, final String requestPath, final String requestBody) {
        return
                requestMethod == primeRequest.getMethod()
                        && ((primeRequest.getPathRegEx() != null && primeRequest.getPathRegEx().equals(requestPath)) || Pattern.matches(primeRequest.getPathRegEx(), requestPath))
                        && ((primeRequest.getBodyRegEx() != null && primeRequest.getBodyRegEx().equals(requestBody)) || Pattern.matches(primeRequest.getBodyRegEx(), requestBody));
    }
    
}
