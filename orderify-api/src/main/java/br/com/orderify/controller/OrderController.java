package br.com.orderify.controller;

import br.com.orderify.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders", description = "Resource to create a new order")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Operation(summary = "Create new order", description = "Contains operations to create a new order",
    responses = @ApiResponse(responseCode = "201", description = "Resource created successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))))
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){

        logger.info("Order received: {}", order.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
