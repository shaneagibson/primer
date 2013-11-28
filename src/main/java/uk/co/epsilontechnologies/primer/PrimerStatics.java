package uk.co.epsilontechnologies.primer;

import uk.co.epsilontechnologies.primer.domain.*;

import java.lang.reflect.Field;

/**
 * Useful static helpers that expose a familiar domain-specific syntax for constructing and programming a Primer instance.
 *
 * @author Shane Gibson
 */
public final class PrimerStatics {

    private PrimerStatics() {
        super();
    }

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

    public static void verify(final Primer... primers) {
        for (final Primer primer : primers) {
            primer.verify();
        }
    }

    public static void initPrimers(final Object testClass) {
        final Class<?> clazz = testClass.getClass();
        for (final Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Primable.class)) {
                final Primable primableAnnotation = field.getAnnotation(Primable.class);
                try {
                    field.setAccessible(true);
                    field.set(
                            testClass,
                            new Primer(
                                    primableAnnotation.contextPath(),
                                    primableAnnotation.port()));
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException("Unable to initialize annotated primers", e);
                }
            }
        }
    }

}