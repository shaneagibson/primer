package uk.co.epsilontechnologies.sample.gateway;

import uk.co.epsilontechnologies.sample.model.Account;

import java.util.List;

public interface IAccountGateway {

    List<Account> getAccountsForUser(final Long userId);

}
