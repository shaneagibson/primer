package uk.co.epsilontechnologies.primer.rest.error;

import org.springframework.http.HttpMethod;

public class RequestNotPrimedException extends Exception {

    private final HttpMethod httpMethod;
    private final String requestPath;
    private final String requestBody;

    public RequestNotPrimedException(final HttpMethod httpMethod, final String requestPath, final String requestBody) {
        this.httpMethod = httpMethod;
        this.requestPath = requestPath;
        this.requestBody = requestBody;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getRequestBody() {
        return requestBody;
    }

}