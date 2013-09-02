package uk.co.epsilontechnologies.primer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

class ResponseHandler {

    void respond(final Response response, final HttpServletResponse httpServletResponse) {
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