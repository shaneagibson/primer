package uk.co.epsilontechnologies.primer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.epsilontechnologies.primer.domain.*;
import uk.co.epsilontechnologies.primer.server.PrimerServer;

import java.util.ArrayList;
import java.util.List;

/**
 * A canned HTTP Server instance that can be programmed to behave in a required fashion - to return the appropriate
 * response/s when given specific request/s.
 *
 * @author Shane Gibson
 */
public class Primer {

    /**
     * Logger to use for error / warn / debug logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Primer.class);

    /**
     * The server instance that is being primed
     */
    private final PrimerServer primerServer;

    private final List<PrimedInvocation> primedInvocations;

    /**
     * Constructs a Primer instance for the given port and context path
     * @param contextPath the context path of the web application being primed
     * @param port the port of the web application being primed
     */
    public Primer(final String contextPath, final int port) {
        this(contextPath, port, new ArrayList<PrimedInvocation>());
    }

    Primer(final String contextPath, final int port, final List<PrimedInvocation> primedInvocations) {
        this(
                new PrimerServer(
                        port,
                        new PrimerRequestHandler(
                                contextPath,
                                primedInvocations)),
                primedInvocations);
    }

    Primer(final PrimerServer primerServer, final List<PrimedInvocation> primedInvocations) {
        this.primerServer = primerServer;
        this.primedInvocations = primedInvocations;
    }

    /**
     * Starts the primer server instance
     */
    public void start() {
        this.primerServer.start();
    }

    /**
     * Stops the primer server instance and clears the primed invocations
     */
    public void stop() {
        this.primedInvocations.clear();
        this.primerServer.stop();
    }

    /**
     * Clears the primed invocations
     */
    public void reset() {
        this.primedInvocations.clear();
    }

    /**
     * Convenience method for defining the request being primed
     * @param requestBuilder the builder for the request that is being primed
     * @return The primed request
     */
    public PrimedRequest receives(final RequestBuilder requestBuilder) {
        return new PrimedRequest(this, requestBuilder.build());
    }

    public PrimedRequest get(final String uri) {
        return new PrimedRequest(this, RequestBuilder.get().withUri(uri).build());
    }

    public PrimedRequest get(final String uri, final List<Pair<Matchable>> parameters) {
        return new PrimedRequest(this, RequestBuilder.get().withUri(uri).withParameters(parameters).build());
    }

    public PrimedRequest get(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers) {
        return new PrimedRequest(this, RequestBuilder.get().withUri(uri).withParameters(parameters).withHeaders(headers).build());
    }

    public PrimedRequest get(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers, final List<Pair<Matchable>> cookies) {
        return new PrimedRequest(this, RequestBuilder.get().withUri(uri).withParameters(parameters).withHeaders(headers).withCookies(cookies).build());
    }

    public PrimedRequest delete(final String uri) {
        return new PrimedRequest(this, RequestBuilder.delete().withUri(uri).build());
    }

    public PrimedRequest delete(final String uri, final List<Pair<Matchable>> parameters) {
        return new PrimedRequest(this, RequestBuilder.delete().withUri(uri).withParameters(parameters).build());
    }

    public PrimedRequest delete(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers) {
        return new PrimedRequest(this, RequestBuilder.delete().withUri(uri).withParameters(parameters).withHeaders(headers).build());
    }

    public PrimedRequest delete(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers, final List<Pair<Matchable>> cookies) {
        return new PrimedRequest(this, RequestBuilder.delete().withUri(uri).withParameters(parameters).withHeaders(headers).withCookies(cookies).build());
    }

    public PrimedRequest put(final String uri) {
        return new PrimedRequest(this, RequestBuilder.put().withUri(uri).build());
    }

    public PrimedRequest put(final String uri, final Matchable body) {
        return new PrimedRequest(this, RequestBuilder.put().withUri(uri).withBody(body).build());
    }

    public PrimedRequest put(final String uri, final Matchable body, final List<Pair<Matchable>> parameters) {
        return new PrimedRequest(this, RequestBuilder.put().withUri(uri).withBody(body).withParameters(parameters).build());
    }

    public PrimedRequest put(final String uri, final Matchable body, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers) {
        return new PrimedRequest(this, RequestBuilder.put().withUri(uri).withBody(body).withParameters(parameters).withHeaders(headers).build());
    }

    public PrimedRequest put(final String uri, final Matchable body, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers, final List<Pair<Matchable>> cookies) {
        return new PrimedRequest(this, RequestBuilder.put().withUri(uri).withBody(body).withParameters(parameters).withHeaders(headers).withCookies(cookies).build());
    }

    public PrimedRequest post(final String uri) {
        return new PrimedRequest(this, RequestBuilder.post().withUri(uri).build());
    }

    public PrimedRequest post(final String uri, final Matchable body) {
        return new PrimedRequest(this, RequestBuilder.post().withUri(uri).withBody(body).build());
    }

    public PrimedRequest post(final String uri, final Matchable body, final List<Pair<Matchable>> parameters) {
        return new PrimedRequest(this, RequestBuilder.post().withUri(uri).withBody(body).withParameters(parameters).build());
    }

    public PrimedRequest post(final String uri, final Matchable body, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers) {
        return new PrimedRequest(this, RequestBuilder.post().withUri(uri).withBody(body).withParameters(parameters).withHeaders(headers).build());
    }

