package uk.co.epsilontechnologies.primer.domain;

/**
 * Interface for use when matching a string value.
 *
 * @author Shane Gibson
 */
public interface Matchable {

    /**
     * Determines if this instance matches the given request string
     * @param requestString the request string to check
     * @return true if the request string matches, false otherwise
     */
    boolean match(String requestString);

}
