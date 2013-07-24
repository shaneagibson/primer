package uk.co.epsilontechnologies.primer.client.jms;

import uk.co.epsilontechnologies.primer.client.jms.error.MessageNotPrimedException;
import uk.co.epsilontechnologies.primer.client.jms.error.PrimedMessageNotIssuedException;
import uk.co.epsilontechnologies.primer.client.jms.matcher.MessageMatcher;
import uk.co.epsilontechnologies.primer.client.jms.queue.MessageBrowser;
import uk.co.epsilontechnologies.primer.client.jms.queue.QueuePurger;

import javax.jms.Message;
import java.util.ArrayList;
import java.util.List;

public class JmsPrimer {

    private final MessageMatcher messageMatcher;
    private final MessageBrowser messageBrowser;
    private final QueuePurger queuePurger;
    private final List<Message> primedMessages;

    /**
     * Constructs the primer client for the jms queue.
     *
     * @param host
     * @param port
     * @param queueName
     */
    public JmsPrimer(final String host, final int port, final String queueName) {
        this.messageMatcher = new MessageMatcher();
        this.queuePurger = new QueuePurger(queueName);
        this.messageBrowser = new MessageBrowser(host, port, queueName);
        this.primedMessages = new ArrayList();
    }

    public void prime(final Message message) {
        this.primedMessages.add(message);
    }

    public void verify() {
        try {
            final List<Message> actualMessages = this.messageBrowser.getMessages();
            this.messageMatcher.match(primedMessages, actualMessages);
        } catch (final PrimedMessageNotIssuedException | MessageNotPrimedException e) {
            throw new RuntimeException(e);
        }
    }

    public void reset() {
        this.queuePurger.purge();
        this.primedMessages.clear();
    }

}
