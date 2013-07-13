package uk.co.epsilontechnologies.primer.server.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.http.HttpMethod;

import java.util.Map;

public class Request {

    private final HttpMethod method;
    private final String pathRegEx;
    private final String bodyRegEx;
    private final Map<String,String> headers;
    private final Map<String,String> requestParameters;

    public Request(
            final HttpMethod method,
            final String pathRegEx,
            final String bodyRegEx,
            final Map<String, String> headers,
            final Map<String, String> requestParameters) {
        this.method = method;
        this.pathRegEx = pathRegEx;
        this.bodyRegEx = bodyRegEx;
        this.headers = headers;
        this.requestParameters = requestParameters;
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

    public Map<String,String> getHeaders() {
        return headers;
    }

    public Map<String,String> getRequestParameters() {
        return requestParameters;
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
