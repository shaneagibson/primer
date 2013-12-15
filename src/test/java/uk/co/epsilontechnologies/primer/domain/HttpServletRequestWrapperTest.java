package uk.co.epsilontechnologies.primer.domain;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IOUtils.class)
public class HttpServletRequestWrapperTest {

    private HttpServletRequestWrapper underTest;

    @Test
    public void shouldGetBodyForRequest() throws IOException {

        // arrange
        final String body = "<xml>body</xml>";
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        final ServletInputStream servletInputStream = mock(ServletInputStream.class);
        mockStatic(IOUtils.class);
        when(IOUtils.toString(servletInputStream)).thenReturn(body);
        when(httpServletRequest.getInputStream()).thenReturn(servletInputStream);
        this.underTest = new HttpServletRequestWrapper(httpServletRequest);

        // act
        final String result = underTest.getBody();

        // assert
        assertEquals(body, result);
    }

    @Test
    public void shouldGetHeadersAsMap() throws IOException {

        // arrange
        final Enumeration<String> headerNames = mock(Enumeration.class);
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(headerNames.hasMoreElements()).thenReturn(true, true, true, false);
        when(headerNames.nextElement()).thenReturn("key1", "key2", "key3");
        mockStatic(IOUtils.class);
        when(IOUtils.toString(any(ServletInputStream.class))).thenReturn("");
        when(httpServletRequest.getHeaderNames()).thenReturn(headerNames);
        when(httpServletRequest.getHeader("key1")).thenReturn("value1");
        when(httpServletRequest.getHeader("key2")).thenReturn("value2");
        when(httpServletRequest.getHeader("key3")).thenReturn("value3");
        this.underTest = new HttpServletRequestWrapper(httpServletRequest);

        // act
        final Map<String,String> result = underTest.getHeadersAsMap();

        // assert
        assertEquals(3, result.size());
        assertTrue(result.containsKey("key1"));
        assertTrue(result.containsKey("key2"));
        assertTrue(result.containsKey("key3"));
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
        assertEquals("value3", result.get("key3"));
    }

    @Test
    public void shouldGetParametersAsMap() throws IOException {

        // arrange
        final Enumeration<String> parameterNames = mock(Enumeration.class);
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(parameterNames.hasMoreElements()).thenReturn(true, true, true, false);
        when(parameterNames.nextElement()).thenReturn("key1", "key2", "key3");
        mockStatic(IOUtils.class);
        when(IOUtils.toString(any(ServletInputStream.class))).thenReturn("");
        when(httpServletRequest.getParameterNames()).thenReturn(parameterNames);
        when(httpServletRequest.getParameter("key1")).thenReturn("value1");
        when(httpServletRequest.getParameter("key2")).thenReturn("value2");
        when(httpServletRequest.getParameter("key3")).thenReturn("value3");
        this.underTest = new HttpServletRequestWrapper(httpServletRequest);

        // act
        final Map<String,String> result = underTest.getParametersAsMap();

        // assert
        assertEquals(3, result.size());
        assertTrue(result.containsKey("key1"));
        assertTrue(result.containsKey("key2"));
        assertTrue(result.containsKey("key3"));
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
        assertEquals("value3", result.get("key3"));
    }

    @Test
    public void shouldGetCookiesAsMap() throws IOException {

        // arrange
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        mockStatic(IOUtils.class);
        when(IOUtils.toString(any(ServletInputStream.class))).thenReturn("");
        when(httpServletRequest.getCookies()).thenReturn(new Cookie[] { new Cookie("key1", "value1"), new Cookie("key2", "value2"), new Cookie("key3", "value3") });
        this.underTest = new HttpServletRequestWrapper(httpServletRequest);

        // act
        final Map<String,String> result = underTest.getCookiesAsMap();

        // assert
        assertEquals(3, result.size());
        assertTrue(result.containsKey("key1"));
        assertTrue(result.containsKey("key2"));
        assertTrue(result.containsKey("key3"));
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
        assertEquals("value3", result.get("key3"));
    }

}
