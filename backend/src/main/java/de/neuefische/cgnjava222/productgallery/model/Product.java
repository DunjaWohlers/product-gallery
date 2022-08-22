package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record Product(
        @Id
        @NotNull
        String id,
        @NotNull
        String title,
        @NotNull
        String description,
        @NotNull
        List<String> pictureUrls,
        @NotNull
        Integer price,
        @NotNull
        Integer availableCount
) {
    public static class ProductFactory {
        private ProductFactory() {
        }

        public static Product create(NewProduct product) {
            return new Product(
                    UUID.randomUUID().toString(),
                    product.title(),
                    product.description(),
                    product.pictureUrls(),
                    product.price(),
                    product.availableCount());
        }

        public static Product create(String id, NewProduct product) {
            return new Product(
                    id,
                    product.title(),
                    product.description(),
                    product.pictureUrls(),
                    product.price(),
                    product.availableCount());
        }
    }
}
