package br.com.orderify.processor.service;

import br.com.orderify.processor.entity.OrderItem;
import br.com.orderify.processor.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void save(List<OrderItem> items) {
        items.forEach(item -> {
            productRepository.save(item.getProduct());
        });
    }
}
