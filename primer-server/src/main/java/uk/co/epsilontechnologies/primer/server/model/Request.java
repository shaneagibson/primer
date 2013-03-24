package uk.co.epsilontechnologies.primer.server.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.http.HttpMethod;

public class Request {

    private final HttpMethod method;
    private final String pathRegEx;
    private final String bodyRegEx;

    public Request(final HttpMethod method, final String pathRegEx, final String bodyRegEx) {
        this.method = method;
        this.pathRegEx = pathRegEx;
        this.bodyRegEx = bodyRegEx;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPathRegEx() {
        return pathRegEx;
    }

    public String getBodyRegEx() {
        return bodyRegEx;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