    public PrimedRequest post(final String uri, final Matchable body, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers, final List<Pair<Matchable>> cookies) {
        return new PrimedRequest(this, RequestBuilder.post().withUri(uri).withBody(body).withParameters(parameters).withHeaders(headers).withCookies(cookies).build());
    }

    public PrimedRequest head(final String uri) {
        return new PrimedRequest(this, RequestBuilder.head().withUri(uri).build());
    }

    public PrimedRequest head(final String uri, final List<Pair<Matchable>> parameters) {
        return new PrimedRequest(this, RequestBuilder.head().withUri(uri).withParameters(parameters).build());
    }

    public PrimedRequest head(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers) {
        return new PrimedRequest(this, RequestBuilder.head().withUri(uri).withParameters(parameters).withHeaders(headers).build());
    }

    public PrimedRequest head(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers, final List<Pair<Matchable>> cookies) {
        return new PrimedRequest(this, RequestBuilder.head().withUri(uri).withParameters(parameters).withHeaders(headers).withCookies(cookies).build());
    }

    public PrimedRequest options(final String uri) {
        return new PrimedRequest(this, RequestBuilder.options().withUri(uri).build());
    }

    public PrimedRequest options(final String uri, final List<Pair<Matchable>> parameters) {
        return new PrimedRequest(this, RequestBuilder.options().withUri(uri).withParameters(parameters).build());
    }

    public PrimedRequest options(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers) {
        return new PrimedRequest(this, RequestBuilder.options().withUri(uri).withParameters(parameters).withHeaders(headers).build());
    }

    public PrimedRequest options(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers, final List<Pair<Matchable>> cookies) {
        return new PrimedRequest(this, RequestBuilder.options().withUri(uri).withParameters(parameters).withHeaders(headers).withCookies(cookies).build());
    }

    public PrimedRequest trace(final String uri) {
        return new PrimedRequest(this, RequestBuilder.trace().withUri(uri).build());
    }

    public PrimedRequest trace(final String uri, final List<Pair<Matchable>> parameters) {
        return new PrimedRequest(this, RequestBuilder.trace().withUri(uri).withParameters(parameters).build());
    }

    public PrimedRequest trace(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers) {
        return new PrimedRequest(this, RequestBuilder.trace().withUri(uri).withParameters(parameters).withHeaders(headers).build());
    }

    public PrimedRequest trace(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers, final List<Pair<Matchable>> cookies) {
        return new PrimedRequest(this, RequestBuilder.trace().withUri(uri).withParameters(parameters).withHeaders(headers).withCookies(cookies).build());
    }

    public PrimedRequest connect(final String uri) {
        return new PrimedRequest(this, RequestBuilder.connect().withUri(uri).build());
    }

    public PrimedRequest connect(final String uri, final List<Pair<Matchable>> parameters) {
        return new PrimedRequest(this, RequestBuilder.connect().withUri(uri).withParameters(parameters).build());
    }

    public PrimedRequest connect(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers) {
        return new PrimedRequest(this, RequestBuilder.connect().withUri(uri).withParameters(parameters).withHeaders(headers).build());
    }

    public PrimedRequest connect(final String uri, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers, final List<Pair<Matchable>> cookies) {
        return new PrimedRequest(this, RequestBuilder.connect().withUri(uri).withParameters(parameters).withHeaders(headers).withCookies(cookies).build());
    }
    
    public PrimedRequest patch(final String uri, final Matchable body) {
        return new PrimedRequest(this, RequestBuilder.patch().withUri(uri).withBody(body).build());
    }

    public PrimedRequest patch(final String uri, final Matchable body, final List<Pair<Matchable>> parameters) {
        return new PrimedRequest(this, RequestBuilder.patch().withUri(uri).withBody(body).withParameters(parameters).build());
    }

    public PrimedRequest patch(final String uri, final Matchable body, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers) {
        return new PrimedRequest(this, RequestBuilder.patch().withUri(uri).withBody(body).withParameters(parameters).withHeaders(headers).build());
    }

    public PrimedRequest patch(final String uri, final Matchable body, final List<Pair<Matchable>> parameters, final List<Pair<Matchable>> headers, final List<Pair<Matchable>> cookies) {
        return new PrimedRequest(this, RequestBuilder.patch().withUri(uri).withBody(body).withParameters(parameters).withHeaders(headers).withCookies(cookies).build());
    }

    /**
     * Verifies that all of the primed invocations were actually invoked.
     * @throws IllegalStateException at least one primed request was not invoked
     */
    void verify() {
        if (!this.primedInvocations.isEmpty()) {
            LOGGER.error("PRIMER --- Primed Requests Not Invoked. [PrimedInvocations:" + this.primedInvocations + "]");
            throw new IllegalStateException("Primed Requests Not Invoked");
        }
    }

    /**
     * Primes this instance with the given request and responses
     * @param request the request to prime
     * @param responses the responses to prime
     * @responses the responses to prime
     */
    void prime(final Request request, final Response... responses) {
        for (final PrimedInvocation primedInvocation : primedInvocations) {
            if (primedInvocation.getRequest().equals(request)) {
                for (final Response response : responses) {
                    primedInvocation.getResponses().add(response);
                }
                return;
            }
        }
        primedInvocations.add(new PrimedInvocation(request, responses));
    }

}