package uk.co.epsilontechnologies.primer.rest.engine;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestParser {

    public HttpMethod parseRequestMethod(final HttpServletRequest request) {
        return HttpMethod.valueOf(request.getMethod());
    }

    public String parseRequestPath(final HttpServletRequest request) {
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

    public Map<String, String> parseHeaders(final HttpServletRequest request) {
        final Map<String,String> headers = new HashMap();
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    public Map<String, String> parseRequestParameters(final HttpServletRequest request) {
        final Map<String,String> requestParameters = new HashMap();
        final Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            final String parameterName = parameterNames.nextElement();
            requestParameters.put(parameterName, request.getParameter(parameterName));
        }
        return requestParameters;
    }

}