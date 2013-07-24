package uk.co.epsilontechnologies.primer.client.rest.model;

public class HttpCycle {

    private final Request request;
    private final Response response;

    public HttpCycle(final Request request, final Response response) {
        this.request = request;
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }

}
