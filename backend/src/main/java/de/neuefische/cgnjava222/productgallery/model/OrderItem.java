package de.neuefische.cgnjava222.productgallery.model;

public record OrderItem(
        Product product,
        int count,
        int price
) {
    public OrderItem(Product product, int count) {
        this(product, count, product.price() * count);
    }
}
