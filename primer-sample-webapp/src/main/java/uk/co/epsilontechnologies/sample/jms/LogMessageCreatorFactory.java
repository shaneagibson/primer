package uk.co.epsilontechnologies.sample.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class LogMessageCreatorFactory {

    private final LogMessageFactory logMessageFactory;

    @Autowired
    public LogMessageCreatorFactory(final LogMessageFactory logMessageFactory) {
        this.logMessageFactory = logMessageFactory;
    }

    public MessageCreator create(final String message) {
        return new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return logMessageFactory.create(session, message);
            }
        };
    }

}
