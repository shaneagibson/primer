package uk.co.epsilontechnologies.primer.client.builder;

import uk.co.epsilontechnologies.primer.client.model.PrimeRequest;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

    private String description;

    private HttpMethod httpMethod = HttpMethod.GET;

    private String pathRegEx = ".*?";

    private String bodyRegEx = ".*?";

    private Map<String,String> headers = new HashMap();

    private Map<String,String> requestParameters = new HashMap();

    private ResponseBuilder primeResponse;

    public RequestBuilder(final String description) {
        this.description = description;
    }

    public RequestBuilder withMethod(final HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public RequestBuilder withPath(final String pathRegEx) {
        this.pathRegEx = pathRegEx;
        return this;
    }

    public RequestBuilder withBody(final String bodyRegEx) {
        this.bodyRegEx = bodyRegEx;
        return this;
    }

    public RequestBuilder withHeader(final String key, final String value) {
        this.headers.put(key, value);
        return this;
    }

    public RequestBuilder withRequestParameter(final String key, final String value) {
        this.requestParameters.put(key, value);
        return this;
    }

    public RequestBuilder thenReturn(final ResponseBuilder primeResponse) {
        this.primeResponse = primeResponse;
        return this;
    }

    public PrimeRequest build() {
        final PrimeRequest primeRequest = new PrimeRequest();
        primeRequest.setDescription(description);
        primeRequest.setBodyRegEx(bodyRegEx);
        primeRequest.setHttpMethod(httpMethod.name());
        primeRequest.setPathRegEx(pathRegEx);
        primeRequest.setResponseBody(primeResponse.getBody());
        primeRequest.setResponseCode(primeResponse.getStatus().value());
        primeRequest.setHeaders(headers);
        primeRequest.setRequestParameters(requestParameters);
        return primeRequest;
    }

    public static RequestBuilder request(final String description) {
        return new RequestBuilder(description);
    }

}
