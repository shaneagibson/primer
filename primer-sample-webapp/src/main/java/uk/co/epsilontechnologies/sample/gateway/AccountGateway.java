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
import uk.co.epsilontechnologies.sample.model.Account;
import uk.co.epsilontechnologies.sample.service.CorrelationIdStore;

import java.util.Arrays;
import java.util.List;

@Component
public class AccountGateway implements IAccountGateway {

    private static final String USER_ID_HEADER = "user-id";

    private final RestTemplate restTemplate;
    private final CorrelationIdStore correlationIdStore;
    private final String accountBaseUrl;

    @Autowired
    public AccountGateway(
            final RestTemplate restTemplate,
            final CorrelationIdStore correlationIdStore,
            @Value("${account.base.url}") final String accountBaseUrl) {
        this.restTemplate = restTemplate;
        this.correlationIdStore = correlationIdStore;
        this.accountBaseUrl  = accountBaseUrl;
    }

    @Override
    public List<Account> getAccountsForUser(final Long userId) {
        final MultiValueMap<String,String> headers = new LinkedMultiValueMap();
        headers.put("correlation-id", Arrays.asList(correlationIdStore.getCorrelationId()));
        final ResponseEntity<Account[]> responseEntity = restTemplate.exchange(
                accountBaseUrl + "/user/{userid}",
                HttpMethod.GET,
                new HttpEntity(headers),
                Account[].class,
                userId);

        if (!responseEntity.getHeaders().containsKey(USER_ID_HEADER) && responseEntity.getHeaders().get(USER_ID_HEADER).equals(String.valueOf(userId))) {
            throw new RuntimeException("user-id Header Attribute not set");
        }

        return Arrays.asList(responseEntity.getBody());
    }

}