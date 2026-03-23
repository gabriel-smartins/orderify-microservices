package br.com.orderify.processor.listener;

import br.com.orderify.processor.entity.Order;
import br.com.orderify.processor.entity.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class OrderListener {

    private final Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @RabbitListener(queues = "orders.v1.order-created.generate-processor")
    public void saveOrder(Order order) {
        order.setStatus(Status.PROCESSED);
        logger.info("Order received: {}", order);
    }
}
