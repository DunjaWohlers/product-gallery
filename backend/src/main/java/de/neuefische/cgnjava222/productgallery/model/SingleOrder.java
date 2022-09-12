package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;


public record SingleOrder(
        @Id
        String id,

        Instant timeDate,

        String userName,
        List<OrderItem> orderItems
) {
}
