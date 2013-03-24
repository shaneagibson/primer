package uk.co.epsilontechnologies.sample.web.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.co.epsilontechnologies.sample.service.IAccountService;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountResource {

    private final IAccountService accountService;

    @Autowired
    public AccountResource(final IAccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/user/{userid}/currency/{currency}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value =  HttpStatus.OK)
    public Map<String,BigDecimal> getBalancesForUser(
            @PathVariable(value = "userid") final Long userId,
            @PathVariable(value = "currency") final String currency) {
        return accountService.getBalancesForUser(userId, currency);
    }

}