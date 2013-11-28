package uk.co.epsilontechnologies.primer.domain;

/**
 * Domain-specific Pair implementation for HTTP Header key / value pairs.
 *
 * @author Shane Gibson
 */
public class Headers extends AbstractPairs {

    public Headers(final Pair... pairs) {
        super(pairs);
    }

}
