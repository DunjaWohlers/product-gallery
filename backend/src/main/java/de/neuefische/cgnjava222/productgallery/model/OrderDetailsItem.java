package de.neuefische.cgnjava222.productgallery.model;

public record OrderDetailsItem(
        Product product,
        int count,
        int price) {
}
