package uk.co.epsilontechnologies.sample.web.resource;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import uk.co.epsilontechnologies.primer.client.Primer;

import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static uk.co.epsilontechnologies.primer.client.builder.RequestBuilder.request;
import static uk.co.epsilontechnologies.primer.client.builder.ResponseBuilder.response;

public class AccountResourceIntegrationTest {

    private RestTemplate restTemplate;
    private Primer accountPrimer;
    private Primer exchangeRatePrimer;

    public AccountResourceIntegrationTest() {
        this.restTemplate = new RestTemplate();
        this.accountPrimer = new Primer("localhost", 8080, "/account");
        this.exchangeRatePrimer = new Primer("localhost", 8080, "/exchangerate");
    }

    @After
    public void tearDown() {
        this.accountPrimer.reset();
        this.exchangeRatePrimer.reset();
    }

    @Test
    public void shouldGetAccountBalances() {

        // given

        accountPrimer.prime(
                request("Get Accounts for User ID")
                        .withMethod(HttpMethod.GET)
                        .withPath("/user/123")
                        .thenReturn(
                                response()
                                        .withStatus(HttpStatus.OK)
                                        .withBody("[{\"accountNumber\":\"1000001\",\"balance\":10000.00,\"currency\":\"GBP\"}," +
                                                "{\"accountNumber\":\"1000002\",\"balance\":20000.00,\"currency\":\"AUD\"}," +
                                                "{\"accountNumber\":\"1000003\",\"balance\":20000.00,\"currency\":\"AUD\"}," +
                                                "{\"accountNumber\":\"1000004\",\"balance\":2500.00,\"currency\":\"EUR\"}]"))
                        .build());

        exchangeRatePrimer.prime(
                request("Get Exchange Rate for GBP/USD")
                        .withMethod(HttpMethod.GET)
                        .withPath("/from/GBP/to/USD")
                        .thenReturn(
                                response()
                                        .withStatus(HttpStatus.OK)
                                        .withBody("1.52"))
                        .build());

        exchangeRatePrimer.prime(
                request("Get Exchange Rate for AUD/USD")
                        .withMethod(HttpMethod.GET)
                        .withPath("/from/AUD/to/USD")
                        .thenReturn(
                                response()
                                        .withStatus(HttpStatus.OK)
                                        .withBody("1.05"))
                        .build());

        exchangeRatePrimer.prime(
                request("Get Exchange Rate for AUD/USD")
                        .withMethod(HttpMethod.GET)
                        .withPath("/from/AUD/to/USD")
                        .thenReturn(
                                response()
                                        .withStatus(HttpStatus.OK)
                                        .withBody("1.02"))
                        .build());

        exchangeRatePrimer.prime(
                request("Get Exchange Rate for EUR/USD")
                        .withMethod(HttpMethod.GET)
                        .withPath("/from/EUR/to/USD")
                        .thenReturn(
                                response()
                                        .withStatus(HttpStatus.OK)
                                        .withBody("1.29"))
                        .build());



        // when

        final Map<String,Double> result = restTemplate.getForObject("http://localhost:8080/sample/account/user/{userid}/currency/{currency}", Map.class, 123L, "USD");

        // then

        accountPrimer.verify();
        exchangeRatePrimer.verify();
        assertEquals(4, result.size());
        assertTrue(result.containsKey("1000001"));
        assertEquals(15200.0, result.get("1000001"), 0);
        assertTrue(result.containsKey("1000002"));
        assertEquals(21000.0, result.get("1000002"), 0);
        assertTrue(result.containsKey("1000003"));
        assertEquals(20400.0, result.get("1000003"), 0);
        assertTrue(result.containsKey("1000004"));
        assertEquals(3225.0, result.get("1000004"), 0);
    }

}