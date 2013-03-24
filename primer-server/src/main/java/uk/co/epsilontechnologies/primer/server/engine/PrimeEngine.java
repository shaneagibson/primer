package uk.co.epsilontechnologies.primer.server.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.epsilontechnologies.primer.server.error.PrimedRequestNotInvokedException;
import uk.co.epsilontechnologies.primer.server.error.RequestNotPrimedException;
import uk.co.epsilontechnologies.primer.client.model.PrimeRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.epsilontechnologies.primer.server.model.Request;
import uk.co.epsilontechnologies.primer.server.model.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class PrimeEngine implements IPrimeEngine {

    private final RequestMatcher requestMatcher;
    private final RequestParser requestParser;
    private final Map<Request,List<Response>> primes;

    @Autowired
    public PrimeEngine(RequestMatcher requestMatcher, RequestParser requestParser) {
        this.requestMatcher = requestMatcher;
        this.requestParser = requestParser;
        this.primes = new HashMap();
    }

    @Override
    public void prime(final PrimeRequest primeRequest) {
        final Request request = new Request(HttpMethod.valueOf(primeRequest.getHttpMethod()), primeRequest.getPathRegEx(), primeRequest.getBodyRegEx());
        final Response response = new Response(HttpStatus.valueOf(primeRequest.getResponseCode()), primeRequest.getResponseBody());
        if (primes.containsKey(request)) {
            final List<Response> responseList = primes.get(request);
            responseList.add(response);
            primes.put(request, responseList);
        } else {
            final ArrayList<Response> responseList = new ArrayList();
            responseList.add(response);
            primes.put(request, responseList);
        }
    }

    @Override
    public void reset() {
        this.primes.clear();
    }

    @Override
    public ResponseEntity<String> verify() throws PrimedRequestNotInvokedException {
        if (primes.isEmpty()) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new PrimedRequestNotInvokedException(primes);
        }
    }

    @Override
    public ResponseEntity<String> handleRequest(final HttpServletRequest request) throws RequestNotPrimedException {
        final HttpMethod requestMethod = requestParser.parseRequestMethod(request);
        final String requestPath = requestParser.parseRequestPath(request);
        final String requestBody = requestParser.parseRequestBody(request);
        for (final Request primedRequest : primes.keySet()) {
            if (requestMatcher.matches(primedRequest, requestMethod, requestPath, requestBody)) {
                final List<Response> responses = primes.get(primedRequest);
                final Response response = responses.size() == 1 ? primes.remove(primedRequest).get(0) : responses.remove(0);
                return new ResponseEntity(response.getBody(), response.getStatus());
            }
        }
        throw new RequestNotPrimedException(requestMethod, requestPath, requestBody);
    }

}