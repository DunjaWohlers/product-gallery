package de.neuefische.cgnjava222.productgallery.model;

import java.time.Instant;
import java.util.List;

public record NewSingleOrder(
        Instant timeDate,
        List<OrderItem> orderItems
) {
}
