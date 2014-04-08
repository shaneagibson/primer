package uk.co.epsilontechnologies.primer;

import org.junit.*;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uk.co.epsilontechnologies.primer.domain.Producer;

import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static uk.co.epsilontechnologies.primer.PrimerStatics.*;
import static uk.co.epsilontechnologies.primer.domain.JsonMatchable.json;
import static uk.co.epsilontechnologies.primer.domain.RegExMatchable.regex;
import static uk.co.epsilontechnologies.primer.domain.RequestBuilder.*;
import static uk.co.epsilontechnologies.primer.domain.ResponseBuilder.response;
import static uk.co.epsilontechnologies.primer.domain.StringMatchable.eq;
import static uk.co.epsilontechnologies.primer.domain.XmlMatchable.xml;

/**
 * Test case for key Primer functionality.
 *
 * @author Shane Gibson
 */
public class PrimerITest {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Primable(contextPath = "/test", port = 8081)
    private Primer primable;

    private static final Primer primer = new Primer("/test", 8082);

    @BeforeClass
    public static void setUpClass() {
        primer.start();
    }

    @AfterClass
    public static void tearDownClass() {
        primer.stop();
    }

    @After
    public void tearDown() {
        primer.reset();
    }

    @Test
    public void shouldHandlePrimedRequestWithParametersAndHeaders() {

        // arrange
        when(primer
                .receives(
                        post()
                                .withUri("/post")
                                .withBody(json("{ \"key\" : \"value\" }"))
                                .withParameter("parameter-key", eq("parameter-value"))
                                .withHeader("request-header-key", eq("request-header-value"))))
                .thenReturn(
                        response(200)
                                .withContentType("application/json")
                                .withBody("[ \"success\" ]")
                                .withHeader("response-header-key", "response-header-value"));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/post?parameter-key=parameter-value", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_JSON, "{ \"key\" : \"value\" }"), String.class);

