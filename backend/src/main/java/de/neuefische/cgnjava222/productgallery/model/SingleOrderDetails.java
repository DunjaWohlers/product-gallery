package de.neuefische.cgnjava222.productgallery.model;

import java.util.List;

public record SingleOrderDetails(
        String id,
        String userName,
        List<OrderDetailsItem> orderItems
) {
}
