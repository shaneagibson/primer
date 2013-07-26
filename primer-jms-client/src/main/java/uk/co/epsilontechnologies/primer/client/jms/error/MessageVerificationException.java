package uk.co.epsilontechnologies.primer.client.jms.error;

import javax.jms.Message;
import java.util.List;

public class MessageVerificationException extends RuntimeException {

    private final List<Message> messagesNotPrimed;
    private final List<Message> primedMessagesNotIssued;

    private MessageVerificationException(final String message) {
        super(message);
        this.messagesNotPrimed = null;
        this.primedMessagesNotIssued = null;
    }

    public MessageVerificationException(
            final List<Message> messagesNotPrimed,
            final List<Message> primedMessagesNotIssued) {
        super();
        this.messagesNotPrimed = messagesNotPrimed;
        this.primedMessagesNotIssued = primedMessagesNotIssued;
    }

    public List<Message> getMessagesNotPrimed() {
        return messagesNotPrimed;
    }

    public List<Message> getPrimedMessagesNotIssued() {
        return primedMessagesNotIssued;
    }

    public String getMessage() {
        return String.format("Primed messages not issued: %s, Issued messages not primed: %s", primedMessagesNotIssued, messagesNotPrimed);
    }

}