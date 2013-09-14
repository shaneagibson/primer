package uk.co.epsilontechnologies.primer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class PrimedInvocation {

    private final Request request;
    private final List<Response> responses = new LinkedList();

    public PrimedInvocation(final Request request, final Response... responses) {
        this.request = request;
        this.responses.addAll(Arrays.asList(responses));
    }

    Request getRequest() {
        return request;
    }

    List<Response> getResponses() {
        return responses;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
