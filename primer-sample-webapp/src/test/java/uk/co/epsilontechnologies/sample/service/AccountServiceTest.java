package uk.co.epsilontechnologies.sample.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.co.epsilontechnologies.sample.gateway.IAccountGateway;
import uk.co.epsilontechnologies.sample.gateway.IExchangeRateGateway;
import uk.co.epsilontechnologies.sample.model.Account;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountServiceTest {

    private AccountService underTest;

    @Mock
    private IAccountGateway mockAccountGateway;

    @Mock
    private IExchangeRateGateway mockExchangeRateGateway;

    @Before
    public void setUp() {
        initMocks(this);
        this.underTest = new AccountService(mockAccountGateway, mockExchangeRateGateway);
    }

    @Test
    public void shouldRetrieveAccountBalancesForUser() {

        // given
        final Long userId = 123L;
        final String currency = "USD";
        final BigDecimal audUsdExchangeRate = new BigDecimal("1.08");
        final BigDecimal gbpUsdExchangeRate = new BigDecimal("0.65");
        final Account account1 = new Account("0001", new BigDecimal("100.00"), "AUD");
        final Account account2 = new Account("0002", new BigDecimal("500.00"), "GBP");
        final List<Account> accounts = Arrays.asList(account1, account2);
        when(mockAccountGateway.getAccountsForUser(userId)).thenReturn(accounts);
        when(mockExchangeRateGateway.getExchangeRate("AUD", "USD")).thenReturn(audUsdExchangeRate);
        when(mockExchangeRateGateway.getExchangeRate("GBP", "USD")).thenReturn(gbpUsdExchangeRate);

        // when
        final Map<String,BigDecimal> result = this.underTest.getBalancesForUser(userId, currency);

        // then
        assertNotNull(result);
        assertTrue(result.containsKey("0001"));
        assertTrue(result.containsKey("0002"));
        assertEquals(new BigDecimal("108.00"), result.get("0001"));
        assertEquals(new BigDecimal("325.00"), result.get("0002"));

    }

}