        // assert
        assertEquals("[ \"success\" ]", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedPostRequest() {

        // arrange
        when(primer.receives(post().withUri("/post").withBody(json("{ \"key\" : \"value\" }")))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/post", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_JSON, "{ \"key\" : \"value\" }"), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedPutRequest() {

        // arrange
        when(primer.receives(put().withUri("/put").withBody(json("{ \"key\" : \"value\" }")))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/put", HttpMethod.PUT, newRequestEntity(MediaType.APPLICATION_JSON, "{ \"key\" : \"value\" }"), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedGetRequest() {

        // arrange
        when(primer.receives(get().withUri("/get"))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/get", HttpMethod.GET, newRequestEntity(), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedDeleteRequest() {

        // arrange
        when(primer.receives(delete().withUri("/delete"))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/delete", HttpMethod.DELETE, newRequestEntity(), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(primer);
    }


    @Test
    public void shouldHandlePrimedOptionsRequest() {

        // arrange
        when(primer.receives(options().withUri("/options"))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/options", HttpMethod.OPTIONS, newRequestEntity(), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedHeadRequest() {

        // arrange
        when(primer.receives(head().withUri("/head"))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/head", HttpMethod.HEAD, newRequestEntity(), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedTraceRequest() {

        // arrange
        when(primer.receives(trace().withUri("/trace"))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/trace", HttpMethod.TRACE, newRequestEntity(), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldFailToHandleNonPrimedRequest() {

        try {

            // act
            restTemplate.exchange("http://localhost:8082/test/get", HttpMethod.GET, newRequestEntity(), String.class);

            fail("Expected HttpClientErrorException was not thrown");

        } catch (final HttpClientErrorException httpClientErrorException) {

            // assert
            assertEquals("Request Not Primed", httpClientErrorException.getResponseBodyAsString());
            assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
        }

    }

    @Test
    public void shouldFailToHandleNonPrimedRequestByIncorrectUri() {

        // arrange
        when(primer.receives(get().withUri("/geet"))).thenReturn(response(200));

        try {

            // act
            restTemplate.exchange("http://localhost:8082/test/get", HttpMethod.GET, newRequestEntity(), String.class);

            fail("Expected HttpClientErrorException was not thrown");

        } catch (final HttpClientErrorException httpClientErrorException) {

            // assert
            assertEquals("Request Not Primed", httpClientErrorException.getResponseBodyAsString());
            assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
        }

    }

    @Test
    public void shouldFailToHandleNonPrimedRequestByIncorrectMethod() {

        // arrange
        when(primer.receives(get().withUri("/get"))).thenReturn(response(200));

        try {

            // act
            restTemplate.exchange("http://localhost:8082/test/get", HttpMethod.DELETE, newRequestEntity(), String.class);

            fail("Expected HttpClientErrorException was not thrown");

        } catch (final HttpClientErrorException httpClientErrorException) {

            // assert
            assertEquals("Request Not Primed", httpClientErrorException.getResponseBodyAsString());
            assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
        }

    }

    @Test
    public void shouldFailToHandleNonPrimedRequestByIncorrectHeader() {

        // arrange
        when(primer.receives(get().withUri("/get").withHeader("request-header-key", "request-header-value-x"))).thenReturn(response(200));

        try {

            // act
            restTemplate.exchange("http://localhost:8082/test/get", HttpMethod.GET, newRequestEntity(), String.class);

            fail("Expected HttpClientErrorException was not thrown");

        } catch (final HttpClientErrorException httpClientErrorException) {

            // assert
            assertEquals("Request Not Primed", httpClientErrorException.getResponseBodyAsString());
            assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
        }

    }

    @Test
    public void shouldFailToHandleNonPrimedRequestByIncorrectParameter() {

        // arrange
        when(primer.receives(get().withUri("/get").withParameter("request-parameter-key", "request-parameter-value-x"))).thenReturn(response(200));

        try {

            // act
            restTemplate.exchange("http://localhost:8082/test/get?request-parameter-key=request-parameter-value", HttpMethod.GET, newRequestEntity(), String.class);

            fail("Expected HttpClientErrorException was not thrown");

        } catch (final HttpClientErrorException httpClientErrorException) {

            // assert
            assertEquals("Request Not Primed", httpClientErrorException.getResponseBodyAsString());
            assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
        }

    }

    @Test
    public void shouldHandlePrimedRequestWithRegExInBody() {

        // arrange
        when(primer.receives(post().withUri("/post").withBody(regex("blah \"([a-z]{5})\"")))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/post", HttpMethod.POST, newRequestEntity(MediaType.TEXT_PLAIN, "blah \"value\""), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedRequestWithSimilarXml() {

        // arrange
        when(primer.receives(post().withUri("/post").withBody(xml("<blah><one/><two/></blah>")))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/post", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_XML, "<blah> <two/> <one/> </blah>"), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldNotHandlePrimedRequestWithDissimilarXml() {

        // arrange
        when(primer.receives(post().withUri("/post").withBody(xml("<blah><one/><two/></blah>")))).thenReturn(response(200));

        try {

            // act
            restTemplate.exchange("http://localhost:8082/test/post", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_XML, "<blah> <two/> <three/> </blah>"), String.class);

            fail("Expected HttpClientErrorException was not thrown");

        } catch (final HttpClientErrorException httpClientErrorException) {

            // assert
            assertEquals("Request Not Primed", httpClientErrorException.getResponseBodyAsString());
            assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
        }

    }

    @Test
    public void shouldHandlePrimedRequestWithSimilarJson() {

        // arrange
        when(primer.receives(post().withUri("/post").withBody(json("{ \"one\" : \"a\", \"two\" : \"b\" }")))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/post", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_JSON, "{\"two\":\"b\",\"one\":\"a\"}"), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldNotHandlePrimedRequestWithDissimilarJson() {

        // arrange
        when(primer.receives(post().withUri("/post").withBody(json("{ \"one\" : \"a\", \"two\" : \"b\" }")))).thenReturn(response(200));

        try {

            // act
            restTemplate.exchange("http://localhost:8082/test/post", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_JSON, "{\"three\":\"c\",\"one\":\"a\"}"), String.class);

            fail("Expected HttpClientErrorException was not thrown");

        } catch (final HttpClientErrorException httpClientErrorException) {

            // assert
            assertEquals("Request Not Primed", httpClientErrorException.getResponseBodyAsString());
            assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
        }

    }

    @Test
    public void shouldFailToVerifyWhenPrimedRequestNotInvoked() {

        // arrange
        when(primer.receives(post().withUri("/get"))).thenReturn(response(200));

        try {

            // act
            verify(primer);

            fail("Expected IllegalStateException was not thrown");

        } catch (final IllegalStateException illegalStateException) {

            // assert
            assertEquals("Primed Requests Not Invoked", illegalStateException.getMessage());
        }
    }

    @Test
    public void shouldAllowMultiplePrimedResponsesForSameRequestAndDifferentThenReturns() {

        // arrange
        when(primer.receives(get().withUri("/get"))).thenReturn(response(200));
        when(primer.receives(get().withUri("/get"))).thenReturn(response(201));
        final ResponseEntity<String> okResponseEntity = restTemplate.exchange("http://localhost:8082/test/get", HttpMethod.GET, newRequestEntity(), String.class);
        assertEquals(HttpStatus.OK, okResponseEntity.getStatusCode());

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/get", HttpMethod.GET, newRequestEntity(), String.class);

        // assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldAllowMultiplePrimedResponsesForSameRequestAndSameThenReturns() {

        // arrange
        when(primer.receives(get().withUri("/get"))).thenReturn(response(200), response(201));
        final ResponseEntity<String> okResponseEntity = restTemplate.exchange("http://localhost:8082/test/get", HttpMethod.GET, newRequestEntity(), String.class);
        assertEquals(HttpStatus.OK, okResponseEntity.getStatusCode());

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/get", HttpMethod.GET, newRequestEntity(), String.class);

        // assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldAllowMultiplePrimedResponsesForDifferentRequests() {

        // arrange
        when(primer.receives(get().withUri("/getA"))).thenReturn(response(200));
        when(primer.receives(get().withUri("/getB"))).thenReturn(response(201));
        final ResponseEntity<String> okResponseEntity = restTemplate.exchange("http://localhost:8082/test/getA", HttpMethod.GET, newRequestEntity(), String.class);
        assertEquals(HttpStatus.OK, okResponseEntity.getStatusCode());

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/getB", HttpMethod.GET, newRequestEntity(), String.class);

        // assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(primer);
    }

    @Test
    public void shouldInitializePrimerViaAnnotation() {

        // arrange
        initPrimers(this);
        primable.start();
        when(primable.receives(get().withUri("/get"))).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8081/test/get", HttpMethod.GET, newRequestEntity(), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        primable.stop();
        verify(primable);
    }

    @Test
    public void shouldHandlePrimedRequestWhereResponseContainsRuntimeProducer() {

        // arrange
        final Producer<String> responseBodyProducer = new Producer<String>() {
            @Override
            public String produce() {
                return "{ time : " + System.currentTimeMillis() + " }";
            }
        };
        when(primer
                .receives(get().withUri("/get")))
                .thenReturn(response(200).withBody(responseBodyProducer));

        // act
        final ResponseEntity<String> result = restTemplate.getForEntity("http://localhost:8082/test/get", String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody().matches(("\\{ time : [0-9]{13} \\}")));
        verify(primer);
    }

    static HttpEntity<String> newRequestEntity() {
        return newRequestEntity(null, null);
    }

    static HttpEntity<String> newRequestEntity(final MediaType contentType, final String requestBody) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("request-header-key", Arrays.asList("request-header-value"));
        if (contentType != null) {
            httpHeaders.setContentType(contentType);
        }
        return new HttpEntity<>(requestBody, httpHeaders);
    }

}
