package uk.co.epsilontechnologies.primer;

import java.lang.reflect.Field;

public final class PrimerAnnotations {

    private PrimerAnnotations() {
        super();
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
