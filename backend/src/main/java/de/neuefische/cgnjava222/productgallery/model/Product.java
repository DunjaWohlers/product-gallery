package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

public record Product(
        @Id
        String id,
        String title,
        String description,
        List<String> pictureUrls,
        Integer price,
        Integer availableCount
) {
        public Product(String title, String description, List<String> pictureUrls, Integer price, Integer availableCount) {
                this(UUID.randomUUID().toString(), title, description, pictureUrls, price, availableCount);

        }
}
