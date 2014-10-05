package uk.co.epsilontechnologies.primer.server;

import uk.co.epsilontechnologies.primer.domain.Response;

import javax.servlet.http.Cookie;
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
            for (final String key : response.getHeaders().keySet()) {
                httpServletResponse.addHeader(key, response.getHeaders().get(key));
            }
            for (final String key : response.getCookies().keySet()) {
                httpServletResponse.addCookie(new Cookie(key, response.getCookies().get(key)));
            }
            httpServletResponse.setStatus(response.getStatus());
            httpServletResponse.setContentType(response.getContentType());
            if (response.getBody() != null) {
                httpServletResponse.getWriter().write(response.getBody());
            }
            httpServletResponse.flushBuffer();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}