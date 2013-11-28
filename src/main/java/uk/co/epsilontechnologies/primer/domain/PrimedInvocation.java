package uk.co.epsilontechnologies.primer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.co.epsilontechnologies.primer.domain.Request;
import uk.co.epsilontechnologies.primer.domain.Response;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Representation of a primed HTTP Request and it's corresponding Responses (in sequence).
 *
 * @author Shane Gibson
 */
public class PrimedInvocation {

    private final Request request;
    private final List<Response> responses = new LinkedList<>();

    public PrimedInvocation(final Request request, final Response... responses) {
        this.request = request;
        this.responses.addAll(Arrays.asList(responses));
    }

    public Request getRequest() {
        return request;
    }

    public List<Response> getResponses() {
        return responses;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
