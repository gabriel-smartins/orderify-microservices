package br.com.orderify.processor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private UUID id = UUID.randomUUID();
    private Product product;
    private Integer quantity;
}
