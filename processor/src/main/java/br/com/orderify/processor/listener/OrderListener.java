package br.com.orderify.processor.listener;

import br.com.orderify.processor.entity.Order;
import br.com.orderify.processor.entity.enums.Status;
import br.com.orderify.processor.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderListener {
    private final OrderService orderService;

    @RabbitListener(queues = "orders.v1.order-created.generate-processor")
    public void saveOrder(Order order) {
        order.setStatus(Status.PROCESSED);
        orderService.save(order);
    }
}
