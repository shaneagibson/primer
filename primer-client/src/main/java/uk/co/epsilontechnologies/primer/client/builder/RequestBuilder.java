package uk.co.epsilontechnologies.primer.client.builder;

import org.springframework.http.HttpMethod;
import uk.co.epsilontechnologies.primer.client.model.PrimeRequest;
import uk.co.epsilontechnologies.primer.client.model.Request;
import uk.co.epsilontechnologies.primer.client.model.Response;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

    private String description;

    private HttpMethod httpMethod = HttpMethod.GET;

    private String pathRegEx = ".*?";

    private String bodyRegEx = ".*?";

    private Map<String,String> headers = new HashMap();

    private Map<String,String> requestParameters = new HashMap();

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

    public Request build() {
        return new Request(description, httpMethod, pathRegEx, bodyRegEx, headers, requestParameters);
    }

    public static RequestBuilder request(final String description) {
        return new RequestBuilder(description);
    }

}
