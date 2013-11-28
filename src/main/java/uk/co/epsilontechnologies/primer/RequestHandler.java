package uk.co.epsilontechnologies.primer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles the given HTTP Servlet Request and issues the appropriate HTTP Servlet Response.
 *
 * @author Shane Gibson
 */
public interface RequestHandler {

    void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException;

}