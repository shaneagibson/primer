package uk.co.epsilontechnologies.primer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.co.epsilontechnologies.primer.RequestMatcher.MapMatcher;
import static uk.co.epsilontechnologies.primer.RequestMatcher.StringMatcher;

public class RequestMatcherTest {

    private RequestMatcher underTest;

    private static final String CONTEXT_PATH = "/blah";

    @Mock
    private StringMatcher mockStringMatcher;

    @Mock
    private MapMatcher mockMapMatcher;

    @Before
    public void setUp() {
        initMocks(this);
        this.underTest = new RequestMatcher(CONTEXT_PATH, mockStringMatcher, mockMapMatcher);
    }

    @Test
    public void shouldMatchRequestMethod() {

        // arrange
        final String primedRequestMethod = "POST";
        final String actualRequestMethod = "POST";
        final PrimedInvocation primedInvocation = mock(PrimedInvocation.class);
        final Request primedRequest = mock(Request.class);
        when(primedInvocation.getRequest()).thenReturn(primedRequest);
        when(primedRequest.getMethod()).thenReturn(primedRequestMethod);
        when(mockStringMatcher.match(actualRequestMethod, primedRequestMethod)).thenReturn(true);

        // act
        final boolean result = this.underTest.matchRequestMethod(actualRequestMethod, primedInvocation);

        // assert
        assertTrue(result);
        verify(mockStringMatcher).match(actualRequestMethod, primedRequestMethod);
    }

    @Test
    public void shouldMatchRequestUri() {

        // arrange
        final String primedRequestUri = "/some/uri";
        final String actualRequestUri = "/some/uri";
        final PrimedInvocation primedInvocation = mock(PrimedInvocation.class);
        final Request primedRequest = mock(Request.class);
        when(primedInvocation.getRequest()).thenReturn(primedRequest);
        when(primedRequest.getURI()).thenReturn(primedRequestUri);
        when(mockStringMatcher.match(actualRequestUri, primedRequestUri)).thenReturn(true);

        // act
        final boolean result = this.underTest.matchRequestUri(actualRequestUri, primedInvocation);

        // assert
        assertTrue(result);
        verify(mockStringMatcher).match(actualRequestUri, primedRequestUri);
    }

    @Test
    public void shouldMatchRequestBody() {

        // arrange
        final String primedRequestBody = "<blah>123</blah>";
        final String actualRequestBody = "<blah>123</blah>";
        final PrimedInvocation primedInvocation = mock(PrimedInvocation.class);
        final Request primedRequest = mock(Request.class);
        when(primedInvocation.getRequest()).thenReturn(primedRequest);
        when(primedRequest.getBody()).thenReturn(primedRequestBody);
        when(mockStringMatcher.match(actualRequestBody, primedRequestBody)).thenReturn(true);

        // act
        final boolean result = this.underTest.matchRequestBody(actualRequestBody, primedInvocation);

        // assert
        assertTrue(result);
        verify(mockStringMatcher).match(actualRequestBody, primedRequestBody);
    }
    
    @Test
    public void shouldMatchRequestHeaders() {
        
        // arrange
        final Map<String,String> primedRequestHeaders = mock(Map.class);
        final Map<String,String> actualRequestHeaders = mock(Map.class);
        final PrimedInvocation primedInvocation = mock(PrimedInvocation.class);
        final Request primedRequest = mock(Request.class);
        final Headers primedHeaders = mock(Headers.class);
        when(primedInvocation.getRequest()).thenReturn(primedRequest);
        when(primedRequest.getHeaders()).thenReturn(primedHeaders);
        when(primedHeaders.get()).thenReturn(primedRequestHeaders);
        when(mockMapMatcher.match(actualRequestHeaders, primedRequestHeaders)).thenReturn(true);

        // act
        final boolean result = this.underTest.matchHeaders(actualRequestHeaders, primedInvocation);
        
        // assert
        assertTrue(result);
        verify(mockMapMatcher).match(actualRequestHeaders, primedRequestHeaders);
    }

