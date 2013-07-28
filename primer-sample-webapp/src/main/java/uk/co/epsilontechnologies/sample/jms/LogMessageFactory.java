package uk.co.epsilontechnologies.sample.jms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.epsilontechnologies.sample.threadlocal.CorrelationIdStore;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class LogMessageFactory {

    private final CorrelationIdStore correlationIdStore;

    @Autowired
    public LogMessageFactory(final CorrelationIdStore correlationIdStore) {
        this.correlationIdStore = correlationIdStore;
    }

    public Message create(final Session session, final String logMessage) throws JMSException {
        final MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString("message", logMessage);
        mapMessage.setLong("timestamp", System.currentTimeMillis());
        mapMessage.setString("correlationId", correlationIdStore.getCorrelationId());
        return mapMessage;
    }

}
