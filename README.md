
primer
======

Primer is a test utility for SOA projects, which allows the priming of RESTful resources though a simple java API.

Primer allows you to create prime instances one of two ways, both of which will be familiar to users of mockito:

    @Primable(contextPath = "/account", port = 9011)
    private Primer accountService;

    @Primable(contextPath = "/exchangerate", port = 9010)
    private Primer exchangeRateService;

    @Before
    public void setUp() {
        initPrimers(this);
    }

Or

    private final Primer accountService = new Primer("/account", 9011);
    private final Primer exchangeRateService = new Primer("/exchangerate", 9010);


Using a primable instance in a test is as follows:

    @Before
    public void setUp() {
        this.accountService.start();
        this.exchangeRateService.start();
    }

    @After
    public void tearDown() {
        this.accountService.stop();
        this.exchangeRateService.stop();
    }

    @Test
    public void test() {

        // ARRANGE

        when(
            accountService.get(
                "/user/123",
                headers(pair("correlation-id", "001"))))
        .thenReturn(
            response(
                200,
                "application/json",
                "[{\"accountNumber\":\"1000001\",\"balance\":10000.00,\"currency\":\"GBP\"}," +
                 "{\"accountNumber\":\"1000002\",\"balance\":20000.00,\"currency\":\"AUD\"}]",
                headers(pair("user-id", "123"))));

        when(
            exchangeRateService.get(
                "/exchange",
                parameters(pair("from", "GBP"), pair("to", "USD")),
                headers(pair("correlation-id", "001"))))
        .thenReturn(
            response(
                200,
                "application/json",
                "1.52"));
        when(
            exchangeRateService.get(
                "/exchange",
                parameters(pair("from", "AUD"), pair("to", "USD")),
                headers(pair("correlation-id", "001"))))
        .thenReturn(
            response(
                200,
                "application/json",
                "1.29"));

        // ACT

        ...

        // ASSERT

        verify(accountService);
        verify(exchangeRateService);

    }


TODO
------

 - Upgrade to use Netty and Java 7
 - Add javadoc