package uk.co.epsilontechnologies.primer.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract collection of java.lang.String key / value pairs.
 *
 * @author Shane Gibson
 */
abstract class AbstractPairs {

    /**
     * Collection of Pairs
     */
    private final Map<String,String> pairs = new HashMap();

    /**
     * Constructs the pairs
     * @param pairs the pairs to add
     */
    public AbstractPairs(final Pair... pairs) {
        for (final Pair pair : pairs) {
            this.pairs.put(pair.getName(), pair.getValue());
        }
    }

    /**
     * Returns the pairs as a map
     * @return the pairs
     */
    public Map<String,String> get() {
        return this.pairs;
    }

    /**
     * @see Object#toString()
     * @return the string representation of the pairs
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @see Object#equals(Object)
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * @see Object#hashCode()
     * @return the hash code for this instance
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}