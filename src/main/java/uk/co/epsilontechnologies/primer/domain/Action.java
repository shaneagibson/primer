package uk.co.epsilontechnologies.primer.domain;

import uk.co.epsilontechnologies.primer.Primer;

/**
 * Representation of a HTTP request against a Primer instance.
 *
 * @author Shane Gibson
 */
public class Action {

    private final Primer primer;
    private final Request request;

    public Action(final Primer primer, final Request request) {
        this.primer = primer;
        this.request = request;
    }

    public Primer getPrimer() {
        return primer;
    }

    public Request getRequest() {
        return request;
    }

}
