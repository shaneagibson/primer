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

    /**
     * The request that has been primed
     */
    private final Request request;

    /**
     * The sequential list of responses that have been primed
     */
    private final List<Response> responses = new LinkedList<>();

    /**
     * Constructs the primed invocation for the given request and sequential list of responses
     * @param request the request that is being primed
     * @param responses the responses that are being primed, in sequence
     */
    public PrimedInvocation(final Request request, final Response... responses) {
        this.request = request;
        this.responses.addAll(Arrays.asList(responses));
    }

    /**
     * Getter for the request
     * @return the request that has been primed
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Getter for the sequential list of responses
     * @return the responses that have been primed
     */
    public List<Response> getResponses() {
        return responses;
    }

    /**
     * @see Object#toString()
     * @return the string representation of the primed invocation
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
