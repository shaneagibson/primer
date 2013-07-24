package uk.co.epsilontechnologies.primer.client.jms;

import uk.co.epsilontechnologies.primer.client.jms.error.MessageNotPrimedException;
import uk.co.epsilontechnologies.primer.client.jms.error.PrimedMessageNotIssuedException;
import uk.co.epsilontechnologies.primer.client.jms.matcher.MessageMatcher;
import uk.co.epsilontechnologies.primer.client.jms.queue.MessageBrowser;
import uk.co.epsilontechnologies.primer.client.jms.queue.QueuePurger;

import javax.jms.Message;
import java.util.Arrays;
import java.util.List;

public class JmsMessageVerifier {

    private final MessageMatcher messageMatcher;
    private final MessageBrowser messageBrowser;
    private final QueuePurger queuePurger;

    /**
     * Constructs the jms message verifier for the JMS queue.
     *
     * @param host
     * @param port
     * @param queueName
     */
    public JmsMessageVerifier(final String host, final int port, final String queueName) {
        this.messageMatcher = new MessageMatcher();
        this.queuePurger = new QueuePurger(queueName);
        this.messageBrowser = new MessageBrowser(host, port, queueName);
    }

    /**
     * Verifies that the expected messages were all issued.
     */
    public void verify(final Message... messages) {
        try {
            final List<Message> actualMessages = this.messageBrowser.getMessages();
            this.messageMatcher.match(Arrays.asList(messages), actualMessages);
        } catch (final PrimedMessageNotIssuedException | MessageNotPrimedException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Resets the JMS Primer instance, purging any messages.
     */
    public void reset() {
        this.queuePurger.purge();
    }

}