package tech.chillo.files;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

@Component
public class RabbitErrorHandler implements RabbitListenerErrorHandler {
    @Override
    public Object handleError(final Message message, final org.springframework.messaging.Message<?> message1, final ListenerExecutionFailedException e) throws Exception {
        e.printStackTrace();
        return null;
    }
}
