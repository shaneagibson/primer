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

    /**
     * The Context Path for the primable instance
     * @return the context path
     */
    String contextPath();

    /**
     * The Port for the primable instance
     * @return the port
     */
    int port();

}
