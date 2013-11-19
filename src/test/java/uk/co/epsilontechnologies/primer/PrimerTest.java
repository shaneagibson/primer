package uk.co.epsilontechnologies.primer;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static uk.co.epsilontechnologies.primer.PrimerStatics.*;

public class PrimerTest {

    private final Primer primer = new Primer("/test", 8500);

    private final RestTemplate restTemplate = new RestTemplate();

    @Before
    public void setUp() {
        this.primer.start();
    }

    @After
    public void tearDown() {
        this.primer.stop();
    }

    @Test
    public void shouldHandlePrimedPostRequest() {

        // arrange
        when(primer.post("/post", "{ \"key\" : \"value\" }", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "{ \"key\" : \"value\" }"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/post?key=value", HttpMethod.POST, new TestRequestCallback("{ \"key\" : \"value\" }"), new TestResponseExtractor());

        // assert
        assertEquals("application/json", "{ \"key\" : \"value\" }", result);
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedPutRequest() {

        // arrange
        when(primer.put("/put", "{ \"key\" : \"value\" }", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "{ \"key\" : \"value\" }"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/put?key=value", HttpMethod.PUT, new TestRequestCallback("{ \"key\" : \"value\" }"), new TestResponseExtractor());

        // assert
        assertEquals("application/json", "{ \"key\" : \"value\" }", result);
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedGetRequest() {

        // arrange
        when(primer.get("/get", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "{ \"key\" : \"value\" }"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/get?key=value", HttpMethod.GET, new TestRequestCallback(), new TestResponseExtractor());

        // assert
        assertEquals("application/json", "{ \"key\" : \"value\" }", result);
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedDeleteRequest() {

        // arrange
        when(primer.delete("/delete", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "{ \"key\" : \"value\" }"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/delete?key=value", HttpMethod.DELETE, new TestRequestCallback(), new TestResponseExtractor());

        // assert
        assertEquals("application/json", "{ \"key\" : \"value\" }", result);
        verify(primer);
    }


    @Test
    public void shouldHandlePrimedOptionsRequest() {

        // arrange
        when(primer.options("/options", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "{ \"key\" : \"value\" }"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/options?key=value", HttpMethod.OPTIONS, new TestRequestCallback(), new TestResponseExtractor());

        // assert
        assertEquals("application/json", "{ \"key\" : \"value\" }", result);
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedHeadRequest() {

        // arrange
        when(primer.head("/head", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "{ \"key\" : \"value\" }"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/head?key=value", HttpMethod.HEAD, new TestRequestCallback(), new TestResponseExtractor());

        // assert
        assertEquals("", result);
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedTraceRequest() {

        // arrange
        when(primer.trace("/trace", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "{ \"key\" : \"value\" }"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/trace?key=value", HttpMethod.TRACE, new TestRequestCallback(), new TestResponseExtractor());

        // assert
        assertEquals("application/json", "{ \"key\" : \"value\" }", result);
        verify(primer);
    }

    @Test
    public void shouldFailToHandleNonPrimedRequest() {

        // arrange
        when(primer.get("/blah")).thenReturn(response(200));

        try {

            // act
            restTemplate.execute("http://localhost:8500/test/get", HttpMethod.GET, new TestRequestCallback(), new TestResponseExtractor());

            fail("Expected HttpClientErrorException was not thrown");

        } catch (final HttpClientErrorException httpClientErrorException) {

            // assert
            assertEquals("Request Not Primed", httpClientErrorException.getResponseBodyAsString());
            assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
        }

    }

    @Test
    public void shouldHandlePrimedPostRequestWithRegEx() {

        // arrange
        when(primer.post("/post", "\\{ \"key\" : \"([a-z]{5})\" }", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "{ \"key\" : \"value\" }"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/post?key=value", HttpMethod.POST, new TestRequestCallback("{ \"key\" : \"value\" }"), new TestResponseExtractor());

        // assert
        assertEquals("application/json", "{ \"key\" : \"value\" }", result);
        verify(primer);
    }


    static class TestRequestCallback implements RequestCallback {

        private String body;

        TestRequestCallback() {
            this(null);
        }

        TestRequestCallback(final String body) {
            this.body = body;
        }

        @Override
        public void doWithRequest(final ClientHttpRequest clientHttpRequest) throws IOException {
            if (body != null) {
                clientHttpRequest.getBody().write(body.getBytes());
            }
            clientHttpRequest.getHeaders().put("key", Arrays.asList("value"));
        }

    }

    static class TestResponseExtractor implements ResponseExtractor<String> {

        @Override
        public String extractData(ClientHttpResponse clientHttpResponse) throws IOException {
            return IOUtils.toString(clientHttpResponse.getBody());
        }

    }

}
