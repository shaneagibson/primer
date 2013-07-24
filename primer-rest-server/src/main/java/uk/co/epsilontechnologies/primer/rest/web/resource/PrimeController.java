package uk.co.epsilontechnologies.primer.rest.web.resource;

import uk.co.epsilontechnologies.primer.rest.error.PrimedRequestNotInvokedException;
import uk.co.epsilontechnologies.primer.rest.error.RequestNotPrimedException;
import uk.co.epsilontechnologies.primer.client.rest.model.PrimeRequest;
import uk.co.epsilontechnologies.primer.rest.engine.IPrimeEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PrimeController {

    private final IPrimeEngine primeEngine;

    @Autowired
    public PrimeController(final IPrimeEngine primeEngine) {
        this.primeEngine = primeEngine;
    }

    @RequestMapping(
            value = "/primer/prime",
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void prime(@RequestBody final PrimeRequest primeRequest) {
        primeEngine.prime(primeRequest);
    }

    @RequestMapping(
            value = "/primer/reset",
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void reset() {
        primeEngine.reset();
    }

    @RequestMapping(
            value = "/primer/verify",
            method = RequestMethod.GET)
    public ResponseEntity<String> verify() throws PrimedRequestNotInvokedException {
        return primeEngine.verify();
    }

    @RequestMapping(value = { "/", "{path:(?!primer).*$}", "{path:(?!primer).*$}/**" })
    @ResponseBody
    public ResponseEntity<String> handleRequest(final HttpServletRequest request) throws RequestNotPrimedException {
        return primeEngine.handleRequest(request);
    }

    @ExceptionHandler(PrimedRequestNotInvokedException.class)
    @ResponseBody
    public ResponseEntity handlePrimedRequestNotInvokedException(final PrimedRequestNotInvokedException primedRequestNotInvokedException) {
        logException("Primed Request Not Invoked: "+primedRequestNotInvokedException.getPrimeRequestList(), primedRequestNotInvokedException);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestNotPrimedException.class)
    @ResponseBody
    public ResponseEntity handleRequestNotPrimedException(final RequestNotPrimedException requestNotPrimedException) {
        logException("Request Not Primed: "+requestNotPrimedException.getHttpMethod()+" "+requestNotPrimedException.getRequestPath()+" "+requestNotPrimedException.getRequestBody(), requestNotPrimedException);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private void logException(final String message, final Exception exception) {
        System.out.println("--------------------------------");
        System.out.println(message);
        exception.printStackTrace();
    }

}