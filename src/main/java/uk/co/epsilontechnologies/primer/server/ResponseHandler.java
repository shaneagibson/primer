package uk.co.epsilontechnologies.primer.server;

import uk.co.epsilontechnologies.primer.domain.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Issues the given response via the Http Servlet Response.
 *
 * @author Shane Gibson
 */
public class ResponseHandler {

    public void respond(final Response response, final HttpServletResponse httpServletResponse) {
        try {
            final Map<String,String> headers = response.getHeaders().get();
            for (final String key : headers.keySet()) {
                httpServletResponse.addHeader(key, headers.get(key));
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