    @Test
    public void shouldMatchRequestParameters() {

        // arrange
        final Map<String,String> primedRequestParameters = mock(Map.class);
        final Map<String,String> actualRequestParameters = mock(Map.class);
        final PrimedInvocation primedInvocation = mock(PrimedInvocation.class);
        final Request primedRequest = mock(Request.class);
        final Parameters primedParameters = mock(Parameters.class);
        when(primedInvocation.getRequest()).thenReturn(primedRequest);
        when(primedRequest.getParameters()).thenReturn(primedParameters);
        when(primedParameters.get()).thenReturn(primedRequestParameters);
        when(mockMapMatcher.match(actualRequestParameters, primedRequestParameters)).thenReturn(true);

        // act
        final boolean result = this.underTest.matchRequestParameters(actualRequestParameters, primedInvocation);

        // assert
        assertTrue(result);
        verify(mockMapMatcher).match(actualRequestParameters, primedRequestParameters);
    }

    public static class MapMatcherTest {

        private MapMatcher underTest;

        @Mock
        private StringMatcher mockStringMatcher;

        @Before
        public void setUp() {
            initMocks(this);
            this.underTest = new MapMatcher(mockStringMatcher);
        }

        @Test
        public void shouldMatchMapContainingAllKeys() {

            // arrange
            final Map<String,String> requestMap = new HashMap<>();
            requestMap.put("key1", "value1");
            requestMap.put("key2", "value2");
            final Map<String,String> primedMap = new HashMap<>();
            primedMap.put("key1", "value1");
            primedMap.put("key2", "value2");
            when(mockStringMatcher.match(anyString(), anyString())).thenReturn(true);

            // act
            final boolean result = this.underTest.match(requestMap, primedMap);

            //assert
            assertTrue(result);
        }

        @Test
        public void shouldMatchMapContainingSubsetOfKeys() {

            // arrange
            final Map<String,String> requestMap = new HashMap<>();
            requestMap.put("key1", "value1");
            requestMap.put("key2", "value2");
            final Map<String,String> primedMap = new HashMap<>();
            primedMap.put("key1", "value1");
            when(mockStringMatcher.match(anyString(), anyString())).thenReturn(true);

            // act
            final boolean result = this.underTest.match(requestMap, primedMap);

            //assert
            assertTrue(result);
        }

        @Test
        public void shouldNotMatchMapContainingAdditionalKeys() {

            // arrange
            final Map<String,String> requestMap = new HashMap<>();
            requestMap.put("key1", "value1");
            final Map<String,String> primedMap = new HashMap<>();
            primedMap.put("key1", "value1");
            primedMap.put("key2", "value2");
            when(mockStringMatcher.match(anyString(), anyString())).thenReturn(true);

            // act
            final boolean result = this.underTest.match(requestMap, primedMap);

            //assert
            assertFalse(result);
        }

    }

    public static class StringMatcherTest {

        private StringMatcher underTest = new StringMatcher();

        @Test
        public void shouldMatchSameValue() {

            // arrange
            final String requestValue = "abc123";
            final String primedValue = "abc123";

            // act
            final boolean result = this.underTest.match(requestValue, primedValue);

            //assert
            assertTrue(result);
        }

        @Test
        public void shouldNotMatchDifferentValue() {

            // arrange
            final String requestValue = "abc123";
            final String primedValue = "def456";

            // act
            final boolean result = this.underTest.match(requestValue, primedValue);

            //assert
            assertFalse(result);
        }

        @Test
        public void shouldMatchRegularExpression() {

            // arrange
            final String requestValue = "abc123";
            final String primedValue = "ab[a-c]12[0-9]";

            // act
            final boolean result = this.underTest.match(requestValue, primedValue);

            //assert
            assertTrue(result);
        }

    }

}
