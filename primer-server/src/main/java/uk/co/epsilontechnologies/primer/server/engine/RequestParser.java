package uk.co.epsilontechnologies.primer.server.engine;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RequestParser {

    public HttpMethod parseRequestMethod(final HttpServletRequest request) {
        return HttpMethod.valueOf(request.getMethod());
    }

    public String parseRequestPath(final HttpServletRequest request) {
        if (request.getQueryString() != null) {
            return request.getPathInfo() + "?" + request.getQueryString();
        }
        return request.getPathInfo();
    }

    public String parseRequestBody(final HttpServletRequest request) {
        try {
            final String requestBody = IOUtils.toString(request.getInputStream());
            if (requestBody == null) {
                return "";
            }
            return requestBody;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}
