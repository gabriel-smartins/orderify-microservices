package br.com.orderify.processor.entity;


import br.com.orderify.processor.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private UUID id = UUID.randomUUID();
    private String customer;
    private List<OrderItem> items = new ArrayList<>();
    private Double totalValue;
    private String notificationEmail;
    private Status status = Status.ON_PROCESSING;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateHour = LocalDateTime.now() ;
}
