package uk.co.epsilontechnologies.primer.domain;

/**
 * Entity for representing a key / value pair of attributes.
 *
 * @author Shane Gibson
 */
public class Pair {

    /**
     * The name of the pair
     */
    private final String name;

    /**
     * The value of the pair
     */
    private final String value;

    /**
     * Constructs the pair for the given name and value
     * @param name the name of the pair
     * @param value the value of the pair
     */
    public Pair(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Getter for the name of the pair
     * @return the name of the pair
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the value of the pair
     * @return the value of the pair
     */
    public String getValue() {
        return value;
    }

}