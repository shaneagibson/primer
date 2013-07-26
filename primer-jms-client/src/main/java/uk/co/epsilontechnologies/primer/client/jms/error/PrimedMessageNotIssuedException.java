package uk.co.epsilontechnologies.primer.client.jms.error;

import javax.jms.Message;

public class PrimedMessageNotIssuedException extends RuntimeException {

    private final Message primedMessage;

    private PrimedMessageNotIssuedException(final String message) {
        super(message);
        this.primedMessage = null;
    }

    private PrimedMessageNotIssuedException(final String message, final Throwable cause) {
        super(message, cause);
        this.primedMessage = null;
    }

    private PrimedMessageNotIssuedException(final Throwable cause) {
        super(cause);
        this.primedMessage = null;
    }

    private PrimedMessageNotIssuedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.primedMessage = null;
    }

    public PrimedMessageNotIssuedException(final Message primedMessage) {
        super();
        this.primedMessage = primedMessage;
    }

    public Message getPrimedMessageNotIssued() {
        return primedMessage;
    }

    public String getMessage() {
        return "Primed message not issued: " + primedMessage;
    }

}
