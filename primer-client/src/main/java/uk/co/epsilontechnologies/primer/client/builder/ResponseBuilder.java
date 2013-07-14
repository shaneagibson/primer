package uk.co.epsilontechnologies.primer.client.builder;

import org.springframework.http.HttpStatus;
import uk.co.epsilontechnologies.primer.client.model.Response;

public class ResponseBuilder {

    private HttpStatus status = HttpStatus.OK;

    private String body = null;

    public ResponseBuilder() {
        super();
    }

    public ResponseBuilder withStatus(final HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseBuilder withBody(final String body) {
        this.body = body;
        return this;
    }

    public static ResponseBuilder response() {
        return new ResponseBuilder();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
    
    public Response build() {
        return new Response(status, body);
    }

}
