package uk.co.epsilontechnologies.sample.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uk.co.epsilontechnologies.sample.service.CorrelationIdStore;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class ExchangeRateGateway implements IExchangeRateGateway {

    private final RestTemplate restTemplate;
    private final CorrelationIdStore correlationIdStore;
    private final String exchangeRateBaseUrl;

    @Autowired
    public ExchangeRateGateway(
            final RestTemplate restTemplate,
            final CorrelationIdStore correlationIdStore,
            @Value("${exchangerate.base.url}") final String exchangeRateBaseUrl) {
        this.restTemplate = restTemplate;
        this.correlationIdStore = correlationIdStore;
        this.exchangeRateBaseUrl = exchangeRateBaseUrl;
    }

    @Override
    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        final MultiValueMap<String,String> headers = new LinkedMultiValueMap();
        headers.put("correlation-id", Arrays.asList(correlationIdStore.getCorrelationId()));
        final ResponseEntity<BigDecimal> responseEntity = restTemplate.exchange(
                exchangeRateBaseUrl + "?from={fromcurrency}&to={tocurrency}",
                HttpMethod.GET,
                new HttpEntity(headers),
                BigDecimal.class,
                fromCurrency,
                toCurrency);
        return responseEntity.getBody();
    }
}
