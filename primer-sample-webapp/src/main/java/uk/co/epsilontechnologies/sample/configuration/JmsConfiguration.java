package uk.co.epsilontechnologies.sample.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfiguration {

    @Value("${activemq.url}")
    private String activeMQUrl;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory(activeMQUrl);
    }

    @Bean(name = "logQueue")
    public ActiveMQQueue logQueue() {
        return new ActiveMQQueue("log");
    }

    @Bean
    public JmsTemplate jmsTemplate(
            final ActiveMQConnectionFactory activeMQConnectionFactory,
            @Qualifier("logQueue") final ActiveMQQueue logQueue) {
        final JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory);
        jmsTemplate.setDefaultDestination(logQueue);
        return jmsTemplate;
    }

}
