package de.neuefische.cgnjava222.productgallery.model;

public record OrderItem(
        String productId,
        int count,
        int price
) {
}
