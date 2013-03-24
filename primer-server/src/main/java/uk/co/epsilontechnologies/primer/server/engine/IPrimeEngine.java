package uk.co.epsilontechnologies.primer.server.engine;

import org.springframework.http.ResponseEntity;
import uk.co.epsilontechnologies.primer.client.model.PrimeRequest;
import uk.co.epsilontechnologies.primer.server.error.PrimedRequestNotInvokedException;
import uk.co.epsilontechnologies.primer.server.error.RequestNotPrimedException;

import javax.servlet.http.HttpServletRequest;

public interface IPrimeEngine {

    void prime(final PrimeRequest primeRequest);

    void reset();

    ResponseEntity<String> verify() throws PrimedRequestNotInvokedException;

    ResponseEntity<String> handleRequest(final HttpServletRequest request) throws RequestNotPrimedException;

}
