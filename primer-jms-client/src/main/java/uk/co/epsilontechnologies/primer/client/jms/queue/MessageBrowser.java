package uk.co.epsilontechnologies.primer.client.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MessageBrowser {

    private final JmsTemplate jmsTemplate;

    public MessageBrowser(final String host, final int port, final String queueName) {
        final ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);
        final ActiveMQQueue activeMQQueue = new ActiveMQQueue(queueName);
        this.jmsTemplate = new JmsTemplate(activeMQConnectionFactory);
        this.jmsTemplate.setDefaultDestination(activeMQQueue);
    }

    public List<Message> getMessages() {
        return jmsTemplate.browse(new BrowserCallback<List<Message>>() {
            @Override
            public List<Message> doInJms(final Session session, final QueueBrowser queueBrowser) throws JMSException {
                final Enumeration enumeration = queueBrowser.getEnumeration();
                final List<Message> messages = new ArrayList();
                while (enumeration.hasMoreElements()) {
                    messages.add((MapMessage) enumeration.nextElement());
                }
                return messages;
            }
        });

    }
    
}