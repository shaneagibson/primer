package uk.co.epsilontechnologies.primer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract collection of java.lang.String key / value pairs.
 *
 * @author Shane Gibson
 */
abstract class AbstractPairs {

    private final Map<String,String> pairs = new HashMap();

    public AbstractPairs(final Pair... pairs) {
        for (final Pair pair : pairs) {
            this.pairs.put(pair.getName(), pair.getValue());
        }
    }

    public Map<String,String> get() {
        return this.pairs;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}