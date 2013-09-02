package uk.co.epsilontechnologies.primer;

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
