package uk.co.epsilontechnologies.primer;

/**
 * Representation of a HTTP request against a Primer instance.
 *
 * @author Shane Gibson
 */
class Action {

    private final Primer primer;
    private final Request request;

    public Action(final Primer primer, final Request request) {
        this.primer = primer;
        this.request = request;
    }

    Primer getPrimer() {
        return primer;
    }

    Request getRequest() {
        return request;
    }

}
