package uk.co.epsilontechnologies.sample.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class ExchangeRateGateway implements IExchangeRateGateway {

    private final RestTemplate restTemplate;
    private final String exchangeRateBaseUrl;

    @Autowired
    public ExchangeRateGateway(
            final RestTemplate restTemplate,
            @Value("${exchangerate.base.url}") final String exchangeRateBaseUrl) {
        this.restTemplate = restTemplate;
        this.exchangeRateBaseUrl = exchangeRateBaseUrl;
    }

    @Override
    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        return this.restTemplate.getForObject(
                exchangeRateBaseUrl + "/from/{fromcurrency}/to/{tocurrency}",
                BigDecimal.class,
                fromCurrency,
                toCurrency);
    }
}
