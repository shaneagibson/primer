package uk.co.epsilontechnologies.primer;

/**
 * Container for programming the 'when' of the test case.
 *
 * @author Shane Gibson
 */
public class When {

    private final Primer primer;
    private final Request request;

    public When(final Primer primer, final Request request) {
        this.primer = primer;
        this.request = request;
    }

    public When thenReturn(final Response... responses) {
        this.primer.prime(new PrimedInvocation(request, responses));
        return this;
    }

    Primer getPrimer() {
        return primer;
    }

}
