package uk.co.epsilontechnologies.primer.rest.error;

import uk.co.epsilontechnologies.primer.client.rest.model.Request;
import uk.co.epsilontechnologies.primer.client.rest.model.Response;

import java.util.List;
import java.util.Map;

public class PrimedRequestNotInvokedException extends Exception {

    private final Map<Request,List<Response>> primeRequestList;

    public PrimedRequestNotInvokedException(final Map<Request,List<Response>> primeRequestList) {
        super();
        this.primeRequestList = primeRequestList;
    }

    private PrimedRequestNotInvokedException() {
        this((Map<Request,List<Response>>) null);
    }

    private PrimedRequestNotInvokedException(final String message) {
        super(message);
        this.primeRequestList = null;
    }

    private PrimedRequestNotInvokedException(final String message, final Throwable cause) {
        super(message, cause);
        this.primeRequestList = null;
    }

    private PrimedRequestNotInvokedException(final Throwable cause) {
        super(cause);
        this.primeRequestList = null;
    }

    private PrimedRequestNotInvokedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.primeRequestList = null;
    }

    public Map<Request,List<Response>> getPrimeRequestList() {
        return primeRequestList;
    }

}