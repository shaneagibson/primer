package uk.co.epsilontechnologies.primer.matcher;

/**
 * Interface for comparison of a primed value against a request value.
 *
 * @author Shane Gibson
 */
public interface Matcher<T,K> {

    /**
     * Matches the primed value against the request value
     * @param primedValue the primed value to match against
     * @param requestValue the request value to match
     * @return true if the values match, false otherwise
     */
    boolean match(T primedValue, K requestValue);

}
