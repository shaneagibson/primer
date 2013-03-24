package uk.co.epsilontechnologies.sample.service;

import java.math.BigDecimal;
import java.util.Map;

public interface IAccountService {

    Map<String,BigDecimal> getBalancesForUser(final Long userId, final String currency);

}
