package uk.co.epsilontechnologies.sample.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.co.epsilontechnologies.sample.model.Account;

import java.util.Arrays;
import java.util.List;

@Component
public class AccountGateway implements IAccountGateway {

    private final RestTemplate restTemplate;
    private final String accountBaseUrl;

    @Autowired
    public AccountGateway(
            final RestTemplate restTemplate,
            @Value("${account.base.url}") final String accountBaseUrl ) {
        this.restTemplate = restTemplate;
        this.accountBaseUrl  = accountBaseUrl;
    }

    @Override
    public List<Account> getAccountsForUser(Long userId) {
        try {
        return Arrays.asList(restTemplate.getForObject(accountBaseUrl + "/user/{userid}", Account[].class, userId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}