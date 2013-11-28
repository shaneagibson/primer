package uk.co.epsilontechnologies.primer;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
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

    private final String body;

    public HttpServletRequestWrapper(final HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
        try {
            this.body = IOUtils.toString(httpServletRequest.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final InputStream inputStream = IOUtils.toInputStream(body);

        final ServletInputStream servletInputStream = new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(final ReadListener readListener) {

            }
        };

        return servletInputStream;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getParametersAsMap() {
        final Enumeration<String> parameterNames = super.getParameterNames();
        final Map<String,String> result = new HashMap<String,String>();
        while (parameterNames.hasMoreElements()) {
            final String name = parameterNames.nextElement();
            final String value = getParameter(name);
            result.put(name, value);
        }
        return result;
    }


    public Map<String, String> getHeadersAsMap() {
        final Enumeration<String> headerNames = super.getHeaderNames();
        final Map<String,String> result = new HashMap<String,String>();
        while (headerNames.hasMoreElements()) {
            final String name = headerNames.nextElement();
            final String value = getHeader(name);
            result.put(name, value);
        }
        return result;
    }

}
