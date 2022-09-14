package de.neuefische.cgnjava222.productgallery.model;


import java.util.List;

public record NewSingleOrder(
        String date,
        List<OrderItem> orderItems
) {
}
