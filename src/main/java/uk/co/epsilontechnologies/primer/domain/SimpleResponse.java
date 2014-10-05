package uk.co.epsilontechnologies.primer.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Instance of a HTTP Response that has been programmed.
 * This will be returned if the by the primer if the associated request is matched.
 *
 * @author Shane Gibson
 */
public class SimpleResponse implements Response {

    /**
     * The HTTP status of the response
     */
    private final int status;

    /**
     * The body of the response
     */
    private final String body;

    /**
     * The headers of the response
     */
    private final Map<String,String> headers;

    /**
     * The content type of the response
     */
    private final String contentType;

    /**
     * The cookies of the response
     */
    private final Map<String,String> cookies;

    /**
     * Constructs the response for the given status, content type, body and headers
     * @param status the HTTP status of the response
     * @param contentType the content type of the response
     * @param body the body of the response
     * @param headers the headers of the response
     * @param cookies the cookies of the response
     */
    public SimpleResponse(
            final int status,
            final String contentType,
            final String body,
            final Map<String, String> headers,
            final Map<String, String> cookies) {
        this.status = status;
        this.body = body;
        this.headers = headers;
        this.contentType = contentType;
        this.cookies = cookies;
    }

    /**
     * Getter for the HTTP status of the response
     * @return the HTTP status of the response
     */
    public int getStatus() {
        return status;
    }

    /**
     * Getter for the body of the response
     * @return the body of the response
     */
    public String getBody() {
        return body;
    }

    /**
     * Getter for the headers of the response
     * @return the headers of the response
     */
    public Map<String,String> getHeaders() {
        return headers;
    }

    /**
     * Getter for the cookies of the response
     * @return the cookies of the response
     */
    public Map<String,String> getCookies() {
        return cookies;
    }


    /**
     * Getter for the Content-Type of the response
     * @return the Content-Type of the response
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Populates the given Http Servlet Response with this Response's data.
     * @param httpServletResponse
     * @throws IOException
     */
    @Override
    public void populate(final HttpServletResponse httpServletResponse) throws IOException {
        for (final String key : this.getHeaders().keySet()) {
            httpServletResponse.addHeader(key, this.getHeaders().get(key));
        }
        for (final String key : this.getCookies().keySet()) {
            httpServletResponse.addCookie(new Cookie(key, this.getCookies().get(key)));
        }
        httpServletResponse.setStatus(this.getStatus());
        httpServletResponse.setContentType(this.getContentType());
        if (this.getBody() != null) {
            httpServletResponse.getWriter().write(this.getBody());
        }
        httpServletResponse.flushBuffer();
    }

    /**
     * @see Object#toString()
     * @return the string representation of the response
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @see Object#equals(Object)
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * @see Object#hashCode()
     * @return the hash code for this instance
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}