package uk.co.epsilontechnologies.primer.domain;

/**
 * Entity for representing a key / value pair of attributes.
 *
 * @author Shane Gibson
 */
public class Pair {

    private final String name;
    private final String value;

    public Pair(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
