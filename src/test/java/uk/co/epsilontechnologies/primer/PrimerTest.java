package uk.co.epsilontechnologies.primer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static uk.co.epsilontechnologies.primer.PrimerStatics.*;

/**
 * Test case for key Primer functionality.
 *
 * @author Shane Gibson
 */
public class PrimerTest {

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
    public void shouldHandlePrimedPostRequest() {

        // arrange
        when(primer.post("/post", "{ \"key\" : \"value\" }", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/json", "[ \"success\" ]", headers(pair("response-header-key", "response-header-value"))));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/post?parameter-key=parameter-value", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_JSON, "{ \"key\" : \"value\" }"), String.class);

        // assert
        assertEquals("[ \"success\" ]", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedPutRequest() {

        // arrange
        when(primer.put("/put", "{ \"key\" : \"value\" }", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/json", "[ \"success\" ]", headers(pair("response-header-key", "response-header-value"))));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/put?parameter-key=parameter-value", HttpMethod.PUT, newRequestEntity(MediaType.APPLICATION_JSON, "{ \"key\" : \"value\" }"), String.class);

        // assert
        assertEquals("[ \"success\" ]", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedGetRequest() {

        // arrange
        when(primer.get("/get", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/json", "[ \"success\" ]", headers(pair("response-header-key", "response-header-value"))));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/get?parameter-key=parameter-value", HttpMethod.GET, newRequestEntity(), String.class);

        // assert
        assertEquals("[ \"success\" ]", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedDeleteRequest() {

        // arrange
        when(primer.delete("/delete", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/json", "[ \"success\" ]", headers(pair("response-header-key", "response-header-value"))));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/delete?parameter-key=parameter-value", HttpMethod.DELETE, newRequestEntity(), String.class);

        // assert
        assertEquals("[ \"success\" ]", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
        verify(primer);
    }


    @Test
    public void shouldHandlePrimedOptionsRequest() {

        // arrange
        when(primer.options("/options", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/json", "[ \"success\" ]", headers(pair("response-header-key", "response-header-value"))));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/options?parameter-key=parameter-value", HttpMethod.OPTIONS, newRequestEntity(), String.class);

        // assert
        assertEquals("[ \"success\" ]", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedHeadRequest() {

        // arrange
        when(primer.head("/head", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/json", "[ \"success\" ]", headers(pair("response-header-key", "response-header-value"))));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/head?parameter-key=parameter-value", HttpMethod.HEAD, newRequestEntity(), String.class);

        // assert
        assertEquals("", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedTraceRequest() {

        // arrange
        when(primer.trace("/trace", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/json", "[ \"success\" ]", headers(pair("response-header-key", "response-header-value"))));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/trace?parameter-key=parameter-value", HttpMethod.TRACE, newRequestEntity(), String.class);

        // assert
        assertEquals("[ \"success\" ]", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
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
        when(primer.get("/geet")).thenReturn(response(200));

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
        when(primer.get("/get")).thenReturn(response(200));

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
        when(primer.get("/get", headers(pair("request-header-key", "request-header-value-x")))).thenReturn(response(200));

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
        when(primer.get("/get", parameters(pair("request-parameter-key", "request-parameter-value-x")))).thenReturn(response(200));

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
        when(primer.post("/post", "blah \"([a-z]{5})\"", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/json", "[ \"success\" ]", headers(pair("response-header-key", "response-header-value"))));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/post?parameter-key=parameter-value", HttpMethod.POST, newRequestEntity(MediaType.TEXT_PLAIN, "blah \"value\""), String.class);

        // assert
        assertEquals("[ \"success\" ]", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedRequestWithSimilarXml() {

        // arrange
        when(primer.post("/post", "<blah><one/><two/></blah>", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/xml", "<success/>", headers(pair("response-header-key", "response-header-value"))));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/post?parameter-key=parameter-value", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_XML, "<blah> <two/> <one/> </blah>"), String.class);

        // assert
        assertEquals("<success/>", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
        verify(primer);
    }

    @Test
    public void shouldNotHandlePrimedRequestWithDissimilarXml() {

        // arrange
        when(primer.post("/post", "<blah><one/><two/></blah>", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/xml", "<success/>", headers(pair("response-header-key", "response-header-value"))));

        try {

            // act
            restTemplate.exchange("http://localhost:8082/test/post?parameter-key=parameter-value", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_XML, "<blah> <two/> <three/> </blah>"), String.class);

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
        when(primer.post("/post", "{ \"one\" : \"a\", \"two\" : \"b\" }", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/json", "[ \"success\" ]", headers(pair("response-header-key", "response-header-value"))));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/test/post?parameter-key=parameter-value", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_JSON, "{\"two\":\"b\",\"one\":\"a\"}"), String.class);

        // assert
        assertEquals("[ \"success\" ]", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response-header-value", result.getHeaders().get("response-header-key").get(0));
        verify(primer);
    }

    @Test
    public void shouldNotHandlePrimedRequestWithDissimilarJson() {

        // arrange
        when(primer.post("/post", "{ \"one\" : \"a\", \"two\" : \"b\" }", parameters(pair("parameter-key", "parameter-value")), headers(pair("request-header-key", "request-header-value")))).thenReturn(response(200, "application/json", "[ \"success\" ]", headers(pair("response-header-key", "response-header-value"))));

        try {

            // act
            restTemplate.exchange("http://localhost:8082/test/post?parameter-key=parameter-value", HttpMethod.POST, newRequestEntity(MediaType.APPLICATION_JSON, "{\"three\":\"c\",\"one\":\"a\"}"), String.class);

            fail("Expected HttpClientErrorException was not thrown");

        } catch (final HttpClientErrorException httpClientErrorException) {

            // assert
            assertEquals("Request Not Primed", httpClientErrorException.getResponseBodyAsString());
            assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
        }

    }

    @Test
    public void shouldInitializePrimerViaAnnotation() {

        // arrange
        initPrimers(this);
        primable.start();
        when(primable.get("/get")).thenReturn(response(200));

        // act
        final ResponseEntity<String> result = restTemplate.exchange("http://localhost:8081/test/get", HttpMethod.GET, newRequestEntity(), String.class);

        // assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        primable.stop();
        verify(primable);
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
