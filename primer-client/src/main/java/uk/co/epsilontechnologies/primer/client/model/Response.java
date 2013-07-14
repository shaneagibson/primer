package uk.co.epsilontechnologies.primer.client.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class Response {

    private HttpStatus status;
    private String body;
    private Map<String,String> headers;

    public Response() {
        super();
    }

    public Response(final HttpStatus status, final String body, final Map<String,String> headers) {
        this.status = status;
        this.body = body;
        this.headers = headers;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public Map<String,String> getHeaders() {
        return headers;
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

