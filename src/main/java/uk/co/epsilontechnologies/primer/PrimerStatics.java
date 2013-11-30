package uk.co.epsilontechnologies.primer;

import uk.co.epsilontechnologies.primer.domain.*;

import java.lang.reflect.Field;

/**
 * Useful static helpers that expose a familiar domain-specific syntax for constructing and programming a Primer instance.
 *
 * @author Shane Gibson
 */
public final class PrimerStatics {

    /**
     * Hidden constructor
     */
    private PrimerStatics() {
        super();
    }

    /**
     * Programmes the HTTP request against the given primer
     * @param primedRequest the primed request being programmed
     * @return the HTTP invocation being programmed
     */
    public static When when(final PrimedRequest primedRequest) {
        return new When(primedRequest.getPrimer(), primedRequest.getRequest());
    }

    /**
     * Constructs the headers for the given pairs
     * @param pairs the header key / values
     * @return the headers instance
     */
    public static Headers headers(final Pair... pairs) {
        return new Headers(pairs);
    }

    /**
     * Constructs a key / value pair
     * @param name the name of the pair
     * @param value the value of the pair
     * @return the pair instance
     */
    public static Pair pair(final String name, final String value) {
        return new Pair(name, value);
    }

    /**
     * Constructs the parameters for the given pairs
     * @param pairs the parameter key / values
     * @return the parameters instance
     */
    public static Parameters parameters(final Pair... pairs) {
        return new Parameters(pairs);
    }

    /**
     * Builds a request for the given method, path, body, parameters and headers
     * @param method the HTTP method for the request
     * @param path the path for the request
     * @param body the body for the request
     * @param parameters the parameters for the request
     * @param headers the headers for the request
     * @return the request instance
     */
    public static Request request(
            final String method,
            final String path,
            final String body,
            final Parameters parameters,
            final Headers headers) {
        return new Request(method, path, body, parameters, headers);
    }

    /**
     * Builds a response for the given status, content type, body and headers
     * @param status the HTTP status for the response
     * @param contentType the Content-Type for the response
     * @param body the body for the response
     * @param headers the headers for the response
     * @return the request instance
     */
    public static Response response(
            final int status,
            final String contentType,
            final String body,
            final Headers headers) {
        return new Response(status, contentType, body, headers);
    }

    /**
     * Builds a response for the given status, content type and body
     * @param status the HTTP status for the response
     * @param contentType the Content-Type for the response
     * @param body the body for the response
     * @return the request instance
     */
    public static Response response(
            final int status,
            final String contentType,
            final String body) {
        return new Response(status, contentType, body);
    }

    /**
     * Builds a response for the given status
     * @param status the HTTP status for the response
     * @return the request instance
     */
    public static Response response(final int status) {
        return new Response(status);
    }

    /**
     * Verifies that all primed invocations were invoked for each of the primer instances
     * @param primers the primers to verify
     */
    public static void verify(final Primer... primers) {
        for (final Primer primer : primers) {
            primer.verify();
        }
    }

    /**
     * Initialized all primer instances that are annotated as @Primable
     * @param testClass the test class instance being initialized
     */
    public static void initPrimers(final Object testClass) {
        final Class<?> clazz = testClass.getClass();
        for (final Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Primable.class)) {
                final Primable primableAnnotation = field.getAnnotation(Primable.class);
                try {
                    field.setAccessible(true);
                    field.set(testClass, new Primer(primableAnnotation.contextPath(), primableAnnotation.port()));
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException("Unable to initialize annotated primers", e);
                }
            }
        }
    }

}