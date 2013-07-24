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

    private PrimedRequestNotInvokedException(String message) {
        this((Map<Request,List<Response>>) null);
    }

    private PrimedRequestNotInvokedException(String message, Throwable cause) {
        this((Map<Request,List<Response>>) null);
    }

    private PrimedRequestNotInvokedException(Throwable cause) {
        this((Map<Request,List<Response>>) null);
    }

    private PrimedRequestNotInvokedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this((Map<Request,List<Response>>) null);
    }

    public Map<Request,List<Response>> getPrimeRequestList() {
        return primeRequestList;
    }

}
