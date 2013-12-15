package uk.co.epsilontechnologies.primer.domain;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for the HTTP Servlet Request, streaming the request content into a local cache.
 * This enables multiple inspections of the request body and also exposes convenient access to Parameters and Headers.
 *
 * @author Shane Gibson
 */
public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

    /**
     * The request body as a String
     */
    private final String body;

    /**
     * Constructs the HttpServletRequestWrapper for the given HttpServletRequest
     * @param httpServletRequest the request to wrap
     */
    public HttpServletRequestWrapper(final HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
        try {
            this.body = IOUtils.toString(httpServletRequest.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Exposes the request content as a string
     * @return the request content
     */
    public String getBody() {
        return body;
    }

    /**
     * Exposes the request parameters as a map of key / value pairs
     * @return the parameters as a map of key / value pairs
     */
    public Map<String, String> getParametersAsMap() {
        final Enumeration<String> parameterNames = super.getParameterNames();
        final Map<String,String> result = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            final String name = parameterNames.nextElement();
            final String value = getParameter(name);
            result.put(name, value);
        }
        return result;
    }

    /**
     * Exposes the headers as a map of key / value pairs
     * @return the headers as a map of key / value pairs
     */
    public Map<String, String> getHeadersAsMap() {
        final Enumeration<String> headerNames = super.getHeaderNames();
        final Map<String,String> result = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            final String name = headerNames.nextElement();
            final String value = getHeader(name);
            result.put(name, value);
        }
        return result;
    }

    /**
     * Exposes the cookies as a map of key / value pairs
     * @return the cookies as a map of key / value pairs
     */
    public Map<String, String> getCookiesAsMap() {
        final Map<String,String> result = new HashMap<>();
        if (super.getCookies() != null) {
            for (final Cookie cookie : super.getCookies()) {
                result.put(cookie.getName(), cookie.getValue());
            }
        }
        return result;
    }

}
