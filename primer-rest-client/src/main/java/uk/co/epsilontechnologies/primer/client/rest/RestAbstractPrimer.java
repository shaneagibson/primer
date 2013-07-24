package uk.co.epsilontechnologies.primer.client.rest;

import uk.co.epsilontechnologies.primer.client.AbstractPrimer;
import uk.co.epsilontechnologies.primer.client.rest.model.HttpCycle;
import uk.co.epsilontechnologies.primer.client.rest.model.PrimeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.co.epsilontechnologies.primer.client.rest.model.Request;
import uk.co.epsilontechnologies.primer.client.rest.model.Response;

public class RestAbstractPrimer implements AbstractPrimer<HttpCycle> {

    private final String baseUrl;
    private final RestTemplate restTemplate;

    /**
     * Constructs the primer client for the rest service.
     *
     * @param host
     * @param port
     * @param contextPath
     */
    public RestAbstractPrimer(final String host, final int port, final String contextPath) {
        this.baseUrl = "http://" + host + ":" + port + (contextPath == null ? "" : contextPath);
        this.restTemplate = new RestTemplate();
    }

    /**
     * Adds a primed request to the primer instance.
     *
     * @param request
     * @param response
     */
    public void prime(final Request request, final Response response) {
        this.prime(new HttpCycle(request, response));
    }

    /**
     * Adds a primed request to the primer instance.
     *
     * @param httpCycle
     */
    @Override
    public void prime(final HttpCycle httpCycle) {
        final String url = baseUrl + "/primer/prime";
        restTemplate.postForObject(url, new PrimeRequest(httpCycle.getRequest(), httpCycle.getResponse()), String.class);
    }

    /**
     * Verifies that all primed requests have been invoked.
     */
    @Override
    public void verify() {
        final String url = baseUrl + "/primer/verify";
        final ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException(response.getBody());
        }
    }

    /**
     * Resets the RestAbstractPrimer instance, removing any primed requests.
     */
    @Override
    public void reset() {
        final String url = baseUrl + "/primer/reset";
        restTemplate.postForObject(url, null, String.class);
    }

}