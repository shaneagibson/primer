package uk.co.epsilontechnologies.sample.gateway;

import java.math.BigDecimal;

public interface IExchangeRateGateway {

    BigDecimal getExchangeRate(final String fromCurrency, final String toCurrency);

}
