package uk.co.epsilontechnologies.primer;

class Action {

    private final Primable primer;
    private final Request request;

    public Action(final Primable primer, final Request request) {
        this.primer = primer;
        this.request = request;
    }

    Primable getPrimer() {
        return primer;
    }

    Request getRequest() {
        return request;
    }

}
