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
        List<ImageInfo> pictureObj
) {
    public static class ProductFactory {
        private ProductFactory() {
        }

        public static Product create(NewProduct product) {
            return new Product(
                    UUID.randomUUID().toString(),
                    product.title(),
                    product.description(),
                    product.pictureObj()
            );
        }

        public static Product create(String id, NewProduct product) {
            return new Product(
                    id,
                    product.title(),
                    product.description(),
                    product.pictureObj()
            );
        }
    }
}
