package uk.co.epsilontechnologies.primer;

import uk.co.epsilontechnologies.primer.domain.*;
import uk.co.epsilontechnologies.primer.matcher.RequestMatcher;
import uk.co.epsilontechnologies.primer.server.PrimerServer;
import uk.co.epsilontechnologies.primer.server.RequestHandler;
import uk.co.epsilontechnologies.primer.server.ResponseHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
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
     * The primed requests and corresponding responses
     */
    private final List<PrimedInvocation> primedInvocations = new ArrayList();

    /**
     * The server instance that is being primed
     */
    private final PrimerServer server;

    /**
     * The print stream that will be used for error reporting
     */
    private final PrintStream errorPrintStream;

    /**
     * Constructs a Primer instance for the given port and context path
     * @param contextPath the context path of the web application being primed
     * @param port the port of the web application being primed
     */
    public Primer(final String contextPath, final int port) {
        this.server = new PrimerServer(port, new PrimedHandler(contextPath));
        this.errorPrintStream = System.err;
    }

    /**
     * Starts the primer server instance
     */
    public void start() {
        this.server.start();
    }

    /**
     * Stops the primer server instance and clears the primed invocations
     */
    public void stop() {
        this.primedInvocations.clear();
        this.server.stop();
    }

    /**
     * Clears the primed invocations
     */
    public void reset() {
        this.primedInvocations.clear();
    }

    /**
     * Primes the server for the given GET request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest get(final String uri, final Parameters parameters, final Headers headers) {
        return new PrimedRequest(this, new Request("GET", uri, "", parameters, headers));
    }

    /**
     * Primes the server for the given GET request
     * @param uri the uri of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest get(final String uri, final Headers headers) {
        return get(uri, new Parameters(), headers);
    }

    /**
     * Primes the server for the given GET request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @return the request that has been primed
     */
    public PrimedRequest get(final String uri, final Parameters parameters) {
        return get(uri, parameters, new Headers());
    }

    /**
     * Primes the server for the given GET request
     * @param uri the uri of the request
     * @return the request that has been primed
     */
    public PrimedRequest get(final String uri) {
        return get(uri, new Parameters(), new Headers());
    }

    /**
     * Primes the server for the given HEAD request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest head(final String uri, final Parameters parameters, final Headers headers) {
        return new PrimedRequest(this, new Request("HEAD", uri, "", parameters, headers));
    }

    /**
     * Primes the server for the given HEAD request
     * @param uri the uri of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest head(final String uri, final Headers headers) {
        return head(uri, new Parameters(), headers);
    }

    /**
     * Primes the server for the given HEAD request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @return the request that has been primed
     */
    public PrimedRequest head(final String uri, final Parameters parameters) {
        return head(uri, parameters, new Headers());
    }

    /**
     * Primes the server for the given HEAD request
     * @param uri the uri of the request
     * @return the request that has been primed
     */
    public PrimedRequest head(final String uri) {
        return head(uri, new Parameters(), new Headers());
    }

    /**
     * Primes the server for the given TRACE request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest trace(final String uri, final Parameters parameters, final Headers headers) {
        return new PrimedRequest(this, new Request("TRACE", uri, "", parameters, headers));
    }

    /**
     * Primes the server for the given TRACE request
     * @param uri the uri of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest trace(final String uri, final Headers headers) {
        return trace(uri, new Parameters(), headers);
    }

    /**
     * Primes the server for the given TRACE request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @return the request that has been primed
     */
    public PrimedRequest trace(final String uri, final Parameters parameters) {
        return trace(uri, parameters, new Headers());
    }

    /**
     * Primes the server for the given TRACE request
     * @param uri the uri of the request
     * @return the request that has been primed
     */
    public PrimedRequest trace(final String uri) {
        return trace(uri, new Parameters(), new Headers());
    }

    /**
     * Primes the server for the given OPTIONS request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest options(final String uri, final Parameters parameters, final Headers headers) {
        return new PrimedRequest(this, new Request("OPTIONS", uri, "", parameters, headers));
    }

    /**
     * Primes the server for the given OPTIONS request
     * @param uri the uri of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest options(final String uri, final Headers headers) {
        return options(uri, new Parameters(), headers);
    }

    /**
     * Primes the server for the given OPTIONS request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @return the request that has been primed
     */
    public PrimedRequest options(final String uri, final Parameters parameters) {
        return options(uri, parameters, new Headers());
    }

    /**
     * Primes the server for the given OPTIONS request
     * @param uri the uri of the request
     * @return the request that has been primed
     */
    public PrimedRequest options(final String uri) {
        return options(uri, new Parameters(), new Headers());
    }

    /**
     * Primes the server for the given DELETE request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest delete(final String uri, final Parameters parameters, final Headers headers) {
        return new PrimedRequest(this, new Request("DELETE", uri, "", parameters, headers));
    }

    /**
     * Primes the server for the given DELETE request
     * @param uri the uri of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest delete(final String uri, final Headers headers) {
        return delete(uri, new Parameters(), headers);
    }

    /**
     * Primes the server for the given DELETE request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @return the request that has been primed
     */
    public PrimedRequest delete(final String uri, final Parameters parameters) {
        return delete(uri, parameters, new Headers());
    }

    /**
     * Primes the server for the given DELETE request
     * @param uri the uri of the request
     * @return the request that has been primed
     */
    public PrimedRequest delete(final String uri) {
        return delete(uri, new Parameters(), new Headers());
    }

    /**
     * Primes the server for the given PUT request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest put(final String uri, final String body, final Parameters parameters, final Headers headers) {
        return new PrimedRequest(this, new Request("PUT", uri, body, parameters, headers));
    }

    /**
     * Primes the server for the given PUT request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @return the request that has been primed
     */
    public PrimedRequest put(final String uri, final String body, final Parameters parameters) {
        return put(uri, body, parameters, new Headers());
    }

    /**
     * Primes the server for the given PUT request
     * @param uri the uri of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest put(final String uri, final String body, final Headers headers) {
        return put(uri, body, new Parameters(), headers);
    }

    /**
     * Primes the server for the given PUT request
     * @param uri the uri of the request
     * @return the request that has been primed
     */
    public PrimedRequest put(final String uri, final String body) {
        return put(uri, body, new Parameters(), new Headers());
    }

    /**
     * Primes the server for the given POST request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest post(final String uri, final String body, final Parameters parameters, final Headers headers) {
        return new PrimedRequest(this, new Request("POST", uri, body, parameters, headers));
    }

    /**
     * Primes the server for the given POST request
     * @param uri the uri of the request
     * @param parameters the parameters of the request
     * @return the request that has been primed
     */
    public PrimedRequest post(final String uri, final String body, final Parameters parameters) {
        return post(uri, body, parameters, new Headers());
    }

    /**
     * Primes the server for the given POST request
     * @param uri the uri of the request
     * @param headers the headers of the request
     * @return the request that has been primed
     */
    public PrimedRequest post(final String uri, final String body, final Headers headers) {
        return post(uri, body, new Parameters(), headers);
    }

    /**
     * Primes the server for the given POST request
     * @param uri the uri of the request
     * @return the request that has been primed
     */
    public PrimedRequest post(final String uri, final String body) {
        return post(uri, body, new Parameters(), new Headers());
    }

    /**
     * Verifies that all of the primed invocations were actually invoked.
     * @throws IllegalStateException at least one primed request was not invoked
     */
    void verify() {
        if (!this.primedInvocations.isEmpty()) {
            errorPrintStream.println("PRIMER --- Primed Requests Not Invoked. [PrimedInvocations:" + this.primedInvocations + "]");
            throw new IllegalStateException("Primed Requests Not Invoked");
        }
    }

    /**
     * Primes this instance with the given invocation
     * @param primedInvocation the primed invocation to prime
     */
    void prime(final PrimedInvocation primedInvocation) {
        this.primedInvocations.add(primedInvocation);
    }

    /**
     * Handler implementation for each HTTP request
     */
    class PrimedHandler implements RequestHandler {

        /**
         * Handler for the response
         */
        private final ResponseHandler responseHandler;

        /**
         * Matcher for checking whether the request matches a primed request
         */
        private final RequestMatcher requestMatcher;

        /**
         * Constructs the primed handler for the given context path
         * @param contextPath the context path of the request being handled
         */
        public PrimedHandler(final String contextPath) {
            this(new ResponseHandler(), new RequestMatcher(contextPath));
        }

        /**
         * Constructs the primed handler for the given context path
         * @param responseHandler the response handler to use
         * @param requestMatcher the request matcher to use
         */
        public PrimedHandler(final ResponseHandler responseHandler, final RequestMatcher requestMatcher) {
            this.responseHandler = responseHandler;
            this.requestMatcher = requestMatcher;
        }

        /**
         * Handles the given HTTP Servlet Request and HTTP Servlet Response.
         * Checks whether the given http servlet request matches any of the primed invocations.
         * If so, the corresponding response is issued, otherwise a 404 response is issued.
         * @param httpServletRequest the HTTP servlet request that has been issued
         * @param httpServletResponse the HTTP servlet response being returned
         */
        @Override
        public void handle(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {

            final HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpServletRequest);

            if (!checkPrimedInvocations(new ArrayList(primedInvocations), requestWrapper, httpServletResponse)) {
                errorPrintStream.println("PRIMER :-- Request Not Primed. [PrimedInvocations:" + primedInvocations + "]");
                this.responseHandler.respond(new Response(404, "application/json", "Request Not Primed"), httpServletResponse);
            }

        }

        /**
         * Recursive function to check whether the given request matches one of the primed invocations
         * If a match is found, the corresponding response is issued
         * @param primedInvocationsToCheck the primed invocations to check
         * @param requestWrapper the request wrapper
         * @param httpServletResponse the HTTP servlet response
         * @return true if one of the primed invocations match the request, false otherwise
         */
        private boolean checkPrimedInvocations(
                final List<PrimedInvocation> primedInvocationsToCheck,
                final HttpServletRequestWrapper requestWrapper,
                final HttpServletResponse httpServletResponse) {

            if (!primedInvocationsToCheck.isEmpty()) {

                final PrimedInvocation primedInvocationToCheck = primedInvocationsToCheck.remove(0);

                if (this.requestMatcher.match(primedInvocationToCheck.getRequest(), requestWrapper)) {

                    final Response response = primedInvocationToCheck.getResponses().remove(0);

                    if (primedInvocationToCheck.getResponses().isEmpty()) {
                        primedInvocations.remove(primedInvocationToCheck);
                    }

                    this.responseHandler.respond(response, httpServletResponse);

                    return true;
                }

                return checkPrimedInvocations(primedInvocationsToCheck, requestWrapper, httpServletResponse);

            }

            return false;
        }

    }

}