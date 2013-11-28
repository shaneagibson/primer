package uk.co.epsilontechnologies.primer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Instance of a HTTP Response that has been programmed.
 * This will be returned if the by the primer if the associated request is matched.
 *
 * @author Shane Gibson
 */
public class Response {

    /**
     * The HTTP status of the response
     */
    private final int status;

    /**
     * The body of the response
     */
    private final String body;

    /**
     * The headers of the response
     */
    private final Headers headers;

    /**
     * The content type of the response
     */
    private final String contentType;

    /**
     * Constructs the response for the given status, content type, body and headers
     * @param status the HTTP status of the response
     * @param contentType the content type of the response
     * @param body the body of the response
     * @param headers the headers of the response
     */
    public Response(
            final int status,
            final String contentType,
            final String body,
            final Headers headers) {
        this.status = status;
        this.body = body;
        this.headers = headers;
        this.contentType = contentType;
    }

    /**
     * Constructs the response for the given status, content type and body
     * @param status the HTTP status of the response
     * @param contentType the content type of the response
     * @param body the body of the response
     */
    public Response(
            final int status,
            final String contentType,
            final String body) {
        this(status, contentType, body, new Headers());
    }

    /**
     * Constructs the response for the given status
     * @param status the HTTP status of the response
     */
    public Response(final int status) {
        this(status, "text/plain", "", new Headers());
    }

    /**
     * Getter for the HTTP status of the response
     * @return the HTTP status of the response
     */
    public int getStatus() {
        return status;
    }

    /**
     * Getter for the body of the response
     * @return the body of the response
     */
    public String getBody() {
        return body;
    }

    /**
     * Getter for the headers of the response
     * @return the headers of the response
     */
    public Headers getHeaders() {
        return headers;
    }

    /**
     * Getter for the Content-Type of the response
     * @return the Content-Type of the response
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @see Object#toString()
     * @return the string representation of the response
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}