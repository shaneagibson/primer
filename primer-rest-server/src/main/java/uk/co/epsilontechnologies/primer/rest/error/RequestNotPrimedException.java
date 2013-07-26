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
        super();
        this.httpMethod = null;
        this.requestPath = null;
        this.requestBody = null;
    }

    private RequestNotPrimedException(final String message) {
        super(message);
        this.httpMethod = null;
        this.requestPath = null;
        this.requestBody = null;
    }

    private RequestNotPrimedException(final String message, final Throwable cause) {
        super(message, cause);
        this.httpMethod = null;
        this.requestPath = null;
        this.requestBody = null;
    }

    private RequestNotPrimedException(final Throwable cause) {
        super(cause);
        this.httpMethod = null;
        this.requestPath = null;
        this.requestBody = null;
    }

    private RequestNotPrimedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpMethod = null;
        this.requestPath = null;
        this.requestBody = null;
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