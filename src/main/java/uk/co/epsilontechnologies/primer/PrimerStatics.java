package uk.co.epsilontechnologies.primer;

public class PrimerStatics {

    public static When when(final Action action) {
        return new When(action.getPrimer(), action.getRequest());
    }

    public static Headers headers(final Pair... pairs) {
        return new Headers(pairs);
    }

    public static Pair pair(final String name, final String value) {
        return new Pair(name, value);
    }

    public static Parameters parameters(final Pair... pairs) {
        return new Parameters(pairs);
    }

    public static Request request(
            final String method,
            final String path,
            final String body,
            final Parameters parameters,
            final Headers headers) {
        return new Request(method, path, body, parameters, headers);
    }

    public static Response response(
            final int status,
            final String contentType,
            final String body,
            final Headers headers) {
        return new Response(status, contentType, body, headers);
    }

    public static Response response(
            final int status,
            final String contentType,
            final String body) {
        return new Response(status, contentType, body);
    }

    public static Response response(final int status) {
        return new Response(status);
    }

    public static void verify(final Primable primer) {
        primer.verify();
    }

}
