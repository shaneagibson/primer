package uk.co.epsilontechnologies.primer.client.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.http.HttpMethod;

import java.util.Map;

public class Request {

    private String description;
    private HttpMethod method;
    private String pathRegEx;
    private String bodyRegEx;
    private Map<String,String> headers;
    private Map<String,String> requestParameters;

    public Request() {
        super();
    }

    public Request(
            final String description,
            final HttpMethod method,
            final String pathRegEx,
            final String bodyRegEx,
            final Map<String, String> headers,
            final Map<String, String> requestParameters) {
        this.description = description;
        this.method = method;
        this.pathRegEx = pathRegEx;
        this.bodyRegEx = bodyRegEx;
        this.headers = headers;
        this.requestParameters = requestParameters;
    }

    public String getDescription() {
        return description;
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
