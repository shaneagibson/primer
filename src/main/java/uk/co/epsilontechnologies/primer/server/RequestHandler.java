package uk.co.epsilontechnologies.primer.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles the given HTTP Servlet Request and issues the appropriate HTTP Servlet Response.
 *
 * @author Shane Gibson
 */
public interface RequestHandler {

    void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}