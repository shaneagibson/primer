package uk.co.epsilontechnologies.primer.client.jms.matcher;

import uk.co.epsilontechnologies.primer.client.jms.error.MessageVerificationException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

public class MessageMatcher {

    public void match(
            final List<Message> expectedMessages,
            final List<Message> actualMessages) {
        final List<Message> messagesNotPrimed = findMismatchedMessages(expectedMessages, actualMessages);
        final List<Message> primedMessagesNotIssued = findMismatchedMessages(actualMessages, expectedMessages);
        if (!messagesNotPrimed.isEmpty() || !primedMessagesNotIssued.isEmpty()) {
            throw new MessageVerificationException(messagesNotPrimed, primedMessagesNotIssued);
        }
    }

    private List<Message> findMismatchedMessages(
            final List<Message> sourceMessages,
            final List<Message> candidateMessages) {
        final List<Message> mismatchedMessages = new ArrayList();
        for (final Message candidateMessage : candidateMessages) {
            boolean found = false;
            for (final Message sourceMessage : sourceMessages) {
                if (matches((MapMessage) sourceMessage, (MapMessage) candidateMessage)) {
                    found = true;
                }
            }
            if (!found) {
                mismatchedMessages.add(candidateMessage);
            }
        }
        return mismatchedMessages;
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