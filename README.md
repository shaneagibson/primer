
primer
======

ATDD utility for SOA projects, which allows the priming of RESTful resources and JMS message queues though a simple java API.

rest
------

By pointing your application to the primer-server webapp instead of the remote service you intend to mock, you can 'prime' individual requests, as follows:

    final RestPrimer accountPrimer = new RestPrimer("localhost", 8080, "/account");
    final RestPrimer exchangeRatePrimer = new RestPrimer("localhost", 8080, "/exchangerate");

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

    logPrimer.prime();
    accountPrimer.verify();


jms
------

For any JMS queue to which your application is expected to publish messages, you can create a JmsMessageVerifier:

    final JmsMessageVerifier logMessageVerifier = new JmsMessageVerifier("localhost", 61616, "log");

After invoking your service, you can then verify that the expected messages were all issued, as follows:

    logMessageVerifier.verify(
            mapMessage("Start Request Log Message")
                    .with("correlationId", "001")
                    .with("message", "getting balances for user: 123 in currency: USD")
                    .with("timestamp", anyLong())
                    .build(),
            mapMessage("End Request Log Message")
                    .with("correlationId", "001")
                    .with("message", "total balance: 78950.00 for user: 123 in currency: USD")
                    .with("timestamp", anyLong())
                    .build());
