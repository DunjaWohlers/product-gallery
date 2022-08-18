package de.neuefische.cgnjava222.productgallery.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public record Product(
        @Id
        String id,
        String title,
        String description,
        List<String> pictureUrls,
        Integer price,
        Integer availableCount
) {
}
