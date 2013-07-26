package uk.co.epsilontechnologies.primer.client.jms.error;

public class PrimerJmsException extends RuntimeException {

    public PrimerJmsException(final String message) {
        super(message);
    }

    public PrimerJmsException(final Throwable cause) {
        super(cause);
    }

}
