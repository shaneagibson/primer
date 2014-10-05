package uk.co.epsilontechnologies.primer.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instance of a HTTP Response that has been programmed.
 * This will be returned if the by the primer if the associated request is matched.
 *
 * @author Shane Gibson
 */
public class SimpleResponseBuilder implements ResponseBuilder {

    public static SimpleResponseBuilder response(final int status) {
        return new SimpleResponseBuilder(status);
    }

    public static SimpleResponseBuilder response(final int status, final String contentType, final String body) {
        return new SimpleResponseBuilder(status).withContentType(contentType).withBody(body);
    }

    public static SimpleResponseBuilder response(final int status, final String contentType, final String body, final List<Pair<String>> headers) {
        return new SimpleResponseBuilder(status).withContentType(contentType).withBody(body).withHeaders(headers);
    }

    public static SimpleResponseBuilder response(final int status, final String contentType, final String body, final List<Pair<String>> headers, final List<Pair<String>> cookies) {
        return new SimpleResponseBuilder(status).withContentType(contentType).withBody(body).withHeaders(headers).withCookies(cookies);
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

    public SimpleResponseBuilder(final int status) {
        this.status = status;
    }

    public SimpleResponseBuilder withBody(final String body) {
        this.body = body;
        return this;
    }

    public SimpleResponseBuilder withHeader(final String name, final String value) {
        this.headers.put(name, value);
        return this;
    }

    public SimpleResponseBuilder withCookie(final String name, final String value) {
        this.cookies.put(name, value);
        return this;
    }

    public SimpleResponseBuilder withContentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }

    public SimpleResponseBuilder withHeaders(final List<Pair<String>> headers) {
        for (final Pair<String> pair : headers) {
            this.headers.put(pair.getKey(), pair.getValue());
        }
        return this;
    }

    public SimpleResponseBuilder withCookies(final List<Pair<String>> cookies) {
        for (final Pair<String> pair : cookies) {
            this.cookies.put(pair.getKey(), pair.getValue());
        }
        return this;
    }

    @Override
    public Response build() {
        return new SimpleResponse(status, contentType, body, headers, cookies);
    }

}