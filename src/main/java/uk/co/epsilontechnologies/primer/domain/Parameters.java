package uk.co.epsilontechnologies.primer.domain;

/**
 * Domain-specific Pair implementation for HTTP Request / Response Parameter key / value pairs.
 *
 * @author Shane Gibson
 */
public class Parameters extends AbstractPairs {

    /**
     * Constructs the parameters for the given pairs
     * @param pairs the parameter key / value pairs
     */
    public Parameters(final Pair... pairs) {
        super(pairs);
    }

}
