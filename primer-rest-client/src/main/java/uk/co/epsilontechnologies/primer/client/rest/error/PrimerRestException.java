package uk.co.epsilontechnologies.primer.client.rest.error;

public class PrimerRestException extends RuntimeException {

    public PrimerRestException(final String message) {
        super(message);
    }

    public PrimerRestException(final Throwable cause) {
        super(cause);
    }

}
