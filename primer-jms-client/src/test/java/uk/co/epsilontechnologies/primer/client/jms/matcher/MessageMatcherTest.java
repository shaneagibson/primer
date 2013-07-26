package uk.co.epsilontechnologies.primer.client.jms.matcher;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.junit.Before;
import org.junit.Test;
import uk.co.epsilontechnologies.primer.client.jms.error.MessageVerificationException;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MessageMatcherTest {

    private MessageMatcher underTest;

    @Before
    public void setUp() {
        this.underTest = new MessageMatcher();
    }

    @Test
    public void shouldSucceedWhenMessagesAllMatch() throws JMSException {

        // given
        final Message message1 = createTestMessage("one");
        final Message message2 = createTestMessage("two");
        final List<Message> expectedMessages = Arrays.asList(message1, message2);
        final List<Message> actualMessages = Arrays.asList(message1, message2);

        // when
        this.underTest.match(expectedMessages, actualMessages);

        // then
        // no exception was thrown
    }

    @Test
    public void shouldFailWhenExpectedMessageIsNotIssued() throws JMSException {

        // given
        final Message message1 = createTestMessage("one");
        final Message message2 = createTestMessage("two");
        final List<Message> expectedMessages = Arrays.asList(message1, message2);
        final List<Message> actualMessages = Arrays.asList(message1);

        try {
            // when
            this.underTest.match(expectedMessages, actualMessages);
            fail("MessageVerificationException was not thrown");

        } catch (final MessageVerificationException e) {
            // then
            assertTrue(e.getMessagesNotPrimed().isEmpty());
            assertEquals(1, e.getPrimedMessagesNotIssued().size());
            assertEquals(message2, e.getPrimedMessagesNotIssued().get(0));
        }
    }

    @Test
    public void shouldFailWhenIssuedMessageIsNotExpected() throws JMSException {

        // given
        final Message message1 = createTestMessage("one");
        final Message message2 = createTestMessage("two");
        final List<Message> expectedMessages = Arrays.asList(message1);
        final List<Message> actualMessages = Arrays.asList(message1, message2);

        try {
            // when
            this.underTest.match(expectedMessages, actualMessages);
            fail("MessageVerificationException was not thrown");

        } catch (final MessageVerificationException e) {
            // then
            assertTrue(e.getPrimedMessagesNotIssued().isEmpty());
            assertEquals(1, e.getMessagesNotPrimed().size());
            assertEquals(message2, e.getMessagesNotPrimed().get(0));
        }
    }

    private Message createTestMessage(final String value) throws JMSException {
        final ActiveMQMapMessage message = new ActiveMQMapMessage();
        message.setString("value", value);
        return message;
    }

}

