package uk.co.epsilontechnologies.primer.domain;

public class Pair<T> {

    private final String key;
    private final T value;

    public Pair(final String key, final T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

}