package uk.co.epsilontechnologies.primer.server;

import uk.co.epsilontechnologies.primer.domain.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Issues the given response via the Http Servlet Response.
 *
 * @author Shane Gibson
 */
public class ResponseHandler {

    /**
     * Marshals the given response into the HTTP Servlet Response
     * @param response the response to issue
     * @param httpServletResponse the HTTP servlet response to write to
     */
    public void respond(final Response response, final HttpServletResponse httpServletResponse) {
        try {
            response.populate(httpServletResponse);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}