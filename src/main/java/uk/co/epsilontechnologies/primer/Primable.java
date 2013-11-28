package uk.co.epsilontechnologies.primer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for demarcating a Primer as part of a test case.
 *
 * @author Shane Gibson
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Primable {

    String contextPath();

    int port();

}
