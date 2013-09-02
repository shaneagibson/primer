package uk.co.epsilontechnologies.primer;

public class When {

    private final Primable primer;
    private final Request request;

    public When(final Primable primer, final Request request) {
        this.primer = primer;
        this.request = request;
    }

    public void thenReturn(final Response... responses) {
        this.primer.prime(new PrimedInvocation(request, responses));
    }

    Primable getPrimer() {
        return primer;
    }

    Request getRequest() {
        return request;
    }

}
