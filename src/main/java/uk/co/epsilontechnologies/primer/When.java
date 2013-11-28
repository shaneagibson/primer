package uk.co.epsilontechnologies.primer;

import uk.co.epsilontechnologies.primer.domain.PrimedInvocation;
import uk.co.epsilontechnologies.primer.domain.Request;
import uk.co.epsilontechnologies.primer.domain.Response;

/**
 * Container for programming the 'when' of the test case.
 *
 * @author Shane Gibson
 */
public class When {

    private final Primer primer;
    private final Request request;

    When(final Primer primer, final Request request) {
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
