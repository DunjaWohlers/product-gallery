package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public record UserOrders(
        @Id
        String name,
        List<SingleOrder> orderList
) {
}
