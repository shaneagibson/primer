package uk.co.epsilontechnologies.primer.domain;

/**
 * Domain-specific Pair implementation for HTTP Header key / value pairs.
 *
 * @author Shane Gibson
 */
public class Headers extends AbstractPairs {

    /**
     * Constructs the headers for the given pairs
     * @param pairs the header key / value pairs
     */
    public Headers(final Pair... pairs) {
        super(pairs);
    }

}
