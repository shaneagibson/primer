package uk.co.epsilontechnologies.sample.web.resource;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uk.co.epsilontechnologies.primer.client.Primer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static uk.co.epsilontechnologies.primer.client.builder.RequestBuilder.request;
import static uk.co.epsilontechnologies.primer.client.builder.ResponseBuilder.response;

public class AccountResourceIntegrationTest {

    private RestTemplate restTemplate;
    private Primer accountService;
    private Primer exchangeRateService;

    public AccountResourceIntegrationTest() {
        this.restTemplate = new RestTemplate();
        this.accountService = new Primer("localhost", 8080, "/account");
        this.exchangeRateService = new Primer("localhost", 8080, "/exchangerate");
    }

    @After
    public void tearDown() {
        this.accountService.reset();
        this.exchangeRateService.reset();
    }

    @Test
    public void shouldGetAccountBalances() {

        // ARRANGE

        accountService.prime(
                request("Get Accounts for User ID")
                        .withMethod(HttpMethod.GET)
                        .withPath("/user/123")
                        .withHeader("correlation-id", "001")
                        .build(),
                response()
                        .withStatus(HttpStatus.OK)
                        .withBody("[{\"accountNumber\":\"1000001\",\"balance\":10000.00,\"currency\":\"GBP\"}," +
                                   "{\"accountNumber\":\"1000002\",\"balance\":20000.00,\"currency\":\"AUD\"}," +
                                   "{\"accountNumber\":\"1000003\",\"balance\":10000.00,\"currency\":\"AUD\"}," +
                                   "{\"accountNumber\":\"1000004\",\"balance\":25000.00,\"currency\":\"EUR\"}]")
                        .withHeader("user-id", "123")
                        .build());

        exchangeRateService.prime(
                request("Get Exchange Rate for GBP / USD")
                        .withMethod(HttpMethod.GET)
                        .withPath("/")
                        .withHeader("correlation-id", "001")
                        .withRequestParameter("from", "GBP")
                        .withRequestParameter("to", "USD")
                        .build(),
                response()
                        .withStatus(HttpStatus.OK)
                        .withBody("1.52")
                        .build());

        exchangeRateService.prime(
                request("Get Exchange Rate for AUD / USD")
                        .withMethod(HttpMethod.GET)
                        .withPath("/")
                        .withHeader("correlation-id", "001")
                        .withRequestParameter("from", "AUD")
                        .withRequestParameter("to", "USD")
                        .build(),
                response()
                        .withStatus(HttpStatus.OK)
                        .withBody("1.05")
                        .build());

        // for illustrative purposes only - ordinarily there would be no need to invoke this service twice
        exchangeRateService.prime(
                request("Get Exchange Rate for AUD / USD")
                        .withMethod(HttpMethod.GET)
                        .withPath("/")
                        .withHeader("correlation-id", "001")
                        .withRequestParameter("from", "AUD")
                        .withRequestParameter("to", "USD")
                        .build(),
                response()
                        .withStatus(HttpStatus.OK)
                        .withBody("1.05")
                        .build());

        exchangeRateService.prime(
                request("Get Exchange Rate for EUR / USD")
                        .withMethod(HttpMethod.GET)
                        .withPath("/")
                        .withHeader("correlation-id", "001")
                        .withRequestParameter("from", "EUR")
                        .withRequestParameter("to", "USD")
                        .build(),
                response()
                        .withStatus(HttpStatus.OK)
                        .withBody("1.29")
                        .build());

        final MultiValueMap headers = new LinkedMultiValueMap();
        headers.put("correlation-id", Arrays.asList("001"));


        // ACT

        final ResponseEntity<Map> responseEntity = restTemplate.exchange(
                "http://localhost:8080/sample/account/user/{userid}/currency/{currency}",
                HttpMethod.GET,
                new HttpEntity(headers),
                Map.class,
                123L,
                "USD");


        // ASSERT

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final Map<String,Double> result = responseEntity.getBody();
        accountService.verify();
        exchangeRateService.verify();
        assertEquals(4, result.size());
        assertTrue(result.containsKey("1000001"));
        assertEquals(15200.0, result.get("1000001"), 0);
        assertTrue(result.containsKey("1000002"));
        assertEquals(21000.0, result.get("1000002"), 0);
        assertTrue(result.containsKey("1000003"));
        assertEquals(10500.0, result.get("1000003"), 0);
        assertTrue(result.containsKey("1000004"));
        assertEquals(32250.0, result.get("1000004"), 0);
    }

}