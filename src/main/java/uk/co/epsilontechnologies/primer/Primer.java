package uk.co.epsilontechnologies.primer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Primer {

    private static final Response INVALID_REQUEST = new Response(404, "application/json", "Request Not Primed");

    private final List<Prime> primes = new ArrayList<>();

    private final Server server;

    public Primer(final String contextPath, final int port) {
        this.server = new Server(port);
        this.server.setHandler(new PrimedHandler(contextPath));
    }

    public void start() {
        try {
            server.start();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            primes.clear();
            server.stop();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static When when(final Action action) {
        return new When(action.getPrimer(), action.getRequest());
    }

    public static Headers headers(final Pair... pairs) {
        return new Headers(pairs);
    }

    public static Pair pair(final String name, final String value) {
        return new Pair(name, value);
    }

    public static Parameters parameters(final Pair... pairs) {
        return new Parameters(pairs);
    }

    public static Request request(
            final String method,
            final String path,
            final String body,
            final Parameters parameters,
            final Headers headers) {
        return new Request(method, path, body, parameters, headers);
    }

    public static Response response(
            final int status,
            final String contentType,
            final String body,
            final Headers headers) {
        return new Response(status, contentType, body, headers);
    }

    public static Response response(
            final int status,
            final String contentType,
            final String body) {
        return new Response(status, contentType, body);
    }

    public static Response response(final int status) {
        return new Response(status);
    }

    public Action get(final String uri, final Parameters parameters, final Headers headers) {
        return new Action(this, new Request("GET", uri, "", parameters, headers));
    }

    public Action get(final String uri, final Headers headers) {
        return get(uri, new Parameters(), headers);
    }

    public Action get(final String uri, final Parameters parameters) {
        return get(uri, parameters, new Headers());
    }

    public Action get(final String uri) {
        return get(uri, new Parameters(), new Headers());
    }

    public Action head(final String uri, final Parameters parameters, final Headers headers) {
        return new Action(this, new Request("HEAD", uri, "", parameters, headers));
    }

    public Action head(final String uri, final Headers headers) {
        return head(uri, new Parameters(), headers);
    }

    public Action head(final String uri, final Parameters parameters) {
        return head(uri, parameters, new Headers());
    }

    public Action head(final String uri) {
        return head(uri, new Parameters(), new Headers());
    }

    public Action trace(final String uri, final Parameters parameters, final Headers headers) {
        return new Action(this, new Request("TRACE", uri, "", parameters, headers));
    }

    public Action trace(final String uri, final Headers headers) {
        return trace(uri, new Parameters(), headers);
    }

    public Action trace(final String uri, final Parameters parameters) {
        return trace(uri, parameters, new Headers());
    }

    public Action trace(final String uri) {
        return trace(uri, new Parameters(), new Headers());
    }

    public Action options(final String uri, final Parameters parameters, final Headers headers) {
        return new Action(this, new Request("OPTIONS", uri, "", parameters, headers));
    }

    public Action options(final String uri, final Headers headers) {
        return options(uri, new Parameters(), headers);
    }

    public Action options(final String uri, final Parameters parameters) {
        return options(uri, parameters, new Headers());
    }

    public Action options(final String uri) {
        return options(uri, new Parameters(), new Headers());
    }

    public Action delete(final String uri, final Parameters parameters, final Headers headers) {
        return new Action(this, new Request("DELETE", uri, "", parameters, headers));
    }

    public Action delete(final String uri, final Headers headers) {
        return delete(uri, new Parameters(), headers);
    }

    public Action delete(final String uri, final Parameters parameters) {
        return delete(uri, parameters, new Headers());
    }

    public Action delete(final String uri) {
        return delete(uri, new Parameters(), new Headers());
    }

    public Action put(final String uri, final String body, final Parameters parameters, final Headers headers) {
        return new Action(this, new Request("PUT", uri, body, parameters, headers));
    }

    public Action put(final String uri, final String body, final Parameters parameters) {
        return put(uri, body, parameters, new Headers());
    }

    public Action put(final String uri, final String body, final Headers headers) {
        return put(uri, body, new Parameters(), headers);
    }

    public Action put(final String uri, final String body) {
        return put(uri, body, new Parameters(), new Headers());
    }

    public Action post(final String uri, final String body, final Parameters parameters, final Headers headers) {
        return new Action(this, new Request("POST", uri, body, parameters, headers));
    }

    public Action post(final String uri, final String body, final Parameters parameters) {
        return post(uri, body, parameters, new Headers());
    }

    public Action post(final String uri, final String body, final Headers headers) {
        return post(uri, body, new Parameters(), headers);
    }

    public Action post(final String uri, final String body) {
        return post(uri, body, new Parameters(), new Headers());
    }

    public void verify() {
        if (!this.primes.isEmpty()) {
            throw new IllegalStateException("Primed requests not invoked: " + this.primes);
        }
    }

    void prime(final Prime prime) {
        this.primes.add(prime);
    }

    class PrimedHandler extends AbstractHandler {

        private final String contextPath;
        private final RequestParser requestParser;
        private final ResponseHandler responseHandler;
        private final RequestMatcher requestMatcher;

        public PrimedHandler(final String contextPath) {
            this(
                    contextPath,
                    new ResponseHandler(),
                    new RequestParser(),
                    new RequestMatcher(contextPath));
        }

        public PrimedHandler(
                final String contextPath,
                final ResponseHandler responseHandler,
                final RequestParser requestParser,
                final RequestMatcher requestMatcher) {
            this.contextPath = contextPath;
            this.responseHandler = responseHandler;
            this.requestParser = requestParser;
            this.requestMatcher = requestMatcher;
        }

        @Override
        public void handle(
                final String target,
                final org.eclipse.jetty.server.Request baseRequest,
                final HttpServletRequest httpServletRequest,
                final HttpServletResponse httpServletResponse) throws IOException, ServletException {

            final String requestMethod = requestParser.parseRequestMethod(httpServletRequest);
            final String requestUri = requestParser.parseRequestUri(httpServletRequest);
            final String requestBody = requestParser.parseRequestBody(httpServletRequest);
            final Map<String,String> requestHeaders = requestParser.parseHeaders(httpServletRequest);
            final Map<String,String> requestParameters = requestParser.parseRequestParameters(httpServletRequest);

            boolean handled = false;

            if (requestUri.startsWith(contextPath)) {

                for (final Prime prime : primes) {

                    if (requestMatcher.matchRequestMethod(requestMethod, prime) &&
                        requestMatcher.matchRequestUri(requestUri, prime) &&
                        requestMatcher.matchRequestBody(requestBody, prime) &&
                        requestMatcher.matchHeaders(requestHeaders, prime) &&
                        requestMatcher.matchRequestParameters(requestParameters, prime)) {

                        final Response response = prime.getResponses().remove(0);
                        if (prime.getResponses().isEmpty()) {
                            primes.remove(prime);
                        }
                        responseHandler.respond(response, httpServletResponse);
                        handled = true;
                        break;
                    }

                }

            }

            if (!handled) {
                responseHandler.respond(INVALID_REQUEST, httpServletResponse);
            }

        }

    }

}