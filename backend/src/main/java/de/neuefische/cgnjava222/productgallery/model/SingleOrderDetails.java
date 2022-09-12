package de.neuefische.cgnjava222.productgallery.model;

import java.time.Instant;
import java.util.List;

public record SingleOrderDetails(
        String id,
        Instant instant,
        String userName,
        List<OrderDetailsItem> orderItems
) {
}
