package br.com.orderify.processor.service;

import br.com.orderify.processor.entity.Order;
import br.com.orderify.processor.entity.OrderItem;
import br.com.orderify.processor.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    public void save(Order order) {
        productService.save(order.getItems());

        List<OrderItem> orderItems = orderItemService.save(order.getItems());

        orderRepository.save(order);

        orderItemService.updatedOrderItem(orderItems, order);

        logger.info("Order saved: {}", order);
    }
}
