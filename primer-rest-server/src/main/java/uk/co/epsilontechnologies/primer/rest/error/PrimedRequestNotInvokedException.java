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

    public Map<Request,List<Response>> getPrimeRequestList() {
        return primeRequestList;
    }

}