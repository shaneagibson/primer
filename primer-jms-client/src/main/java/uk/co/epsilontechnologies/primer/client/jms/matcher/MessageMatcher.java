package uk.co.epsilontechnologies.primer.client.jms.matcher;

import uk.co.epsilontechnologies.primer.client.jms.error.MessageNotPrimedException;
import uk.co.epsilontechnologies.primer.client.jms.error.PrimedMessageNotIssuedException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

public class MessageMatcher {

    public void match(
            final List<Message> expectedMessages,
            final List<Message> actualMessages) {
        for (final Message actualMessage : actualMessages) {
            boolean found = false;
            for (final Message expectedMessage : expectedMessages) {
                if (matches((MapMessage) expectedMessage, (MapMessage) actualMessage)) {
                    found = true;
                }
            }
            if (!found) {
                throw new MessageNotPrimedException(actualMessage);
            }
        }
        for (final Message expectedMessage : expectedMessages) {
            boolean found = false;
            for (final Message actualMessage : actualMessages) {
                if (matches((MapMessage) expectedMessage, (MapMessage) actualMessage)) {
                    found = true;
                }
            }
            if (!found) {
                throw new PrimedMessageNotIssuedException(expectedMessage);
            }
        }
    }

    private boolean matches(
            final MapMessage expectedMessage,
            final MapMessage actualMessage) {
        try {
            final Enumeration<String> expectedMessageKeys = expectedMessage.getMapNames();
            while (expectedMessageKeys.hasMoreElements()) {
                final String key = expectedMessageKeys.nextElement();
                if (!key.equals("description")) {
                    final String expectedRegEx = expectedMessage.getString(key);
                    final String actualValue = actualMessage.getString(key);
                    if (!Pattern.matches(expectedRegEx, actualValue)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (final JMSException e) {
            throw new RuntimeException(e);
        }
    }

}
