package uk.co.epsilontechnologies.primer.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Wrapper of the Jetty HTTP Server.
 *
 * @author Shane Gibson
 */
public class PrimerServer {

    /**
     * The Jetty HTTP Server
     */
    private final Server server;

    /**
     * Constructs a server for the given port and request handler
     * @param port the port on which the server should operate
     * @param requestHandler the request handler to use
     */
    public PrimerServer(final int port, final RequestHandler requestHandler) {
        this.server = new Server(port);
        this.server.setHandler(new ServerRequestHandler(requestHandler));
    }

    /**
     * Start the server
     */
    public void start() {
        try {
            this.server.start();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stop the server
     */
    public void stop() {
        try {
            this.server.stop();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The Jetty request handler that will be invoked on any request
     */
    class ServerRequestHandler extends AbstractHandler {

        /**
         * The server-agnostic request handler
         */
        private final RequestHandler requestHandler;

        /**
         * Constructs a server request handler for the given server-agnostic request handler
         * @param requestHandler the request handler to wrap
         */
        public ServerRequestHandler(final RequestHandler requestHandler) {
            this.requestHandler = requestHandler;
        }

        /**
         * Handler implementation for any HTTP request
         *
         * @see AbstractHandler#handle(String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
         *
         * @param target the target
         * @param baseRequest the base request
         * @param httpServletRequest the HTTP servlet request
         * @param httpServletResponse the HTTP servlet response
         * @throws IOException an IO exception occurred
         * @throws ServletException a Servlet exception occurred
         */
        @Override
        public void handle(
                final String target,
                final org.eclipse.jetty.server.Request baseRequest,
                final HttpServletRequest httpServletRequest,
                final HttpServletResponse httpServletResponse) throws IOException, ServletException {
            this.requestHandler.handle(httpServletRequest, httpServletResponse);
        }

    }

}