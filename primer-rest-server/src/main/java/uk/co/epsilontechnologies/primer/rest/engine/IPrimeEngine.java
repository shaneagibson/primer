package uk.co.epsilontechnologies.primer.rest.engine;

import org.springframework.http.ResponseEntity;
import uk.co.epsilontechnologies.primer.client.rest.model.PrimeRequest;
import uk.co.epsilontechnologies.primer.rest.error.PrimedRequestNotInvokedException;
import uk.co.epsilontechnologies.primer.rest.error.RequestNotPrimedException;

import javax.servlet.http.HttpServletRequest;

public interface IPrimeEngine {

    void prime(final PrimeRequest primeRequest);

    void reset();

    ResponseEntity<String> verify() throws PrimedRequestNotInvokedException;

    ResponseEntity<String> handleRequest(final HttpServletRequest request) throws RequestNotPrimedException;

}
