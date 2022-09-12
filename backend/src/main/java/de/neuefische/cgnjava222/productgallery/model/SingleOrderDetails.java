package de.neuefische.cgnjava222.productgallery.model;

import java.util.List;

public record SingleOrderDetails(
        String id,
        String date,
        List<OrderDetailsItem> orderItems
) {
}
