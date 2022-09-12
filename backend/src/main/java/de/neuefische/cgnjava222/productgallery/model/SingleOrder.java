package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public record SingleOrder(
        @Id
        String id,

        LocalDateTime date,

        String userName,
        List<OrderItem> orderItems
) {
}
