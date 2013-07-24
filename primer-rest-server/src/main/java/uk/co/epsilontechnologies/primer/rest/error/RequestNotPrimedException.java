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

    private RequestNotPrimedException() {
        this((HttpMethod) null, (String) null, (String) null);
    }

    private RequestNotPrimedException(final String message) {
        this((HttpMethod) null, (String) null, (String) null);
    }

    private RequestNotPrimedException(final String message, final Throwable cause) {
        this((HttpMethod) null, (String) null, (String) null);
    }

    private RequestNotPrimedException(final Throwable cause) {
        this((HttpMethod) null, (String) null, (String) null);
    }

    private RequestNotPrimedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        this((HttpMethod) null, (String) null, (String) null);
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