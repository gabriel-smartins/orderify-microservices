package br.com.orderify.notification.listener;

import br.com.orderify.notification.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    private final Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @RabbitListener(queues = "orders.v1.order-created.generate-notification")
    public void sendNotification(Order order){
        logger.info("Notification created: {}", order.toString());
    }
}
