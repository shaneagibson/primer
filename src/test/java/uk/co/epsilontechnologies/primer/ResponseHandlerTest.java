package uk.co.epsilontechnologies.primer;

import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class ResponseHandlerTest {

    private ResponseHandler underTest = new ResponseHandler();

    @Test
    public void shouldHandleResponse() throws Exception {

        // arrange
        final Response response = mock(Response.class);
        final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        final Headers headers = new Headers(new Pair("key1", "value1"), new Pair("key2", "value2"));
        final PrintWriter printWriter = mock(PrintWriter.class);
        when(response.getStatus()).thenReturn(200);
        when(response.getContentType()).thenReturn("application/json");
        when(response.getBody()).thenReturn("<b>Hello, World!</b>");
        when(response.getHeaders()).thenReturn(headers);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // act
        this.underTest.respond(response, httpServletResponse);

        // assert
        verify(httpServletResponse).setStatus(200);
        verify(httpServletResponse).setContentType("application/json");
        verify(httpServletResponse).addHeader("key1", "value1");
        verify(httpServletResponse).addHeader("key2", "value2");
        verify(printWriter).write("<b>Hello, World!</b>");
        verify(httpServletResponse).flushBuffer();
    }

}
