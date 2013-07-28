package uk.co.epsilontechnologies.sample.web.resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import uk.co.epsilontechnologies.primer.client.jms.JmsMessageVerifier;
import uk.co.epsilontechnologies.primer.client.rest.RestPrimer;
import uk.co.epsilontechnologies.sample.configuration.ApplicationConfiguration;
import uk.co.epsilontechnologies.sample.configuration.WebConfiguration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static uk.co.epsilontechnologies.primer.client.jms.builder.MapMessageBuilder.mapMessage;
import static uk.co.epsilontechnologies.primer.client.jms.matcher.RegExMatchers.anyLong;
import static uk.co.epsilontechnologies.primer.client.rest.builder.RequestBuilder.request;
import static uk.co.epsilontechnologies.primer.client.rest.builder.ResponseBuilder.response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = { ApplicationConfiguration.class, WebConfiguration.class })
public class AccountResourceIT {

    private final RestPrimer accountService;
    private final RestPrimer exchangeRateService;
    private final JmsMessageVerifier logMessageVerifier;

    @Autowired
    private AccountResource accountResource;

    public AccountResourceIT() {
        this.accountService = new RestPrimer("localhost", 9010, "/account");
        this.exchangeRateService = new RestPrimer("localhost", 9010, "/exchangerate");
        this.logMessageVerifier = new JmsMessageVerifier("localhost", 61616, "log");
    }

    @After
    public void tearDown() {
        this.accountService.reset();
        this.exchangeRateService.reset();
        this.logMessageVerifier.reset();
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

        final Map<String,BigDecimal> result = accountResource.getBalancesForUser(123L, "USD");


        // ASSERT

        accountService.verify();
        exchangeRateService.verify();
        logMessageVerifier.verify(
                mapMessage("Start Request Log Message")
                        .with("correlationId", "001")
                        .with("message", "getting balances for user: 123 in currency: USD")
                        .with("timestamp", anyLong())
                        .build(),
                mapMessage("End Request Log Message")
                        .with("correlationId", "001")
                        .with("message", "total balance: 78950.00 for user: 123 in currency: USD")
                        .with("timestamp", anyLong())
                        .build());
        assertEquals(4, result.size());
        assertTrue(result.containsKey("1000001"));
        assertEquals(15200.0, result.get("1000001").doubleValue(), 0);
        assertTrue(result.containsKey("1000002"));
        assertEquals(21000.0, result.get("1000002").doubleValue(), 0);
        assertTrue(result.containsKey("1000003"));
        assertEquals(10500.0, result.get("1000003").doubleValue(), 0);
        assertTrue(result.containsKey("1000004"));
        assertEquals(32250.0, result.get("1000004").doubleValue(), 0);
    }

}