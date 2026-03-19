package br.com.orderify.notification.service;

import br.com.orderify.notification.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("orders-api@company.com");
        message.setTo(order.getNotificationEmail());
        message.setSubject("Order Notification");
        message.setText(generateMessage(order));
        mailSender.send(message);
    }

    private String generateMessage(Order order) {
        String orderId = order.getId().toString();
        String customer = order.getCustomer();
        String value = order.getTotalValue().toString();
        String status = order.getStatus().name();

        return String
                .format("Hello, %s \nOrder nº %s, has been placed successfully!\nStatus: %s.\nTotal: %s.", customer, orderId, status, value);
    }
}
