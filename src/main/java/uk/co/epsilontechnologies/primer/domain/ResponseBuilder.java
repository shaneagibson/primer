package uk.co.epsilontechnologies.primer.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Instance of a HTTP Response that has been programmed.
 * This will be returned if the by the primer if the associated request is matched.
 *
 * @author Shane Gibson
 */
public class ResponseBuilder {

    public static ResponseBuilder response(final int status) {
        return new ResponseBuilder(status);
    }

    /**
     * The HTTP status of the response
     */
    private final int status;

    /**
     * The body of the response
     */
    private String body;

    /**
     * The content type of the response
     */
    private String contentType;

    /**
     * The headers of the response
     */
    private final Map<String,String> headers = new HashMap<>();

    /**
     * The cookies of the response
     */
    private final Map<String,String> cookies = new HashMap<>();

    ResponseBuilder(final int status) {
        this.status = status;
    }

    public ResponseBuilder withBody(final String body) {
        this.body = body;
        return this;
    }

    public ResponseBuilder withHeader(final String name, final String value) {
        this.headers.put(name, value);
        return this;
    }

    public ResponseBuilder withCookie(final String name, final String value) {
        this.cookies.put(name, value);
        return this;
    }

    public ResponseBuilder withContentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Response build() {
        return new Response(status, contentType, body, headers, cookies);
    }
}