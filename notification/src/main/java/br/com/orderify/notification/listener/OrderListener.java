package br.com.orderify.notification.listener;

import br.com.orderify.notification.entity.Order;
import br.com.orderify.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderListener {

    private final Logger logger = LoggerFactory.getLogger(OrderListener.class);

    private final EmailService emailService;

    @RabbitListener(queues = "orders.v1.order-created.generate-notification")
    public void sendNotification(Order order){
        emailService.sendEmail(order);
        logger.info("Notification created: {}", order.toString());
    }
}
