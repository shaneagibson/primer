
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
            accountService.receives(
                get()
                    .withUri("/user/123")
                    .withHeader("correlation-id", "001")))
        .thenReturn(
            response(200)
                .withContentType("application/json")
                .withBody("[{\"accountNumber\":\"1000001\",\"balance\":10000.00,\"currency\":\"GBP\"}," +
                           "{\"accountNumber\":\"1000002\",\"balance\":20000.00,\"currency\":\"AUD\"}]")
                .withHeader("user-id", "123"));

        when(
            exchangeRateService.receives(
                get()
                    .withUri("/exchange")
                    .withParameter("from", "GBP")
                    .withParameter("to", "USD")
                    .withHeader("correlation-id", "001")))
        .thenReturn(
            response(200)
                .withContentType("application/json")
                .withBody("1.52"));

        when(
            exchangeRateService.receives(
                get()
                    .withUri("/exchange")
                    .withParameter("from", "AUD")
                    .withParameter("to", "USD")
                    .withHeader("correlation-id", "001")))
        .thenReturn(
            response(200)
                .withContentType("application/json")
                .withBody("1.29"));

        // ACT

        ...

        // ASSERT

        verify(accountService, exchangeRateService);

    }

In addition to providing string-based matching, the primer can use JSON, XML or RegEx matchable values, as follows:

JSON:

        when(
            exchangeRateService.receives(
                post()
                    .withUri("/exchange")
                    .withBody(json("{ \"from\" : \"AUD\", \"to\" : \"USD\" }"))
                    .withHeader("correlation-id", "001")))
        .thenReturn(
            response(200)
                .withContentType("application/json")
                .withBody("1.29"));

XML:

        when(
            exchangeRateService.receives(
                post()
                    .withUri("/exchange")
                    .withBody(xml("<currencyPair><from>AUD</from><to>USD</to></currencyPair>"))
                    .withHeader("correlation-id", "001")))
        .thenReturn(
            response(200)
                .withContentType("application/json")
                .withBody("1.29"));

RegEx:

        when(
            exchangeRateService.receives(
                get()
                    .withUri("/exchange")
                    .withParameter("from", "AUD")
                    .withParameter("to", regex("([A-Z]{3})"))
                    .withHeader("correlation-id", "001")))
        .thenReturn(
            response(200)
                .withContentType("application/json")
                .withBody("1.29"));


In addition, the HTTP request and response can be primed using simpler, overloaded methods (rather than the slightly more verbose builder pattern):

        when(
            accountService.get("/user/123", headers(pair("correlation-id", "001")))
        .thenReturn(
            response(
                200,
                "application/json",
                "[{\"accountNumber\":\"1000001\",\"balance\":10000.00,\"currency\":\"GBP\"},{\"accountNumber\":\"1000002\",\"balance\":20000.00,\"currency\":\"AUD\"}]",
                headers(pair("user-id", "123"))));
