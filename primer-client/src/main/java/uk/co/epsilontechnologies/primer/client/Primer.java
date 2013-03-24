package uk.co.epsilontechnologies.primer.client;

import uk.co.epsilontechnologies.primer.client.model.PrimeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Primer {

    private final String baseUrl;
    private final RestTemplate restTemplate;

    /**
     * Constructs the primer client for the service.
     *
     * @param host
     * @param port
     * @param contextPath
     */
    public Primer(final String host, final int port, final String contextPath) {
        this.baseUrl = "http://" + host + ":" + port + (contextPath == null ? "" : contextPath);
        this.restTemplate = new RestTemplate();
    }

    /**
     * Adds a primed request to the primer instance.
     * @param primeRequest
     */
    public void prime(final PrimeRequest primeRequest) {
        final String url = baseUrl + "/primer/prime";
        restTemplate.postForObject(url, primeRequest, String.class);
    }

    /**
     * Verifies that all primed requests have been invoked.
     *
     * @throws IllegalStateException - at least one primed request was not invoked.
     */
    public void verify() {
        final String url = baseUrl + "/primer/verify";
        final ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException(response.getBody());
        }
    }

    /**
     * Resets the Primer instance, removing any primed requests.
     */
    public void reset() {
        final String url = baseUrl + "/primer/reset";
        restTemplate.postForObject(url, null, String.class);
    }

}