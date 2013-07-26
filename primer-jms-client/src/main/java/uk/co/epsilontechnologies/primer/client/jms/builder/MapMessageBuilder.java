package uk.co.epsilontechnologies.primer.client.jms.builder;

import org.apache.activemq.command.ActiveMQMapMessage;

import javax.jms.JMSException;
import javax.jms.MapMessage;

public class MapMessageBuilder {

    private final MapMessage mapMessage = new ActiveMQMapMessage();

    public MapMessageBuilder(final String description) {
        try {
            mapMessage.setString("description", description);
        } catch (final JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public MapMessageBuilder with(final String key, final String value) {
        try {
            mapMessage.setString(key, value);
            return this;
        } catch (final JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public MapMessage build() {
        return mapMessage;
    }

    public static MapMessageBuilder mapMessage(final String description) {
        return new MapMessageBuilder(description);
    }

}
