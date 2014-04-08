package uk.co.epsilontechnologies.primer;

import uk.co.epsilontechnologies.primer.domain.Matchable;
import uk.co.epsilontechnologies.primer.domain.Pair;
import uk.co.epsilontechnologies.primer.domain.PrimedRequest;
import uk.co.epsilontechnologies.primer.domain.Producer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

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

    public static Pair<String> pair(final String key, final String value) {
        return new Pair(key, value);
    }

    public static Pair<Producer<String>> pair(final String key, final Producer<String> value) {
        return new Pair(key, value);
    }

    public static Pair<Matchable> pair(final String key, final Matchable value) {
        return new Pair(key, value);
    }

    public static <T> List<Pair<T>> headers(final Pair<T>... headers) {
        return Arrays.asList(headers);
    }

    public static <T> List<Pair<T>> cookies(final Pair<T>... cookies) {
        return Arrays.asList(cookies);
    }

    public static <T> List<Pair<T>> parameters(final Pair<T>... parameters) {
        return Arrays.asList(parameters);
    }

}