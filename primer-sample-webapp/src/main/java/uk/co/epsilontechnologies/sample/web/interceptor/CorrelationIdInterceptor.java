package uk.co.epsilontechnologies.sample.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import uk.co.epsilontechnologies.sample.service.CorrelationIdStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class CorrelationIdInterceptor extends HandlerInterceptorAdapter {

    private static final String CORRELATION_ID_HEADER = "correlation-id";

    private final CorrelationIdStore correlationIdStore;

    @Autowired
    public CorrelationIdInterceptor(final CorrelationIdStore correlationIdStore) {
        this.correlationIdStore = correlationIdStore;
    }

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler) throws Exception {

        final String correlationId = request.getHeader(CORRELATION_ID_HEADER);

        correlationIdStore.setCorrelationId(correlationId == null ? UUID.randomUUID().toString() : correlationId);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler,
            final ModelAndView modelAndView) throws Exception {

        correlationIdStore.removeCorrelationId();

        super.postHandle(request, response, handler, modelAndView);
    }

}
