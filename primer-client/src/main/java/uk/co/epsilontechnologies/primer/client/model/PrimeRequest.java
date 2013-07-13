package uk.co.epsilontechnologies.primer.client.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Map;

public class PrimeRequest {

    private String description;

    private String httpMethod;

    private String pathRegEx;

    private String bodyRegEx;

    private Map<String,String> headers;

    private Map<String,String> requestParameters;

    private int responseCode;

    private String responseBody;

    public PrimeRequest() {
        super();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPathRegEx() {
        return pathRegEx;
    }

    public void setPathRegEx(String pathRegEx) {
        this.pathRegEx = pathRegEx;
    }

    public String getBodyRegEx() {
        return bodyRegEx;
    }

    public void setBodyRegEx(String bodyRegEx) {
        this.bodyRegEx = bodyRegEx;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public void setHeaders(Map<String,String> headers) {
        this.headers = headers;
    }

    public Map<String,String> getHeaders() {
        return headers;
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
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
