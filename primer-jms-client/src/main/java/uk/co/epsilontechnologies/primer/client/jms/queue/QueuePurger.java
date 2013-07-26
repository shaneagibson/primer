package uk.co.epsilontechnologies.primer.client.jms.queue;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import uk.co.epsilontechnologies.primer.client.jms.error.PrimerJmsException;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.net.MalformedURLException;

public class QueuePurger {

    private static final String JMX_HOST_PROPERTY = "primer.jmx.host";
    private static final String JMX_PORT_PROPERTY = "primer.jmx.port";

    private static final String DEFAULT_JMX_HOST = "localhost";
    private static final int DEFAULT_JMX_PORT = 1099;

    static {
        System.setProperty("java.rmi.server.hostname", resolveJmxHost());
        System.setProperty("com.sun.management.jmxremote.port", String.valueOf(resolveJmxPort()));
        System.setProperty("com.sun.management.jmxremote.ssl.need.client.auth", "false");
        System.setProperty("com.sun.management.jmxremote.ssl", "false");
        System.setProperty("com.sun.management.jmxremote.authenticate", "false");
    }

    private final String queueName;

    public static int resolveJmxPort() {
        return System.getProperty(JMX_PORT_PROPERTY) != null ? Integer.parseInt(System.getProperty(JMX_PORT_PROPERTY)) : DEFAULT_JMX_PORT;
    }

    public static String resolveJmxHost() {
        return System.getProperty(JMX_HOST_PROPERTY) != null ? System.getProperty(JMX_HOST_PROPERTY) : DEFAULT_JMX_HOST;
    }

    public QueuePurger(final String queueName) {
        this.queueName = queueName;
    }

    public void purge() {
        try {
            final JMXServiceURL jmxServiceUrl = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
            try (final JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl)) {
                final MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
                final ObjectName activeMQ = new ObjectName("org.apache.activemq:BrokerName=localhost,Type=Broker");
                final BrokerViewMBean brokerViewMBean = MBeanServerInvocationHandler.newProxyInstance(mBeanServerConnection, activeMQ, BrokerViewMBean.class, true);
                for (final ObjectName queueObjectName : brokerViewMBean.getQueues()) {
                    final QueueViewMBean candidateQueueViewMBean = MBeanServerInvocationHandler.newProxyInstance(mBeanServerConnection, queueObjectName, QueueViewMBean.class, true);
                    if (candidateQueueViewMBean.getName().equals(queueName)) {
                        candidateQueueViewMBean.purge();
                        return;
                    }
                }
            } catch (final Exception e) {
                throw new PrimerJmsException(e);
            }
        } catch (final MalformedURLException e) {
            throw new PrimerJmsException(e);
        }
        throw new PrimerJmsException("No QueueViewMBean found for queue: " + queueName);
    }

}