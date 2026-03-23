package br.com.orderify.processor.service;

import br.com.orderify.processor.entity.Order;
import br.com.orderify.processor.entity.OrderItem;
import br.com.orderify.processor.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> save(List<OrderItem> items) {
        return orderItemRepository.saveAll(items);
    }

    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void updatedOrderItem(List<OrderItem> orderItems, Order order) {
        orderItems.forEach(item -> {
            item.setOrder(order);
            this.save(item);
        });
    }
}
