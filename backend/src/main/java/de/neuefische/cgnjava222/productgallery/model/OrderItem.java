package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.PersistenceCreator;

public record OrderItem(
        Product product,
        int count,
        int price
) {
    @PersistenceCreator
    public OrderItem {
    }

    public OrderItem(Product product, int count) {
        this(product, count, product.price() * count);
    }
}
