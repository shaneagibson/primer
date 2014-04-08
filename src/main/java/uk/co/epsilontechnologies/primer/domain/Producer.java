package uk.co.epsilontechnologies.primer.domain;

/**
 * Interface for runtime production of an object.
 *
 * @param <T> The object to produce
 */
public interface Producer<T> {

    /**
     * Produces the object for this implementation.
     *
     * @return the produced object
     */
    T produce();

}
