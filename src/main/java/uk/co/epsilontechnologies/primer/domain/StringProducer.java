package uk.co.epsilontechnologies.primer.domain;

class StringProducer implements Producer<String> {

    private final String s;

    public StringProducer(final String s) {
        this.s = s;
    }

    @Override
    public String produce() {
        return s;
    }

}