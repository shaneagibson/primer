package uk.co.epsilontechnologies.primer;

import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    private static final Primer primer = new Primer("/test", 8500);

    private static final RestTemplate restTemplate = new RestTemplate();

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
    public void shouldHandlePrimedRequestWithRegExInBody() {

        // arrange
        when(primer.post("/post", "\\{ \"key\" : \"([a-z]{5})\" }", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "{ \"key\" : \"value\" }"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/post?key=value", HttpMethod.POST, new TestRequestCallback("{ \"key\" : \"value\" }"), new TestResponseExtractor());

        // assert
        assertEquals("application/json", "{ \"key\" : \"value\" }", result);
        verify(primer);
    }

    @Test
    public void shouldHandlePrimedRequestWithSimilarXml() {

        // arrange
        when(primer.post("/post", "<blah><one/><two/></blah>", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/xml", "<success/>"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/post?key=value", HttpMethod.POST, new TestRequestCallback(MediaType.APPLICATION_XML, "<blah> <two/> <one/> </blah>"), new TestResponseExtractor());

        // assert
        assertEquals("application/xml", "<success/>", result);
        verify(primer);
    }

    @Test
    public void shouldNotHandlePrimedRequestWithDissimilarXml() {

        // arrange
        when(primer.post("/post", "<blah><one/><two/></blah>", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/xml", "<success/>"));

        try {

            // act
            restTemplate.execute("http://localhost:8500/test/post?key=value", HttpMethod.POST, new TestRequestCallback(MediaType.APPLICATION_XML, "<blah> <two/> <three/> </blah>"), new TestResponseExtractor());

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
        when(primer.post("/post", "{ \"one\" : \"a\", \"two\" : \"b\" }", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "[ \"success\" ]"));

        // act
        final String result = restTemplate.execute("http://localhost:8500/test/post?key=value", HttpMethod.POST, new TestRequestCallback(MediaType.APPLICATION_JSON, "{\"two\":\"b\",\"one\":\"a\"}"), new TestResponseExtractor());

        // assert
        assertEquals("application/xml", "[ \"success\" ]", result);
        verify(primer);
    }

    @Test
    public void shouldNotHandlePrimedRequestWithDissimilarJson() {

        // arrange
        when(primer.post("/post", "{ \"one\" : \"a\", \"two\" : \"b\" }", parameters(pair("key", "value")), headers(pair("key", "value")))).thenReturn(response(200, "application/json", "[ \"success\" ]"));

        try {

            // act
            restTemplate.execute("http://localhost:8500/test/post?key=value", HttpMethod.POST, new TestRequestCallback(MediaType.APPLICATION_JSON, "{\"three\":\"c\",\"one\":\"a\"}"), new TestResponseExtractor());

            fail("Expected HttpClientErrorException was not thrown");

        } catch (final HttpClientErrorException httpClientErrorException) {

            // assert
            assertEquals("Request Not Primed", httpClientErrorException.getResponseBodyAsString());
            assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
        }

    }


    static class TestRequestCallback implements RequestCallback {

        private final String body;
        private final MediaType contentType;

        TestRequestCallback() {
            this(null, null);
        }

        TestRequestCallback(final String body) {
            this(null, body);
        }

        TestRequestCallback(final MediaType contentType, final String body) {
            this.contentType = contentType;
            this.body = body;
        }

        @Override
        public void doWithRequest(final ClientHttpRequest clientHttpRequest) throws IOException {
            if (body != null) {
                clientHttpRequest.getBody().write(body.getBytes());
            }
            if (contentType != null) {
                clientHttpRequest.getHeaders().setContentType(contentType);
            }
            clientHttpRequest.getHeaders().put("key", Arrays.asList("value"));
        }

    }

    static class TestResponseExtractor implements ResponseExtractor<String> {

        @Override
        public String extractData(final ClientHttpResponse clientHttpResponse) throws IOException {
            return IOUtils.toString(clientHttpResponse.getBody());
        }

    }

}
