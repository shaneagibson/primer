
primer
======

ATDD utility for SOA projects, which allows the priming of RESTful resources though a simple java API.

By pointing your application to the primer-server webapp instead of the remote service you intend to mock, you can 'prime' individual requests, as follows:

    final Primer accountPrimer = new Primer("localhost", 8080, "/account");
    final Primer exchangeRatePrimer = new Primer("localhost", 8080, "/exchangerate");

    accountPrimer.prime(
            request("Get Accounts for User ID")
                    .withMethod(HttpMethod.GET)
                    .withPath("/user/123")
                    .withHeader("correlation-id", "001")
                    .build(),
            response()
                    .withStatus(HttpStatus.OK)
                    .withBody("[{\"accountNumber\":\"1000001\",\"balance\":10000.00,\"currency\":\"GBP\"}," +
                            "{\"accountNumber\":\"1000002\",\"balance\":20000.00,\"currency\":\"AUD\"}," +
                            "{\"accountNumber\":\"1000003\",\"balance\":20000.00,\"currency\":\"AUD\"}," +
                            "{\"accountNumber\":\"1000004\",\"balance\":2500.00,\"currency\":\"EUR\"}]")
                    .withHeader("user-id", "123")
                    .build());

    exchangeRatePrimer.prime(
            request("Get Exchange Rate for GBP/USD")
                    .withMethod(HttpMethod.GET)
                    .withPath("/")
                    .withHeader("correlation-id", "001")
                    .withRequestParameter("from", "GBP")
                    .withRequestParameter("to", "USD")
                    .build(),
            response()
                    .withStatus(HttpStatus.OK)
                    .withBody("1.52")
                    .build());


After invoking your service, you can then verify that all primed requests were invoked, as follows:

    accountPrimer.verify();
    exchangeRatePrimer.verify();
