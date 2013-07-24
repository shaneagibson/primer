package uk.co.epsilontechnologies.primer.client;

public interface Primer<T> {

    void reset();

    void prime(T t);

    void verify();

}
