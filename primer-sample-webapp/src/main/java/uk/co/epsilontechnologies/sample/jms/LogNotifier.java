package uk.co.epsilontechnologies.sample.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogNotifier implements ILogNotifier {

    private final JmsTemplate jmsTemplate;
    private final LogMessageCreatorFactory logMessageCreatorFactory;

    @Autowired
    public LogNotifier(
            final JmsTemplate jmsTemplate,
            final LogMessageCreatorFactory logMessageCreatorFactory) {
        this.jmsTemplate = jmsTemplate;
        this.logMessageCreatorFactory = logMessageCreatorFactory;
    }

    @Override
    public void log(final String message) {
        jmsTemplate.send(logMessageCreatorFactory.create(message));
    }

}
