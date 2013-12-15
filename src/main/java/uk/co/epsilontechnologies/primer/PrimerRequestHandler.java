package uk.co.epsilontechnologies.primer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.epsilontechnologies.primer.domain.HttpServletRequestWrapper;
import uk.co.epsilontechnologies.primer.domain.PrimedInvocation;
import uk.co.epsilontechnologies.primer.domain.Response;
import uk.co.epsilontechnologies.primer.matcher.RequestMatcher;
import uk.co.epsilontechnologies.primer.server.RequestHandler;
import uk.co.epsilontechnologies.primer.server.ResponseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static uk.co.epsilontechnologies.primer.domain.ResponseBuilder.response;

/**
 * Handler implementation for each HTTP request
 */
class PrimerRequestHandler implements RequestHandler {

    /**
     * Logger to use for error / warn / debug logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Primer.class);

    /**
     * Handler for the response
     */
    private final ResponseHandler responseHandler;

    /**
     * Matcher for checking whether the request matches a primed request
     */
    private final RequestMatcher requestMatcher;

    /**
     * The primed requests and corresponding responses
     */
    private final List<PrimedInvocation> primedInvocations;

    /**
     * Constructs the primed handler for the given context path
     * @param contextPath the context path of the request being handled
     */
    public PrimerRequestHandler(final String contextPath, final List<PrimedInvocation> primedInvocations) {
        this(new ResponseHandler(), new RequestMatcher(contextPath), primedInvocations);
    }

    /**
     * Constructs the primed handler for the given context path
     * @param responseHandler the response handler to use
     * @param requestMatcher the request matcher to use
     */
    private PrimerRequestHandler(
            final ResponseHandler responseHandler,
            final RequestMatcher requestMatcher,
            final List<PrimedInvocation> primedInvocations) {
        this.responseHandler = responseHandler;
        this.requestMatcher = requestMatcher;
        this.primedInvocations = primedInvocations;
    }

    /**
     * Handles the given HTTP Servlet Request and HTTP Servlet Response.
     * Checks whether the given http servlet request matches any of the primed invocations.
     * If so, the corresponding response is issued, otherwise a 404 response is issued.
     * @param httpServletRequest the HTTP servlet request that has been issued
     * @param httpServletResponse the HTTP servlet response being returned
     */
    @Override
    public void handle(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {

        final HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpServletRequest);

        if (!checkPrimedInvocations(new ArrayList(primedInvocations), requestWrapper, httpServletResponse)) {
            LOGGER.error("PRIMER :-- Request Not Primed. [PrimedInvocations:" + primedInvocations + "]");
            this.responseHandler.respond(response(404).withContentType("text/plain").withBody("Request Not Primed").build(), httpServletResponse);
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
