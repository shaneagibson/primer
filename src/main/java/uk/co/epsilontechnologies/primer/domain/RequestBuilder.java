package uk.co.epsilontechnologies.primer.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.co.epsilontechnologies.primer.domain.StringMatchable.any;
import static uk.co.epsilontechnologies.primer.domain.StringMatchable.empty;
import static uk.co.epsilontechnologies.primer.domain.StringMatchable.eq;

/**
 * Builder implementation for a Request
 *
 * @author Shane Gibson
 */
public class RequestBuilder {

    public static RequestBuilder post() {
        return new RequestBuilder("POST");
    }

    public static RequestBuilder get() {
        return new RequestBuilder("GET");
    }

    public static RequestBuilder put() {
        return new RequestBuilder("PUT");
    }

    public static RequestBuilder delete() {
        return new RequestBuilder("DELETE");
    }

    public static RequestBuilder trace() {
        return new RequestBuilder("TRACE");
    }

    public static RequestBuilder options() {
        return new RequestBuilder("OPTIONS");
    }

    public static RequestBuilder head() {
        return new RequestBuilder("HEAD");
    }

    public static RequestBuilder patch() {
        return new RequestBuilder("PATCH");
    }

    public static RequestBuilder connect() {
        return new RequestBuilder("CONNECT");
    }

    RequestBuilder(final String method) {
        this.method = method;
    }

    private final String method;

    /**
     * The URI of the request
     */
    private Matchable uri = any();

    /**
     * The body of the request
     */
    private Matchable body = any();

    /**
     * The parameters of the request
     */
    private final Map<String,Matchable> parameters = new HashMap<>();

    /**
     * The headers of the request
     */
    private final Map<String,Matchable> headers = new HashMap<>();

    /**
     * The cookies of the request
     */
    private final Map<String,Matchable> cookies = new HashMap<>();

    public RequestBuilder withUri(final Matchable uri) {
        this.uri = uri;
        return this;
    }

    public RequestBuilder withUri(final String uri) {
        this.uri = eq(uri);
        return this;
    }

    public RequestBuilder withBody(final Matchable body) {
        this.body = body;
        return this;
    }

    public RequestBuilder withBody(final String body) {
        this.body = eq(body);
        return this;
    }

    public RequestBuilder withHeader(final String name, final Matchable value) {
        this.headers.put(name, value);
        return this;
    }

    public RequestBuilder withParameter(final String name, final Matchable value) {
        this.parameters.put(name, value);
        return this;
    }

    public RequestBuilder withCookie(final String name, final Matchable value) {
        this.cookies.put(name, value);
        return this;
    }

    public RequestBuilder withHeader(final String name, final String value) {
        this.headers.put(name, eq(value));
        return this;
    }

    public RequestBuilder withParameter(final String name, final String value) {
        this.parameters.put(name, eq(value));
        return this;
    }

    public RequestBuilder withCookie(final String name, final String value) {
        this.cookies.put(name, eq(value));
        return this;
    }

    public RequestBuilder withParameters(final List<Pair<Matchable>> parameters) {
        for (final Pair<Matchable> pair : parameters) {
            this.parameters.put(pair.getKey(), pair.getValue());
        }
        return this;
    }

    public RequestBuilder withHeaders(final List<Pair<Matchable>> headers) {
        for (final Pair<Matchable> pair : headers) {
            this.headers.put(pair.getKey(), pair.getValue());
        }
        return this;
    }

    public RequestBuilder withCookies(final List<Pair<Matchable>> cookies) {
        for (final Pair<Matchable> pair : cookies) {
            this.cookies.put(pair.getKey(), pair.getValue());
        }
        return this;
    }

    public Request build() {
        return new Request(method, uri, body, parameters, headers, cookies);
    }

}