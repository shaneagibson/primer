package uk.co.epsilontechnologies.primer;

public interface Matcher<T,K> {

    boolean match(T stringToMatch, K k);

}
