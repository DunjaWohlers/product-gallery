package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

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
    @PersistenceConstructor
    public Product(
            @NotNull String id,
            @NotNull String title,
            @NotNull String description,
            @NotNull List<String> pictureUrls,
            @NotNull Integer price,
            @NotNull Integer availableCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pictureUrls = pictureUrls;
        this.price = price;
        this.availableCount = availableCount;
    }

    public Product(NewProduct product) {
        this(UUID.randomUUID().toString(), product.title(), product.description(),
                product.pictureUrls(), product.price(), product.availableCount());
    }
}
