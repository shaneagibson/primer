package uk.co.epsilontechnologies.primer;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Instance of a HTTP Response that has been programmed.
 * This will be returned if the by the primer if the associated request is matched.
 *
 * @author Shane Gibson
 */
public class Response {

    private final int status;
    private final String body;
    private final Headers headers;
    private final String contentType;

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

    public Response(
            final int status,
            final String contentType,
            final String body) {
        this(status, contentType, body, new Headers());
    }

    public Response(final int status) {
        this(status, "text/plain", "", new Headers());
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}