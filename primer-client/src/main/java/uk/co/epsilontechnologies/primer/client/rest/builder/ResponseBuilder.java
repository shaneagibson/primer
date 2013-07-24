package uk.co.epsilontechnologies.primer.client.rest.builder;

import org.springframework.http.HttpStatus;
import uk.co.epsilontechnologies.primer.client.rest.model.Response;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    private HttpStatus status = HttpStatus.OK;

    private String body = null;

    private Map<String,String> headers = new HashMap();

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

    public ResponseBuilder withHeader(final String name, final String value) {
        this.headers.put(name, value);
        return this;
    }

    public static ResponseBuilder response() {
        return new ResponseBuilder();
    }

    public Response build() {
        return new Response(status, body, headers);
    }

}
