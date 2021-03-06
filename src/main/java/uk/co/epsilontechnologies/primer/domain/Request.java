package uk.co.epsilontechnologies.primer.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * Instance of a HTTP Request that has been programmed.
 * This will be matched against any issued HTTP Requests at runtime to determine if there is a primed response.
 *
 * @author Shane Gibson
 */
public class Request {

    /**
     * The HTTP Method of the request (GET, POST, PUT, DELETE, HEAD, etc)
     */
    private final String method;

    /**
     * The URI of the request
     */
    private final Matchable uri;

    /**
     * The body of the request
     */
    private final Matchable body;

    /**
     * The parameters of the request
     */
    private final Map<String,Matchable> parameters;

    /**
     * The headers of the request
     */
    private final Map<String,Matchable> headers;

    /**
     * The cookies of the request
     */
    private final Map<String,Matchable> cookies;

    /**
     * Constructs the request for the given details
     * @param method the HTTP method of the request
     * @param uri the URI of the request
     * @param body the body of the request
     * @param parameters the parameters of the request
     * @param headers the headers of the request
     * @param cookies the cookies of the request
     */
    public Request(
            final String method,
            final Matchable uri,
            final Matchable body,
            final Map<String,Matchable> parameters,
            final Map<String,Matchable> headers,
            final Map<String,Matchable> cookies) {
        this.method = method;
        this.uri = uri;
        this.body = body;
        this.parameters = parameters;
        this.headers = headers;
        this.cookies = cookies;
    }

    /**
     * Getter for the HTTP method of the request
     * @return the HTTP method of the request
     */
    public String getMethod() {
        return method;
    }

    /**
     * Getter for the URI of the request
     * @return the URI of the request
     */
    public Matchable getURI() {
        return uri;
    }

    /**
     * Getter for the body of the request
     * @return the body of the request
     */
    public Matchable getBody() {
        return body;
    }

    /**
     * Getter for the headers of the request
     * @return the headers of the request
     */
    public Map<String,Matchable> getHeaders() {
        return headers;
    }

    /**
     * Getter for the parameters of the request
     * @return the parameters of the request
     */
    public Map<String,Matchable> getParameters() {
        return parameters;
    }

    /**
     * Getter for the cookies of the request
     * @return the cookies of the request
     */
    public Map<String,Matchable> getCookies() {
        return cookies;
    }

    /**
     * @see Object#toString()
     * @return the string representation of the request
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
