package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record Product(
        @Id String id,
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
        public Product(String title, String description, List<String> pictureUrls, Integer price, Integer availableCount) {
                this(UUID.randomUUID().toString(), title, description, pictureUrls, price, availableCount);

        }
}
