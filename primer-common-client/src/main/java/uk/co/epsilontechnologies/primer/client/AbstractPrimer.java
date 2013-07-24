package uk.co.epsilontechnologies.primer.client;

public interface AbstractPrimer<T> {

    void reset();

    void prime(T t);

    void verify();

}
