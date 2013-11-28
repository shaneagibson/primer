package uk.co.epsilontechnologies.primer.matcher;

/**
 * Interface for comparison of a primed value against a request value.
 *
 * @author Shane Gibson
 */
public interface Matcher<T,K> {

    boolean match(T primedValue, K requestValue);

}
