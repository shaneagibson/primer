package uk.co.epsilontechnologies.primer.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles the given HTTP Servlet Request and issues the appropriate HTTP Servlet Response.
 *
 * @author Shane Gibson
 */
public interface RequestHandler {

    /**
     * Handles the HTTP Servlet Request and Response
     * @param httpServletRequest the HTTP servlet request to handle
     * @param httpServletResponse the HTTP servlet response to issue
     */
    void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}