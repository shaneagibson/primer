package uk.co.epsilontechnologies.primer.client.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.co.epsilontechnologies.primer.client.rest.model.PrimeRequest;
import uk.co.epsilontechnologies.primer.client.rest.model.Request;
import uk.co.epsilontechnologies.primer.client.rest.model.Response;

public class RestPrimer {

    private final String baseUrl;
    private final RestTemplate restTemplate;

    public RestPrimer(final String host, final int port, final String contextPath) {
        this.baseUrl = "http://" + host + ":" + port + (contextPath == null ? "" : contextPath);
        this.restTemplate = new RestTemplate();
    }

    public void prime(final Request request, final Response response) {
        final String url = baseUrl + "/primer/prime";
        restTemplate.postForObject(url, new PrimeRequest(request, response), String.class);
    }

    public void verify() {
        final String url = baseUrl + "/primer/verify";
        final ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException(response.getBody());
        }
    }

    public void reset() {
        final String url = baseUrl + "/primer/reset";
        restTemplate.postForObject(url, null, String.class);
    }

}