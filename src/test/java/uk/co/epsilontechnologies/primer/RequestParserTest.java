package uk.co.epsilontechnologies.primer;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IOUtils.class)
public class RequestParserTest {

    private final RequestParser underTest = new RequestParser();

    @Test
    public void shouldParseRequestUri() {

        // arrange
        final String requestUri = "/some/request/path";
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getRequestURI()).thenReturn(requestUri);

        // act
        final String result = this.underTest.parseRequestUri(httpServletRequest);

        // assert
        assertEquals(requestUri, result);

    }

    @Test
    public void shouldParseRequestBody() throws Exception {

        // arrange
        mockStatic(IOUtils.class);
        final String requestBody = "<b>Hello, World!</b>";
        final ServletInputStream servletInputStream = mock(ServletInputStream.class);
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getInputStream()).thenReturn(servletInputStream);
        when(IOUtils.toString(servletInputStream)).thenReturn(requestBody);

        // act
        final String result = this.underTest.parseRequestBody(httpServletRequest);

        // assert
        assertEquals(requestBody, result);

    }

    @Test
    public void shouldParseRequestMethod() {

        // arrange
        final String requestMethod = "GET";
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getMethod()).thenReturn(requestMethod);

        // act
        final String result = this.underTest.parseRequestMethod(httpServletRequest);

        // assert
        assertEquals(requestMethod, result);

    }

    @Test
    public void shouldParseRequestHeaders() {

        // arrange
        final String key1 = "key1";
        final String value1 = "value1";
        final String key2 = "key2";
        final String value2 = "value2";
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        final Enumeration<String> headerNames = mock(Enumeration.class);
        when(httpServletRequest.getHeaderNames()).thenReturn(headerNames);
        when(headerNames.hasMoreElements()).thenReturn(true, true, false);
        when(headerNames.nextElement()).thenReturn(key1, key2);
        when(httpServletRequest.getHeader(key1)).thenReturn(value1);
        when(httpServletRequest.getHeader(key2)).thenReturn(value2);

        // act
        final Map<String,String> result = this.underTest.parseHeaders(httpServletRequest);

        // assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey(key1));
        assertTrue(result.containsKey(key2));
        assertEquals(value1, result.get(key1));
        assertEquals(value2, result.get(key2));

    }

    @Test
    public void shouldParseRequestParameters() {

        // arrange
        final String key1 = "key1";
        final String value1 = "value1";
        final String key2 = "key2";
        final String value2 = "value2";
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        final Enumeration<String> parameterNames = mock(Enumeration.class);
        when(httpServletRequest.getParameterNames()).thenReturn(parameterNames);
        when(parameterNames.hasMoreElements()).thenReturn(true, true, false);
        when(parameterNames.nextElement()).thenReturn(key1, key2);
        when(httpServletRequest.getParameter(key1)).thenReturn(value1);
        when(httpServletRequest.getParameter(key2)).thenReturn(value2);

        // act
        final Map<String,String> result = this.underTest.parseRequestParameters(httpServletRequest);

        // assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey(key1));
        assertTrue(result.containsKey(key2));
        assertEquals(value1, result.get(key1));
        assertEquals(value2, result.get(key2));

    }

}
