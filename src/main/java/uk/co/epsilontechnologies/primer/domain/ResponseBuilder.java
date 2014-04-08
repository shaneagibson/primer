package uk.co.epsilontechnologies.primer.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static ResponseBuilder response(final int status, final String contentType, final String body) {
        return new ResponseBuilder(status).withContentType(contentType).withBody(body);
    }

    public static ResponseBuilder response(final int status, final String contentType, final Producer<String> bodyProducer) {
        return new ResponseBuilder(status).withContentType(contentType).withBody(bodyProducer);
    }

    public static ResponseBuilder response(final int status, final String contentType, final String body, final List<Pair<String>> headers) {
        return new ResponseBuilder(status).withContentType(contentType).withBody(body).withHeaders(headers);
    }

    public static ResponseBuilder response(final int status, final String contentType, final Producer<String> bodyProducer, final Set<Pair<Producer<String>>> headers) {
        return new ResponseBuilder(status).withContentType(contentType).withBody(bodyProducer).withHeaders(headers);
    }

    public static ResponseBuilder response(final int status, final String contentType, final String body, final List<Pair<String>> headers, final List<Pair<String>> cookies) {
        return new ResponseBuilder(status).withContentType(contentType).withBody(body).withHeaders(headers).withCookies(cookies);
    }

    public static ResponseBuilder response(final int status, final String contentType, final Producer<String> bodyProducer, final Set<Pair<Producer<String>>> headers, final Set<Pair<Producer<String>>> cookies) {
        return new ResponseBuilder(status).withContentType(contentType).withBody(bodyProducer).withHeaders(headers).withCookies(cookies);
    }

    /**
     * The HTTP status of the response
     */
    private final int status;

    /**
     * The body of the response
     */
    private Producer<String> body;

    /**
     * The content type of the response
     */
    private String contentType;

    /**
     * The headers of the response
     */
    private final Map<String,Producer<String>> headers = new HashMap<>();

    /**
     * The cookies of the response
     */
    private final Map<String,Producer<String>> cookies = new HashMap<>();

    ResponseBuilder(final int status) {
        this.status = status;
    }

    public ResponseBuilder withBody(final String body) {
        this.body = new StringProducer(body);
        return this;
    }

    public ResponseBuilder withBody(final Producer<String> body) {
        this.body = body;
        return this;
    }

    public ResponseBuilder withHeader(final String name, final String value) {
        this.headers.put(name, new StringProducer(value));
        return this;
    }

    public ResponseBuilder withHeader(final String name, final Producer<String> valueProducer) {
        this.headers.put(name, valueProducer);
        return this;
    }

    public ResponseBuilder withCookie(final String name, final String value) {
        this.cookies.put(name, new StringProducer(value));
        return this;
    }

    public ResponseBuilder withCookie(final String name, final Producer<String> valueProducer) {
        this.cookies.put(name, valueProducer);
        return this;
    }

    public ResponseBuilder withContentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }

    public ResponseBuilder withHeaders(final List<Pair<String>> headers) {
        for (final Pair<String> pair : headers) {
            this.headers.put(pair.getKey(), new StringProducer(pair.getValue()));
        }
        return this;
    }

    public ResponseBuilder withHeaders(final Set<Pair<Producer<String>>> headers) {
        for (final Pair<Producer<String>> pair : headers) {
            this.headers.put(pair.getKey(), pair.getValue());
        }
        return this;
    }

    public ResponseBuilder withCookies(final List<Pair<String>> cookies) {
        for (final Pair<String> pair : cookies) {
            this.cookies.put(pair.getKey(), new StringProducer(pair.getValue()));
        }
        return this;
    }

    public ResponseBuilder withCookies(final Set<Pair<Producer<String>>> cookies) {
        for (final Pair<Producer<String>> pair : cookies) {
            this.cookies.put(pair.getKey(), pair.getValue());
        }
        return this;
    }

    public Response build() {
        return new Response(status, contentType, body, headers, cookies);
    }

}