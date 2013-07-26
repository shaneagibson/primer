package uk.co.epsilontechnologies.primer.client.jms.error;

import javax.jms.Message;

public class MessageNotPrimedException extends RuntimeException {

    private final Message messageNotPrimed;

    private MessageNotPrimedException(final String message) {
        super(message);
        this.messageNotPrimed = null;
    }

    private MessageNotPrimedException(final String message, final Throwable cause) {
        super(message, cause);
        this.messageNotPrimed = null;
    }

    private MessageNotPrimedException(final Throwable cause) {
        super(cause);
        this.messageNotPrimed = null;
    }

    private MessageNotPrimedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.messageNotPrimed = null;
    }

    public MessageNotPrimedException(final Message messageNotPrimed) {
        super();
        this.messageNotPrimed = messageNotPrimed;
    }

    public Message getMessageNotPrimed() {
        return messageNotPrimed;
    }

    public String getMessage() {
        return "Message not primed: " + messageNotPrimed;
    }

}
