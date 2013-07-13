package uk.co.epsilontechnologies.sample.service;

import org.springframework.stereotype.Component;

@Component
public class CorrelationIdStore {

    private static final ThreadLocal<String> CORRELATION_ID_THREAD_LOCAL = new ThreadLocal();

    public void setCorrelationId(final String correlationId) {
        CORRELATION_ID_THREAD_LOCAL.set(correlationId);
    }

    public String getCorrelationId() {
        return CORRELATION_ID_THREAD_LOCAL.get();
    }

    public void removeCorrelationId() {
        CORRELATION_ID_THREAD_LOCAL.remove();
    }

}
