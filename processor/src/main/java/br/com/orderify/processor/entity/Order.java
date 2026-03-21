package br.com.orderify.processor.entity;


import br.com.orderify.processor.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private UUID id = UUID.randomUUID();

    private String customer;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "total_value")
    private Double totalValue;

    @Column(name = "notification_email")
    private String notificationEmail;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ON_PROCESSING;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateHour = LocalDateTime.now() ;
}
