package uk.co.epsilontechnologies.primer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Wraps of the Jetty HTTP Server.
 *
 * @author Shane Gibson
 */
class PrimerServer {

    private final Server server;

    public PrimerServer(final int port, final RequestHandler requestHandler) {
        this.server = new Server(port);
        this.server.setHandler(new ServerRequestHandler(requestHandler));
    }

    public void start() {
        try {
            this.server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            this.server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class ServerRequestHandler extends AbstractHandler {

        private final RequestHandler requestHandler;

        public ServerRequestHandler(final RequestHandler requestHandler) {
            this.requestHandler = requestHandler;
        }

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