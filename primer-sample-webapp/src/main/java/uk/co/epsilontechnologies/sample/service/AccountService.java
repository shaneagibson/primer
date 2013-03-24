package uk.co.epsilontechnologies.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.epsilontechnologies.sample.gateway.IAccountGateway;
import uk.co.epsilontechnologies.sample.gateway.IExchangeRateGateway;
import uk.co.epsilontechnologies.sample.model.Account;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService implements IAccountService {

    private final IAccountGateway accountGateway;
    private final IExchangeRateGateway exchangeRateGateway;

    @Autowired
    public AccountService(
            final IAccountGateway accountGateway,
            final IExchangeRateGateway exchangeRateGateway) {
        this.accountGateway = accountGateway;
        this.exchangeRateGateway = exchangeRateGateway;
    }

    @Override
    public Map<String,BigDecimal> getBalancesForUser(final Long userId, final String currency) {

        final Map<String,BigDecimal> balances = new HashMap();

        final List<Account> accounts = accountGateway.getAccountsForUser(userId);

        for (final Account account : accounts) {

            final BigDecimal exchangeRate = exchangeRateGateway.getExchangeRate(account.getCurrency(), currency);

            final BigDecimal balance = exchangeRate.multiply(account.getBalance());

            balances.put(account.getAccountNumber(), balance);
        }

        return balances;
    }

}
