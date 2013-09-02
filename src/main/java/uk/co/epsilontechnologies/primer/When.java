package uk.co.epsilontechnologies.primer;

public class When {

    private final Primer primer;
    private final Request request;

    public When(final Primer primer, final Request request) {
        this.primer = primer;
        this.request = request;
    }

    public void thenReturn(final Response... responses) {
        this.primer.prime(new Prime(request, responses));
    }

    Primer getPrimer() {
        return primer;
    }

    Request getRequest() {
        return request;
    }

}
