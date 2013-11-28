package uk.co.epsilontechnologies.primer.domain;

import uk.co.epsilontechnologies.primer.Primer;

/**
 * Representation of a HTTP request against a Primer instance.
 *
 * @author Shane Gibson
 */
public class Action {

    /**
     * The primer instance that is being actioned
     */
    private final Primer primer;

    /**
     * The request that is being actioned
     */
    private final Request request;

    /**
     * Constructs the action for the given primer and request
     * @param primer the primer being actioned
     * @param request the request being actioned
     */
    public Action(final Primer primer, final Request request) {
        this.primer = primer;
        this.request = request;
    }

    /**
     * Gets the primer instance being actioned
     * @return the primer instance
     */
    public Primer getPrimer() {
        return primer;
    }

    /**
     * Gets the request instance being actioned
     * @return the request instance
     */
    public Request getRequest() {
        return request;
    }

}